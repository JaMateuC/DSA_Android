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
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JesusLigero on 13/12/2017.
 */

public class Ranking extends AppCompatActivity {

    private ArrayAdapter<String> adaptador;
    private ProgressDialog progressDialog;
    private String BASE_URL = "http://192.168.0.13:8080/myapp/";
    Button consultar;

    ArrayList<String> lista;


    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        lista = new ArrayList<String>();


        adaptador= new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, lista);
        ListView lisv = (ListView) findViewById(R.id.Rankinglist);
        lisv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        Getlista();

        lisv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String operacio = (String)adapterView.getItemAtPosition(i);//pendiente

            }
        });


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

        //JSON que enviamos al servido

        Call<ArrayList<UsuarioJSON>> getlsita= apiService.listaR();
        getlsita.enqueue(new Callback<ArrayList<UsuarioJSON>>()
        {
            @Override
            public void onResponse(Call<ArrayList<UsuarioJSON>> login, Response<ArrayList<UsuarioJSON>> response)
            {
                ArrayList<UsuarioJSON> listaR = response.body();


                lista.clear();
                int i = 1;
                for (UsuarioJSON u : listaR)
                {
                    lista.add(i+" "+u.toString());
                    i++;
                }
                adaptador.notifyDataSetChanged();

                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<ArrayList<UsuarioJSON>> login, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

}
