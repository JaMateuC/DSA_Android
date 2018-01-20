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
import eetac.dsa.model.MonstruoJSON;
import eetac.dsa.model.ObjetoJSON;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inventario extends AppCompatActivity
{
    private String BASE_URL;
    private ArrayAdapter<String> adaptador;
    private ProgressDialog progressDialog;
    private UsuarioJSON user;
    Button consultar;
    EditText Nombreusuario;
    ArrayList<String> lista;
    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        BASE_URL = getString(R.string.URL_BASE);

        lista = new ArrayList<String>();

        Bundle intentdata = getIntent().getExtras();
        user = (UsuarioJSON) intentdata.getSerializable("usuario");

        adaptador= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        ListView lisv = (ListView) findViewById(R.id.ListaObjetos);
        lisv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

        Getlista();
    }

    public void Getlista()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");
        progressDialog.show();

        APIservice apiService = retrofit.create(APIservice.class);

        Call<ArrayList<ObjetoJSON>>  getlista = apiService.listaObjetos(user.getNombre());
        getlista.enqueue(new Callback<ArrayList<ObjetoJSON>>()
        {
            @Override
            public void onResponse(Call<ArrayList<ObjetoJSON>> getlista, Response<ArrayList<ObjetoJSON>> response)
            {
                if(response.body()== null)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Els servidor no ha dado respuesta", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ArrayList<ObjetoJSON> listaM = response.body();
                if(listaM == null)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"No existe el usuario", Toast.LENGTH_SHORT);
                    toast.show();
                }

                else
                {
                    lista.clear();
                    int i = 1;
                    for (ObjetoJSON m : listaM)
                    {
                        lista.add(i+" "+m.toString());
                        i++;
                    }

                    if(i == 1){  lista.add("No hay objetos guardados");  }
                    adaptador.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ObjetoJSON>> getlista, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}