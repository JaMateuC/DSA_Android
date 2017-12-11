package eetac.dsa.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import eetac.dsa.R;
import eetac.dsa.model.Usuario123;
import eetac.dsa.model.UsuarioJSON;

public class IniciarSesion extends AppCompatActivity
{
    //tokken loggin autoritzacio
    //loggin, si o no y un token, desprs get del ususario complet

    private int key;    //Key de autentificación con el servidor
    Button perfil;
    Button partidas;
    Button ranking;
    Button logout;
    UsuarioJSON user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        Bundle intentdata = getIntent().getExtras();
        key = (int) intentdata.getSerializable("key");

        perfil = (Button) findViewById(R.id.Perfil);
        logout = (Button) findViewById(R.id.Logout);

        perfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {/*
                Intent intent= new Intent(view.getContext(), Perfil.class);
                intent.putExtra("usuario", user);
                startActivityForResult(intent, 2);
                */
            }
        });

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}
