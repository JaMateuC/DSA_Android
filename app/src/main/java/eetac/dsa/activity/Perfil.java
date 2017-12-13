package eetac.dsa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import eetac.dsa.R;
import eetac.dsa.model.UsuarioJSON;

public class Perfil extends AppCompatActivity {

    private String BASE_URL;
    UsuarioJSON user;
    EditText email;
    EditText pass;
    EditText genero;
    EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Recoge los valores de la actividad anterior
        Bundle intentdata = getIntent().getExtras();
        BASE_URL = (String) intentdata.getSerializable("URL");
        user = (UsuarioJSON) intentdata.getSerializable("usuario");

        email = (EditText) findViewById(R.id.Email);
        pass = (EditText) findViewById(R.id.password);
        genero = (EditText) findViewById(R.id.Genero);
        nombre= (EditText) findViewById(R.id.Nombre);

        nombre.setText(user.getNombre());
        email.setText(user.getEmail());
        pass.setText(user.getPassword());
        if(user.isGenero())
            genero.setText("hombre");
        else
            genero.setText("mujer");


    }
}
