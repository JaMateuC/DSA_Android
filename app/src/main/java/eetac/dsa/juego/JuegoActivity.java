package eetac.dsa.juego;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import eetac.dsa.activity.Main;
import eetac.dsa.activity.MainMenu;
import eetac.dsa.activity.MonstruoAdapter;
import eetac.dsa.juego.Controlador.Escenario;
import eetac.dsa.juego.Controlador.Monstruo;
import eetac.dsa.juego.Controlador.Objeto;
import eetac.dsa.juego.Controlador.Usuario;
import eetac.dsa.R;
import eetac.dsa.juego.root.CombatCall;
import eetac.dsa.juego.root.ConexionServidor;
import eetac.dsa.juego.root.Mundo;
import eetac.dsa.juego.vista.JuegoView;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JuegoActivity extends AppCompatActivity
{
    private ArrayAdapter<String> adaptadorO;
    private ArrayAdapter<String> adaptadorM;
    private ArrayAdapter<String> adaptadorG;
    private ArrayAdapter<String> adaptadorOO;
    private MonstruoAdapter adapter;
    ArrayList<String> listaO;
    ArrayList<Monstruo> listaM;
    ArrayList<String> listaG;
    ArrayList<String> listaOO;
    public int opciones;
    ListView lisG;
    ListView lisv;
    ListView lisM;
    ListView lisOO;
    int key;
    Mundo mundo;
    private static Retrofit retrofit = null;
    private String BASE_URL;

    ArrayList<String> Objetosencontrados;
    RestClient client;

    JuegoView juegoView;
    int direccion = 0;
    ConstraintLayout iuMap;
    ConstraintLayout iuCombat;
    int indiceobjeto;
    boolean genero;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);


        mundo = Mundo.getIns();
        key = getIntent().getExtras().getInt("key");
        BASE_URL = getString(R.string.URL_BASE);

        juegoView = (JuegoView)findViewById(R.id.juego_view);
        juegoView.setmResources(getResources());
        juegoView.setDireccion(direccion);

        Objetosencontrados= new ArrayList<>();
        listaO= new ArrayList<String>();
        adaptadorO= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaO);
        lisv = (ListView) findViewById(R.id.ListaInventario);
        lisv.setAdapter(adaptadorO);

        //listaM= new ArrayList<String>();
        //adaptadorM= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaM);
        lisM = (ListView) findViewById(R.id.ListaMonstruos);
        //lisM.setAdapter(adaptadorM);



        listaG= new ArrayList<String>(Arrays.asList("monstruos", "objetos", "salir"));

        adaptadorG= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaG);
        lisG = (ListView) findViewById(R.id.ListaGeneral);
        lisG.setAdapter(adaptadorG);


        listaOO= new ArrayList<String>(Arrays.asList("usar", "tirar", "atras"));

        adaptadorOO= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaOO);
        lisOO = (ListView) findViewById(R.id.ListaOpcionobjetos);
        lisOO.setAdapter(adaptadorOO);



        lisG.setVisibility(View.VISIBLE);
        lisv.setVisibility(View.GONE);
        lisM.setVisibility(View.GONE);
        lisOO.setVisibility(View.GONE);
        //
        indiceobjeto=-1;
        lisv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            String opcion;
            if (opciones==1){
                opcion = "usar";
            }
            else{
                opcion = "tirar";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(JuegoActivity.this);
            builder.setTitle("Quieres " + opcion + ": "+ mundo.getUsuario().getInventario().getListObjetos().elementAt(i).getNombre()+"\n");
            builder.setMessage("Descripción: "+ mundo.getUsuario().getInventario().getListObjetos().elementAt(i).getDescripcion());

            final String[] aux = listaO.get(i).split("-");
            Objeto o = mundo.getUsuario().getInventario().buscarObjetoPorNombre(aux[1]);
            final int index = mundo.getUsuario().getInventario().getListObjetos().indexOf(o);
            //final int index = 0;
            //
            //mundo.getUsuario().getInventario().getListObjetos().get(index);
            //mundo.getUsuario().getInventario().getListObjetos().get(i);
            builder.setPositiveButton("SI", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {


                    if (opciones == 1){
                        Objeto o = mundo.getUsuario().getInventario().buscarObjetoPorNombre(aux[1]);
                        if (o.getDestino() == Objeto.Destino.Personaje)
                        {
                            mundo.getUsuario().usarObjeto(index);
                            Cambioinventario();
                        }
                        else{
                            indiceobjeto = index;
                            lisv.setVisibility(View.GONE);
                            lisM.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        mundo.getUsuario().getInventario().quitarObjeto(index);
                        Cambioinventario();
                    }




                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    });

        lisM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i !=0)
                {
                    int aux = i-1;
                    if (indiceobjeto != -1)
                    {
                        mundo.getUsuario().usarObjetoAMonstruo(indiceobjeto,aux);
                        indiceobjeto = -1;

                        Toast toast = Toast.makeText(JuegoActivity.this.getApplicationContext(), "Usaste el objeto en el monstruo!", Toast.LENGTH_SHORT);
                        toast.show();
                        Cambioinventario();

                    }
                    else{
                        Toast toast = Toast.makeText(JuegoActivity.this.getApplicationContext(), "Puntos de vida: "+listaM.get(aux).getVidaActual()+"/"+listaM.get(aux).getVidaBase()+" Nivel: "+ listaM.get(aux).getNivel()+" Experiencia: "+ listaM.get(aux).getExperiencia(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }





            }
        });

        lisG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0)
                {
                    lisM.setVisibility(View.VISIBLE);
                    lisG.setVisibility(View.GONE);
                }
                else if ( i == 1)
                {
                    lisOO.setVisibility(View.VISIBLE);
                    lisG.setVisibility(View.GONE);
                }
                else if ( i ==2){
                    lisG.setVisibility(View.GONE);
                }


            }
        });

        lisOO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0)
                {
                    lisv.setVisibility(View.VISIBLE);
                    lisOO.setVisibility(View.GONE);
                    opciones=1;
                }
                else if ( i == 1)
                {
                    lisv.setVisibility(View.VISIBLE);
                    lisOO.setVisibility(View.GONE);
                    opciones = 2;
                }
                else if ( i ==2){

                    lisOO.setVisibility(View.GONE);
                    lisG.setVisibility(View.VISIBLE);
                }


            }
        });
            Button Menuprincipal = (Button)findViewById(R.id.menuprincipal);


        Menuprincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisG.setVisibility(View.VISIBLE);
                lisv.setVisibility(View.GONE);
                lisM.setVisibility(View.GONE);
                lisOO.setVisibility(View.GONE);
            }
        });


        Button down = (Button)findViewById(R.id.button_down);
        Button up = (Button)findViewById(R.id.button_up);
        Button left = (Button)findViewById(R.id.button_left);
        Button right = (Button)findViewById(R.id.button_right);

        Button leftCombat = (Button)findViewById(R.id.button_left_combat);
        Button rightCombat = (Button)findViewById(R.id.button_right_combat);
        Button upCombate = (Button)findViewById(R.id.button_up_combate);

        Button iuAtaque1 = (Button)findViewById(R.id.iu_ataque1);
        Button iuAtaque2 = (Button)findViewById(R.id.iu_ataque2);
        Button iuAtaque3 = (Button)findViewById(R.id.iu_ataque3);
        Button iuAtaque4 = (Button)findViewById(R.id.iu_ataque4);

        Button capturar = findViewById(R.id.Capturar);

        capturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mundo.getIns().getCombate().capturar();
            }
        });

        iuAtaque1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.atacar(0);
            }
        });

        iuAtaque2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.atacar(1);
            }
        });
        iuAtaque3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.atacar(2);
            }
        });
        iuAtaque4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.atacar(3);
            }
        });

        iuMap = (ConstraintLayout)findViewById(R.id.map_layout_iu);
        iuCombat = (ConstraintLayout)findViewById(R.id.combat_layout_iu);
        iuCombat.setVisibility(View.GONE);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.mover(0,+1);
                direccion = 0;
                juegoView.setDireccion(direccion);
                Cambioinventario();
            }
        });


        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.mover(0,-1);
                direccion = 3;
                juegoView.setDireccion(direccion);
                Cambioinventario();
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.mover(-1,0);
                direccion = 1;
                juegoView.setDireccion(direccion);
                Cambioinventario();

            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.mover(+1,0);
                direccion = 2;
                juegoView.setDireccion(direccion);
                Cambioinventario();
            }
        });

        leftCombat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    mundo.comIzq();
                return false;
            }
        });

        upCombate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    mundo.saltar();
                return false;
            }
        });


        rightCombat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    mundo.comDer();
                return false;
            }
        });

        client = new RestClient(key, this, mundo, new Mundo.sincro() {
            @Override
            public void sincro() {
                View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
                listaM = new ArrayList<Monstruo>(mundo.getUsuario().getLista_montruos().getListMonstruos());
                adapter = new MonstruoAdapter(JuegoActivity.this,
                        R.layout.listview_item_row, listaM);
                lisM.addHeaderView(header);

                lisM.setAdapter(adapter);
            }
        });

        mundo.init(this.key, new ConexionServidor() {
            @Override
            public void cambiarMapa(int key, String nombreEscenario, int x, int y) {
                client.cambiarMapa(key, nombreEscenario, x, y);
            }

            @Override
            public void updateUsuario(int key, String nombreEscenario, Usuario usuario) {
                try {
                    UsuarioJSON usuarioJSON = new UsuarioJSON();
                    usuarioJSON.fromUsuario(usuario);
                    client.guardar(key, nombreEscenario, usuarioJSON);

                } catch (Exception e) {
                    Log.e("saveUser", "error al pasar de usuario a json cuando se guarda");
                }

            }

            @Override
            public void getLoginArgs(int key) {
                client.getLoginArgs(key);
            }
        }, new CombatCall() {
            @Override
            public void init() {
                iuMap.setVisibility(View.GONE);
                iuCombat.setVisibility(View.VISIBLE);
            }

            @Override
            public void resultado(final boolean isWin,final boolean capturar) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iuMap.setVisibility(View.VISIBLE);
                        iuCombat.setVisibility(View.GONE);

                        if(isWin) {
                            Mundo.getIns().endCombat(capturar);
                        }
                        else
                        {
                            Mundo.getIns().getUsuario().getLista_montruos().quitarMonstruoPorPosicion(0);
                            Mundo.getIns().endCombat(false);
                            Mundo.getIns().cambiarMapa("Escenario1",1,1);

                        }
                        //
                        Cambioinventario();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        juegoView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        juegoView.pause();
    }

    public void Cambioinventario(){
        int i = 1;
        listaO.clear();
        listaM.clear();
        Objetosencontrados.clear();
        try {
            for (Objeto o : mundo.getUsuario().getInventario().getListObjetos()) {
                if(Objetosencontrados.contains(o.toString())) {
                   int index = Objetosencontrados.indexOf(o.toString());
                   String aux = listaO.get(index);
                   String[] auxl= aux.split("-");
                   int num = Integer.parseInt(auxl[0]);
                   num++;
                   listaO.remove(index);
                   listaO.add(index,num+"-"+o.toString()+"-");
                   //Objetosencontrados.add(o.toString());
                }
                else{
                    listaO.add(1 + "-" + o.toString()+"-"); //1 + " " + o.toString());
                    Objetosencontrados.add(o.toString());
                    i++;
                }

            }

        }
        catch(NullPointerException e)
        {
            listaO.clear();

    }
        //for (Monstruo M : mundo.getUsuario().getLista_montruos().getListMonstruos()) {
        //    listaM.add(M.toString());
        //}
        if (mundo.getEstado()== Mundo.FSM.play) {
            listaM = new ArrayList<Monstruo>(mundo.getUsuario().getLista_montruos().getListMonstruos());
            adapter = new MonstruoAdapter(this,
                    R.layout.listview_item_row, listaM);
            //lisM.addHeaderView(header);

            lisM.setAdapter(adapter);
        }

        adaptadorO.notifyDataSetChanged();

    }
}