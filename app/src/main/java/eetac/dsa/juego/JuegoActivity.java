package eetac.dsa.juego;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import eetac.dsa.juego.Controlador.Escenario;
import eetac.dsa.juego.Controlador.Objeto;
import eetac.dsa.juego.Controlador.Usuario;
import eetac.dsa.R;
import eetac.dsa.juego.root.ConexionServidor;
import eetac.dsa.juego.root.Mundo;
import eetac.dsa.juego.vista.JuegoView;
import eetac.dsa.model.UsuarioJSON;

public class JuegoActivity extends AppCompatActivity
{
    private ArrayAdapter<String> adaptador;
    private ArrayAdapter<String> adaptadorM;
    ArrayList<String> listaO;
    ArrayList<String> listaM;
    int key;
    Mundo mundo;

    ArrayList<String> Objetosencontrados;
    RestClient client;

    JuegoView juegoView;
    int direccion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        mundo = Mundo.getIns();
        key = getIntent().getExtras().getInt("key");

        juegoView = (JuegoView)findViewById(R.id.juego_view);
        juegoView.setmResources(getResources());
        juegoView.setDireccion(direccion);

        Objetosencontrados= new ArrayList<>();
        listaO= new ArrayList<String>();
        adaptador= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaO);
        ListView lisv = (ListView) findViewById(R.id.ListaInventario);
        lisv.setAdapter(adaptador);



        listaM= new ArrayList<String>();
        adaptadorM= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaM);
        ListView lisM = (ListView) findViewById(R.id.ListaMonstruos);
        lisM.setAdapter(adaptadorM);

        //
        lisv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String operacio = (String)adapterView.getItemAtPosition(i);//pendiente
                Toast toast = Toast.makeText(getApplicationContext(), mundo.getUsuario().getInventario().getListObjetos().elementAt(i).getDescripcion(), Toast.LENGTH_SHORT);
                toast.show();

            }
        });



        Button down = (Button)findViewById(R.id.button_down);
        Button up = (Button)findViewById(R.id.button_up);
        Button left = (Button)findViewById(R.id.button_left);
        Button right = (Button)findViewById(R.id.button_right);

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
        
        client = new RestClient(key,this,mundo);

        mundo.init(key, new ConexionServidor()
        {
            @Override
            public void cambiarMapa(int key, String nombreEscenario, int x, int y)
            {
                client.cambiarMapa(key,nombreEscenario,x,y);
            }

            @Override
            public void updateUsuario(int key, String nombreEscenario, Usuario usuario) {
                try {
                    UsuarioJSON usuarioJSON = new UsuarioJSON();
                    usuarioJSON.fromUsuario(usuario);
                    client.guardar(key,nombreEscenario,usuarioJSON);

                }
                catch (Exception e)
                {
                    Log.e("saveUser","error al pasar de usuario a json cuando se guarda");
                }

            }

            @Override
            public void getLoginArgs(int key) {
                client.getLoginArgs(key);
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
        int i = 0;
        listaO.clear();
        Objetosencontrados.clear();
        try {
            for (Objeto o : mundo.getUsuario().getInventario().getListObjetos()) {
                if(Objetosencontrados.contains(o.toString())) {
                   int index = Objetosencontrados.indexOf(o.toString());
                   String aux = listaO.get(index);
                   String[] auxl= aux.split(" ");
                   int num = Integer.parseInt(auxl[0]);
                   num++;
                   listaO.remove(index);
                   listaO.add(index,num+" "+o.toString());
                }
                else{
                    listaO.add(1 + " " + o.toString()); //1 + " " + o.toString());
                    Objetosencontrados.add(o.toString());
                    i++;
                }

            }

        }
        catch(NullPointerException e)
        {
            listaO.clear();

    }
        adaptador.notifyDataSetChanged();
    }
}