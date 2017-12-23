package eetac.dsa.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import eetac.dsa.R;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Perfil extends AppCompatActivity {


    private ProgressDialog progressDialog;
    private static Retrofit retrofit = null;
    private static String BASE_URL;
    UsuarioJSON usuario;
    int key;

    EditText nombre;
    EditText oldpass;
    EditText newpass;
    EditText email;
    EditText genero;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        BASE_URL = getString(R.string.URL_BASE);

        nombre = (EditText) findViewById(R.id.nombre);
        oldpass = (EditText) findViewById(R.id.oldPassword);
        newpass = (EditText) findViewById(R.id.newPassword);
        email = (EditText) findViewById(R.id.email);
        genero = (EditText) findViewById(R.id.genero);

        getPerfil();
        /*lista =


        //lista=(ArrayList<String>)getIntent().getSerializableExtra("valor1");

        adaptador= new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, lista);
        ListView lisv = (ListView) findViewById(R.id.Lista1);
        lisv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        */
    }

    public void getPerfil()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando Sesión");
        progressDialog.show();

        APIservice apiService = retrofit.create(APIservice.class);


        //Recoge los valores de la actividad anterior
        Bundle intentdata = getIntent().getExtras();
        usuario = (UsuarioJSON) intentdata.getSerializable("usuario");

        Call<UsuarioJSON> profile = apiService.profile(usuario.getNombre());
        profile.enqueue(new Callback<UsuarioJSON>()
        {
            @Override
            public void onResponse(Call<UsuarioJSON> profile, Response<UsuarioJSON> response)
            {
                progressDialog.dismiss();

                UsuarioJSON user = response.body();

                nombre.setText(user.getNombre());
                email.setText(user.getEmail());

                if(user.isGenero()) genero.setText("hombre");
                else                genero.setText("mujer");
            }

            @Override
            public void onFailure(Call<UsuarioJSON> login, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}