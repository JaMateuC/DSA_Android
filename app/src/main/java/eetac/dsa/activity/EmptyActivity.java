package eetac.dsa.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class EmptyActivity extends AppCompatActivity
{
    private static final String TAG = Main.class.getSimpleName();
    private static Retrofit retrofit = null;
    private SharedPreferences sharedpref;
    private String BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        BASE_URL = getString(R.string.URL_BASE);

        //Inicia sesión automaticamente
        sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        if (!sharedpref.getString("username", "").equals(""))
        {
            IniciarSesion();
        }
        else
        {
            //Si no detecta un usuario logeado pasa al Main para iniciar sesion
            Intent intent = new Intent(this, Main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    public void IniciarSesion()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);

        //JSON que enviamos al servido
        final UsuarioJSON usuario = new UsuarioJSON(sharedpref.getString("username", ""), sharedpref.getString("password", ""));

        Call<KeyUser> login = apiService.login(usuario);
        login.enqueue(new Callback<KeyUser>()
        {
            @Override
            public void onResponse(Call<KeyUser> login, Response<KeyUser> response)
            {
                if(response.body()== null)
                {
                    Intent intent = new Intent(EmptyActivity.this, Main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return;
                }

                int key = response.body().getKey();
                if(key == 0)
                {
                    Intent intent = new Intent(EmptyActivity.this, Main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return;
                }

                //El usuario está autentificado
                usuario.setKey(key);

                Intent intent = new Intent(EmptyActivity.this, MainMenu.class);
                intent.putExtra("key", key);
                intent.putExtra("usuario", usuario);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<KeyUser> login, Throwable t)
            {
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}