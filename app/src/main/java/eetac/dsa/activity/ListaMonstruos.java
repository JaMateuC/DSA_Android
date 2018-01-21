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

        BASE_URL = getString(R.string.URL_BASE);

        lista = new ArrayList<String>();

        Bundle intentdata = getIntent().getExtras();
        UsuarioJSON u = (UsuarioJSON) intentdata.getSerializable("usuario");

        Nombreusuario = (EditText) findViewById(R.id.NombreUsuario);
        Nombreusuario.setText(u.getNombre());

        adaptador= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
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

        consultar = (Button) findViewById(R.id.Filtrar);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(!Nombreusuario.getText().toString().equals(""))
                {
                    Getlista();
                }

                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Campos incompletos", Toast.LENGTH_SHORT);
                    toast.show();
                }
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

        Call<ArrayList<MonstruoJSON>>  getlista = apiService.listaMonstruos(Nombreusuario.getText().toString());
        getlista.enqueue(new Callback<ArrayList<MonstruoJSON>>()
        {
            @Override
            public void onResponse(Call<ArrayList<MonstruoJSON>> getlista, Response<ArrayList<MonstruoJSON>> response)
            {
                progressDialog.dismiss();

                if(response.body()== null)
                {
                    lista.clear();
                    lista.add("Usuario no registrado");
                    return;
                }

                ArrayList<MonstruoJSON> listaM = response.body();

                lista.clear();
                int i = 1;
                for (MonstruoJSON m : listaM)
                {
                    lista.add(i+" "+m.toString());
                    i++;
                }

                if(i == 1){  lista.add("No hay monstruos capturados");  }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<MonstruoJSON>> getlista, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
