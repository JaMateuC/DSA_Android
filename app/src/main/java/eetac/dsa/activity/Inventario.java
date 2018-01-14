package eetac.dsa.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


import eetac.dsa.R;
import eetac.dsa.juego.Controlador.Objeto;
import eetac.dsa.juego.Controlador.Usuario;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JesusLigero on 14/01/2018.
 */

public class Inventario extends AppCompatActivity{
    private ArrayAdapter<String> adaptador;
    private ProgressDialog progressDialog;
    private String BASE_URL ;
    Button consultar;
    EditText Nombreusuario;
    ArrayList<String> lista;

    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        BASE_URL = getString(R.string.URL_BASE);

        lista = new ArrayList<String>();

        Bundle intentdata = getIntent().getExtras();
        UsuarioJSON u = (UsuarioJSON) intentdata.getSerializable("usuario");


        Nombreusuario = (EditText) findViewById(R.id.Filtro);
        Nombreusuario.setText(u.getNombre());

        adaptador= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        ListView lisv = (ListView) findViewById(R.id.ListaInventario);
        lisv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        Getlista(u.getNombre());




        //


        lisv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String operacio = (String)adapterView.getItemAtPosition(i);//pendiente
            }
        });

        consultar = (Button) findViewById(R.id.Filtrar);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Getlista("123");
            }
        });
    }

    public void Getlista(String nombre)
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");
        progressDialog.show();

        APIservice apiService = retrofit.create(APIservice.class);

        //JSON que enviamos al servido

        Call<Usuario> profile = apiService.profile(nombre);
        profile.enqueue(new Callback<Usuario>()
        {
            @Override
            public void onResponse(Call<Usuario> login, Response<Usuario> response)
            {

                if(response.body()== null)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Els servidor no ha dado respuesta", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                Usuario user = response.body();


                if(user == null) {

                    Toast toast = Toast.makeText(getApplicationContext(),"No existe el usuario", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    //lista.clear();
                    int i = 1;
                    try {
                        for (Objeto o : user.getInventario().getListObjetos()) {
                            lista.add(i + " " + o.toString());
                            i++;
                        }
                    }
                    catch(NullPointerException e)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(),"no tiene inventario", Toast.LENGTH_SHORT);
                        toast.show();

                    }

                    //adaptador.notifyDataSetChanged();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Usuario> login, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}
