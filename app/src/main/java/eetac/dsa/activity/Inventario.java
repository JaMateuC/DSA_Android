package eetac.dsa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eetac.dsa.R;

public class Inventario extends AppCompatActivity
{
    private String BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        BASE_URL = getString(R.string.URL_BASE);
    }
}