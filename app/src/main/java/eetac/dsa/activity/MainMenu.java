package eetac.dsa.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import eetac.dsa.R;
import eetac.dsa.juego.JuegoActivity;
import eetac.dsa.model.UsuarioJSON;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private String BASE_URL;
    private int key;    //Key de autentificación con el servidor
    UsuarioJSON user;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnJugar = (Button)findViewById(R.id.btnJugar);
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {  return true;  }

        return super.onOptionsItemSelected(item);
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
                    SharedPreferences sharedpref= getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("username", "");
                    editor.putString("password", "");
                    editor.putInt("key",key);
                    editor.apply();

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

        //No están implementados estos 2 botones del menú
        else if (id == R.id.nav_share)
        {
            Intent intent= new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}