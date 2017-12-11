package eetac.dsa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import eetac.dsa.R;
import eetac.dsa.model.Usuario123;
import eetac.dsa.model.UsuarioJSON;

/**
 * Created by JesusLigero on 10/12/2017.
 */

public class Perfil extends AppCompatActivity {

    UsuarioJSON user;
    EditText email;
    EditText pass;
    EditText genero;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Bundle intentdata = getIntent().getExtras();
        user = (UsuarioJSON) intentdata.getSerializable("usuario");
        email = (EditText) findViewById(R.id.Email);
        pass = (EditText) findViewById(R.id.password);
        genero = (EditText) findViewById(R.id.Genero);


        email.setText(user.getEmail());
        pass.setText(user.getPassword());
        if(user.isGenero())
            genero.setText("hombre");
        else
            genero.setText("mujer");

    }
}
