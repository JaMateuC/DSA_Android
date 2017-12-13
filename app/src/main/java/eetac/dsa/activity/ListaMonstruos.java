package eetac.dsa.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import eetac.dsa.R;
import eetac.dsa.model.MonstruoJSON;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

/**
 * Created by JesusLigero on 12/12/2017.
 */

public class ListaMonstruos extends AppCompatActivity {

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
        setContentView(R.layout.activity_monstruos);

        Nombreusuario = (EditText) findViewById(R.id.NombreUsuario);

        lista = new ArrayList<String>();
        Bundle intentdata = getIntent().getExtras();

        UsuarioJSON u = (UsuarioJSON) intentdata.getSerializable("usuario");
        BASE_URL=intentdata.getString("URL");

        Nombreusuario.setText(u.getNombre());



        adaptador= new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, lista);
        ListView lisv = (ListView) findViewById(R.id.ListaMonstruos);
        lisv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        Getlista();

        lisv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String operacio = (String)adapterView.getItemAtPosition(i);//pendiente

            }
        });


        consultar = (Button) findViewById(R.id.Consultar);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Getlista();
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

        Call<ArrayList<MonstruoJSON>>  getlsita= apiService.listaM(Nombreusuario.getText().toString());
        getlsita.enqueue(new Callback<ArrayList<MonstruoJSON>>()
        {
            @Override
            public void onResponse(Call<ArrayList<MonstruoJSON>> login, Response<ArrayList<MonstruoJSON>> response)
            {
                ArrayList<MonstruoJSON> listaM = response.body();
                if(listaM == null) {
                    Toast toast = Toast.makeText(getApplicationContext(),"No existe el usuario", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    lista.clear();
                    int i = 1;
                    for (MonstruoJSON m : listaM)
                    {

                        lista.add(i+" "+m.toString());
                        i++;
                    }
                    adaptador.notifyDataSetChanged();
                }
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<ArrayList<MonstruoJSON>> login, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }



}
