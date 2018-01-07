package eetac.dsa.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eetac.dsa.R;
import eetac.dsa.model.KeyUser;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.model.querysclient.QueryUpdateUsuario;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Perfil extends AppCompatActivity
{
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
    Button save;
    Button delete;

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
        save = (Button) findViewById(R.id.guardar);
        delete = (Button) findViewById(R.id.eliminar);

        getPerfil();

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Crea una ventana emergente
                AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);

                builder.setTitle("Modificar Perfil");
                builder.setMessage("¿Quieres guardar los cambios realizados?\nEsta acción no se puede deshacer");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //   update();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Crea una ventana emergente
                AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);

                builder.setTitle("Eliminar Usuario");
                builder.setMessage("¿Quieres dar de baja a este usuario?\nEsta acción no se puede deshacer");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //Falta realizar la petición al servidor para eliminar el usuario
                        //Si la respuesta es OK hace las siguientes lineas

                        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpref.edit();
                        editor.putString("username", "");
                        editor.putString("password", "");
                        editor.putInt("key", -1);
                        editor.apply();

                        Intent intent = new Intent(Perfil.this, Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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
        progressDialog.setMessage("Cargando perfil");
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

                if(response.body() == null)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Els servidor no ha dado respuesta", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

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

    public void update()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando");
        progressDialog.show();

        APIservice apiService = retrofit.create(APIservice.class);

        //JSON que enviamos al servidor
        UsuarioJSON user = new UsuarioJSON(nombre.getText().toString(), newpass.getText().toString(),
                                           email.getText().toString(), genero.isActivated());

        QueryUpdateUsuario querry = new QueryUpdateUsuario();
        querry.setKey(0);
        querry.setUsuarioJson(user);
        //querry.setNomEscenari();

        Call<KeyUser> updateUser = apiService.profile_update(querry);
        updateUser.enqueue(new Callback<KeyUser>()
        {
            @Override
            public void onResponse(Call<KeyUser> updateUser, Response<KeyUser> response)
            {
                progressDialog.dismiss();
                String text;
                int  key = response.body().getKey();

                if(key == 0)
                {
                    text = "Cambios realizados correctamente";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }

                else
                {
                    text = "Error al guardar";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
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