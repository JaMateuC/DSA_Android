package eetac.dsa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import eetac.dsa.R;
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

        /*lista =


        //lista=(ArrayList<String>)getIntent().getSerializableExtra("valor1");

        adaptador= new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, lista);
        ListView lisv = (ListView) findViewById(R.id.Lista1);
        lisv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        */

    }
}
