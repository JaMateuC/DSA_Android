package eetac.dsa.activity;

import android.app.ProgressDialog;
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
import eetac.dsa.model.KeyUser;
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
    private static final String TAG = Main.class.getSimpleName();
    private ProgressDialog progressDialog;
    public static final String BASE_URL = "http://10.192.119.86:8080/myapp/";

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

        //Inicia sesión automaticamente
        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        if(!sharedpref.getString("username", "").equals(""))
        {
            user.setText(sharedpref.getString("username", ""));
            pass.setText(sharedpref.getString("password", ""));

            IniciarSesion();
        }

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
                IniciarSesion();
            }
        }
        );

        btnRegistrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Main.this, Registrar.class);
                intent.putExtra("URL", BASE_URL);
                startActivity(intent);
            }
        }
        );
    }

    public void IniciarSesion()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando Sesión");
        progressDialog.show();

        APIservice apiService = retrofit.create(APIservice.class);

        //JSON que enviamos al servido
        final UsuarioJSON usuario = new UsuarioJSON(user.getText().toString(),
                                                    pass.getText().toString());

        Call<KeyUser> login = apiService.login(usuario);
        login.enqueue(new Callback<KeyUser>()
        {
            @Override
            public void onResponse(Call<KeyUser> login, Response<KeyUser> response)
            {
                progressDialog.dismiss();
                int key = response.body().getKey();
                if(key == 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario y/o contrasela incorrectos", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                //El usuario está autentificado

                usuario.setKey(key);
                Toast toast = Toast.makeText(getApplicationContext(), "Bienvenido  "+usuario.toString(), Toast.LENGTH_SHORT);
                toast.show();

                SharedPreferences sharedpref= getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.putString("username", user.getText().toString());
                editor.putString("password", pass.getText().toString());
                editor.putInt("key",key);
                //key to
                editor.apply();

                Intent intent = new Intent(Main.this, MainMenu.class);
                intent.putExtra("URL", BASE_URL);
                intent.putExtra("key", key);
                intent.putExtra("usuario", usuario);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onFailure(Call<KeyUser> login, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}