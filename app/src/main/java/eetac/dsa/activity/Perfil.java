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

public class Perfil extends AppCompatActivity
{
    private ProgressDialog progressDialog;
    private static Retrofit retrofit = null;
    private static String BASE_URL;

    UsuarioJSON user;
    UsuarioJSON usuario;
    int key;

    EditText nombre;
    EditText oldpass;
    EditText newpass;
    EditText email;
    Spinner  genero;
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
        genero = (Spinner) findViewById(R.id.spinner_profile);
        save = (Button) findViewById(R.id.guardar);
        delete = (Button) findViewById(R.id.eliminar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genero.setAdapter(adapter);


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
                        if(oldpass.getText().toString().equals(usuario.getPassword()))
                        {
                            update();   //Actualiza el usuario
                        }

                        else
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT);
                            toast.show();
                        }
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
                        if(oldpass.getText().toString().equals(usuario.getPassword()))
                        {
                            delete();   //Elimina el usuario
                        }

                        else
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT);
                            toast.show();
                        }
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

                user = response.body();     //Guarda todos los parámetros enviados por el servidor

                nombre.setText(user.getNombre());
                email.setText(user.getEmail());

                if(user.isGenero()) genero.setSelection(0);
                else                genero.setSelection(1);
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

        //Campos modificables del usuario
        if(newpass.getText().toString().equals("")){ user.setPassword(oldpass.getText().toString()); }
        else{ user.setPassword(newpass.getText().toString()); }

        user.setEmail(email.getText().toString());

        boolean sex;
        if (genero.getSelectedItem().equals("Hombre")) { sex = true; }
        else{ sex = false; }
        user.setGenero(sex);

        Call<UsuarioJSON> updateUser = apiService.uptadeFields(user);
        updateUser.enqueue(new Callback<UsuarioJSON>()
        {
            @Override
            public void onResponse(Call<UsuarioJSON> updateUser, Response<UsuarioJSON> response)
            {
                progressDialog.dismiss();

                if(response.body()== null)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Els servidor no ha dado respuesta", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                String text;
                if(response.body().getKey() == 0) //El servidor devuelve respuesta afirmativa
                {
                    text = "Cambios realizados correctamente";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();

                    SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("username", response.body().getNombre());
                    editor.putString("password", response.body().getPassword());
                    editor.putInt("key", response.body().getKey());
                    editor.apply();

                    Intent intent = new Intent(Perfil.this, MainMenu.class);
                    intent.putExtra("key", response.body().getKey());
                    intent.putExtra("usuario", response.body());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                else
                {
                    text = "Error al guardar";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioJSON> registro, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void delete()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        progressDialog = new ProgressDialog(Perfil.this);
        progressDialog.setMessage("Eliminando usuario");
        progressDialog.show();

        APIservice apiService = retrofit.create(APIservice.class);

        UsuarioJSON userEliminado = new UsuarioJSON();
            userEliminado.setNombre(nombre.getText().toString());
            userEliminado.setPassword(oldpass.getText().toString());
            userEliminado.setEmail(email.getText().toString());

        Call<KeyUser> eliminar = apiService.deleteUser(userEliminado);

        eliminar.enqueue(new Callback<KeyUser>()
        {
            @Override
            public void onResponse(Call<KeyUser> args, Response<KeyUser> response)
            {
                progressDialog.dismiss();

                if(response.body().getKey() == 1)
                {
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

                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error al dar de baja el usuario", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<KeyUser> args, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        //Cerrar sesion
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        APIservice apiService = retrofit.create(APIservice.class);

        Call<String> loginArgs= apiService.closeSesion(key);
        loginArgs.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> args, Response<String> response)
            {

            }

            @Override
            public void onFailure(Call<String> args, Throwable t)
            {
            }
        });
    }
}