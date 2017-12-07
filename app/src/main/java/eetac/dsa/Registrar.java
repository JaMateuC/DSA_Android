package eetac.dsa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registrar extends AppCompatActivity
{
    private static final String URL_BASE = "https://api.github.com/";
    private Retrofit retrofit=null;

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

                connectAPIservice();
            }
        });
    }

    public void connectAPIservice ()
    {
        if (this.retrofit == null)
        {
            this.retrofit = new Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);
        Call<Integer> post = apiService.registroUser("Albert");

        post.enqueue(new Callback<Integer>()
        {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                Toast toast = Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {
                Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
