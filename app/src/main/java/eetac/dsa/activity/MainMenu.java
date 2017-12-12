package eetac.dsa.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

import eetac.dsa.R;
import eetac.dsa.model.UsuarioJSON;

public class MainMenu extends AppCompatActivity
{
    //tokken loggin autoritzacio
    //loggin, si o no y un token, desprs get del ususario complet

    private String BASE_URL;
    private int key;    //Key de autentificación con el servidor

    Button perfil;
    Button listaM;
    Button ranking;
    Button logout;
    UsuarioJSON user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        //Recoge los valores de la actividad anterior
        Bundle intentdata = getIntent().getExtras();
        BASE_URL = (String) intentdata.getSerializable("URL");
        key = (int) intentdata.getSerializable("key");
        user = (UsuarioJSON)intentdata.getSerializable("usuario");

        perfil = (Button) findViewById(R.id.Perfil);
        logout = (Button) findViewById(R.id.Logout);

        perfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent= new Intent(view.getContext(), Perfil.class);
                intent.putExtra("URL", BASE_URL);
                intent.putExtra("usuario", user);
                startActivityForResult(intent, 2);

            }
        });

        listaM = (Button) findViewById(R.id.Partidas);
        listaM.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent= new Intent(view.getContext(), ListaMonstruos.class);
                intent.putExtra("URL", BASE_URL);
                intent.putExtra("usuario", user);
                startActivityForResult(intent, 3);

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
