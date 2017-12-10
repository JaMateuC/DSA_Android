package eetac.dsa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.EventObject;

import eetac.dsa.R;
import eetac.dsa.model.Testeo;
import eetac.dsa.model.Usuario;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registrar extends AppCompatActivity
{
    private static final String TAG = Registrar.class.getSimpleName();
    public static final String BASE_URL = "http://192.168.0.13:8080/myapp/json/";
    private static Retrofit retrofit = null;

    EditText usuario;
    EditText password;
    EditText password2;
    Spinner genero;
    EditText email;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        genero = (Spinner) findViewById(R.id.spinner);
        email = (EditText) findViewById(R.id.email);
        btn = (Button) findViewById(R.id.button_aceptar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genero.setAdapter(adapter);


        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (TextUtils.isEmpty(usuario.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) ||
                    TextUtils.isEmpty(password2.getText().toString()) || TextUtils.isEmpty(email.getText().toString()))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Campos incompletos", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                else if (!password.getText().toString().equals(password2.getText().toString()))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error en la contraseña", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }


                Registro();
                //connectAPIservice();
            }
        });


    }

    public void connectAPIservice ()//no se usa
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);

        Call<Testeo> post = apiService.getTest(usuario.getText().toString());
        post.enqueue(new Callback<Testeo>()
        {
            @Override
            public void onResponse(Call<Testeo> post, Response<Testeo> response)
            {
                String text;
                if(response.body().getValue().equals("Ok"))
                {
                    text="Sí";
                }
                else
                {
                    text="No";
                }

                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<Testeo> post, Throwable t)
            {
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    public void Registro()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);


        //Call<Testeo> post2 = apiService.test2("asd");

        Usuario u = new Usuario(email.getText().toString(),password.getText().toString(),true);
        Call<Usuario> post = apiService.registro(u);
        post.enqueue(new Callback<Usuario>()
        {
            @Override
            public void onResponse(Call<Usuario> post, Response<Usuario> response)
            {
                Toast toast = Toast.makeText(getApplicationContext(), "correcto!!  "+response.body().toString(), Toast.LENGTH_SHORT);
                toast.show();
                finish();


            }

            @Override
            public void onFailure(Call<Usuario> post, Throwable t)
            {
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();

                //return null
            }
        });
    }



}