package eetac.dsa.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Registrar extends AppCompatActivity
{
    private static final String TAG = Registrar.class.getSimpleName();
    private ProgressDialog progressDialog;
    private String BASE_URL;
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

        BASE_URL = getString(R.string.URL_BASE);

        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.newPassword);
        password2 = (EditText) findViewById(R.id.password2);
        genero = (Spinner) findViewById(R.id.spinner_profile);
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
            }
        });
    }

    public void Registro()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando Usuario");
        progressDialog.show();

        APIservice apiService = retrofit.create(APIservice.class);

        boolean sex;
        if (genero.getSelectedItem().equals("Hombre")) { sex = true; }
        else{ sex = false; }

        //JSON que enviamos al servidor
        UsuarioJSON user = new UsuarioJSON(usuario.getText().toString().toLowerCase(),
                                           password.getText().toString(),
                                           email.getText().toString().toLowerCase(),
                                           sex);

        Call<KeyUser> registro = apiService.registro(user);
        registro.enqueue(new Callback<KeyUser>()
        {
            @Override
            public void onResponse(Call<KeyUser> registro, Response<KeyUser> response)
            {
                progressDialog.dismiss();
                String text;
                int  key = response.body().getKey();

                if(key == 0)
                {
                    text = "Usuario registrado correctamente";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }

                else if(key == 1){

                    text = "Usuario ya existe";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();

                }
                else
                {
                    text = "Error al registrarse";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }

                //se pueden hacer codigos de errores
            }

            @Override
            public void onFailure(Call<KeyUser> registro, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}