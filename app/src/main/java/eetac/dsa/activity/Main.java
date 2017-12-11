package eetac.dsa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import eetac.dsa.R;
import eetac.dsa.model.Testeo;
import eetac.dsa.model.Usuario123;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Main extends AppCompatActivity
{
    //splash screen avans de main
    private static final String TAG = Registrar.class.getSimpleName();
   public static final String BASE_URL = "http://10.193.55.241:8080/myapp/json/";

    private static Retrofit retrofit = null;

    EditText user;
    EditText pass;
    CheckBox check;
    Button btnIniciar;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.userBox);
        pass = (EditText) findViewById(R.id.passBox);
        check = (CheckBox) findViewById(R.id.checkBox);
        btnIniciar = (Button) findViewById(R.id.btnIniciarSesion);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrarse);

        SharedPreferences sharedpref= getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        user.setText(sharedpref.getString("username", ""));
        pass.setText(sharedpref.getString("password", "test"));
        btnIniciar.performClick(); // no va D:


        check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (check.isChecked()) {  pass.setTransformationMethod(null);  }

                else {  pass.setTransformationMethod(new PasswordTransformationMethod());  }
            }
        }
        );

        btnIniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (TextUtils.isEmpty(user.getText().toString()) || TextUtils.isEmpty(pass.getText().toString()))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Campos incompletos", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                //connectAPIservice();
                IniciarSesion();
            }
        }
        );

        btnRegistrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                //connectAPIservice();
                Intent intent = new Intent(Main.this, Registrar.class);
                startActivity(intent);
            }
        }
        );
    }


    public void connectAPIservice() //no se usa
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);


        //Call<Testeo> post2 = apiService.test2("asd");
        Testeo t= new Testeo ("asd");
        Call<Testeo> post = apiService.test2(t);
        post.enqueue(new Callback<Testeo>()
        {
            @Override
            public void onResponse(Call<Testeo> post, Response<Testeo> response)
            {
                Toast toast = Toast.makeText(getApplicationContext(), response.body().getValue(), Toast.LENGTH_SHORT);
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

    public void IniciarSesion()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);

        Call<UsuarioJSON> login = apiService.testandroid(user.getText().toString(), pass.getText().toString());
        login.enqueue(new Callback<UsuarioJSON>()
        {
            @Override
            public void onResponse(Call<UsuarioJSON> post, Response<UsuarioJSON> response)
            {

                 UsuarioJSON res = response.body();
                 if(res.isGenero()){

                     Toast toast = Toast.makeText(getApplicationContext(), "Bienvenido  "+res.toString(), Toast.LENGTH_SHORT);
                     toast.show();


                     SharedPreferences sharedpref= getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                     SharedPreferences.Editor editor = sharedpref.edit();
                     editor.putString("username", user.getText().toString());
                     editor.putString("password", pass.getText().toString());
                     editor.apply();


                     Intent intent = new Intent(Main.this, IniciarSesion.class);
                     intent.putExtra("usuario", res);
                     startActivityForResult(intent, 1);
                 }
                else
                     {
                         Toast toast = Toast.makeText(getApplicationContext(), "Campos incorrectos", Toast.LENGTH_SHORT);
                         toast.show();
                     }


            }

            @Override
            public void onFailure(Call<UsuarioJSON> post, Throwable t)
            {
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();


            }
        });


    }
}