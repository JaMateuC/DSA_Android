package eetac.dsa.activity;

import android.content.Intent;
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
import android.widget.TextView;

import eetac.dsa.R;
import eetac.dsa.model.UsuarioJSON;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private String BASE_URL;
    private int key;            //Key de autentificación con el servidor
    UsuarioJSON user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recoge los valores de la actividad anterior
        Bundle intentdata = getIntent().getExtras();
        BASE_URL = (String) intentdata.getSerializable("URL");
        key = (int) intentdata.getSerializable("key");
        user = (UsuarioJSON)intentdata.getSerializable("usuario");

        //Modificar usuario de la barra lateral
/*
        TextView user_nav = (TextView) findViewById(R.id.nav_user);
        TextView email_nav = (TextView) findViewById(R.id.nav_email);

        user_nav.setText(user.getNombre());
        email_nav.setText(user.getEmail());
*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        //Manejar los ítems de vista de navegación aquí
        int id = item.getItemId();

        if (id == R.id.nav_perfil)
        {
            Intent intent= new Intent(this, Perfil.class);
            intent.putExtra("URL", BASE_URL);
            intent.putExtra("key", key);
            intent.putExtra("usuario", user);
            startActivityForResult(intent, 2);
        }

        else if (id == R.id.nav_monstruos)
        {
            Intent intent= new Intent(this, ListaMonstruos.class);
            intent.putExtra("URL", BASE_URL);
            intent.putExtra("key", key);
            intent.putExtra("usuario", user);
            startActivityForResult(intent, 3);
        }

        else if (id == R.id.nav_ranking) {  }

        else if (id == R.id.nav_logout) {  finish();  }

        //No están implementados estos 2 botones del menú
        else if (id == R.id.nav_share) {  }
        else if (id == R.id.nav_send) {  }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}