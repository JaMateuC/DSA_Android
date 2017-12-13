package eetac.dsa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eetac.dsa.R;
import eetac.dsa.rest.APIclient;

public class EmptyActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        int key;

        //Inicia sesión automaticamente
        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        if(!sharedpref.getString("username", "").equals(""))
        {
            APIclient client = new APIclient();
            key = client.IniciarSesion(sharedpref.getString("username", ""), sharedpref.getString("password", ""));

            if(key == 0)
            {
                Intent intent = new Intent(this, Main.class);
                startActivity(intent);
            }

            else
            {
                Intent intent = new Intent(this, MainMenu.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        }

        else
        {
            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
        }

    }
}