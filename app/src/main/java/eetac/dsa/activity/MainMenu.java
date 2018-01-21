package eetac.dsa.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import eetac.dsa.R;
import eetac.dsa.juego.JuegoActivity;
import eetac.dsa.juego.RestClient;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private ProgressDialog progressDialog;
    private static Retrofit retrofit = null;
    private String BASE_URL;
    private int key;    //Key de autentificación con el servidor
    UsuarioJSON user;
    TextView id_name;
    ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        BASE_URL = getString(R.string.URL_BASE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recoge los valores de la actividad anterior
        Bundle intentdata = getIntent().getExtras();
        key = (int) intentdata.getSerializable("key");
        user = (UsuarioJSON)intentdata.getSerializable("usuario");

        //Boton para abrir web
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://147.83.7.205:8080/myapp/web"));
                startActivity(launchBrowser);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Escribe el nombre del usuario en el menú lateral
        View headerView = navigationView.getHeaderView(0);

        id_name = (TextView) headerView.findViewById(R.id.textView_idUser);
        id_name.setText(user.getNombre());

        avatar = (ImageView) headerView.findViewById(R.id.avatar);

        getPerfil();

        Button btnJugar = (Button)findViewById(R.id.btnJugar);
        btnJugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainMenu.this, JuegoActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {  drawer.closeDrawer(GravityCompat.START);  }
        else {  super.onBackPressed();  }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil)
        {
            Intent intent= new Intent(this, Perfil.class);
            intent.putExtra("usuario", user);
            startActivity(intent);
        }

        else if (id == R.id.nav_monstruos)
        {
            Intent intent= new Intent(this, ListaMonstruos.class);
            intent.putExtra("usuario", user);
            startActivity(intent);
        }

        else if (id == R.id.nav_ranking)
        {
            Intent intent= new Intent(this, Ranking.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_inventario)
        {
            Intent intent= new Intent(this, Inventario.class);
            intent.putExtra("usuario", user);
            startActivity(intent);
        }

        else if (id == R.id.nav_logout)
        {
            //Crea una ventana emergente
            AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);

            builder.setTitle("Cerrar Sessión");
            builder.setMessage("¿Quieres cerrar la sessión actual?\nVolverás al menú de inicio");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("username", "");
                    editor.putString("password", "");
                    editor.putInt("key", -1);
                    editor.apply();

                    cerrarSesion();

                    Intent intent= new Intent(MainMenu.this, Main.class);
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

        else if (id == R.id.nav_share)
        {
            Intent intent= new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        cerrarSesion();
    }

    public void getPerfil()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");
        progressDialog.show();

        APIservice apiService = retrofit.create(APIservice.class);

        //Recoge los valores de la actividad anterior
        Bundle intentdata = getIntent().getExtras();

        Call<UsuarioJSON> profile = apiService.profile(((UsuarioJSON) intentdata.getSerializable("usuario")).getNombre());
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

                if(response.body().isGenero()){  avatar.setImageResource(R.drawable.avatar_hombre);  }
                else {  avatar.setImageResource(R.drawable.avatar_mujer);  }
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

    public void cerrarSesion()
    {
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