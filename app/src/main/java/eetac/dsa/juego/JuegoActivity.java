package eetac.dsa.juego;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import eetac.dsa.juego.Controlador.Usuario;
import eetac.dsa.R;
import eetac.dsa.juego.root.ConexionServidor;
import eetac.dsa.juego.root.Mundo;
import eetac.dsa.juego.vista.JuegoView;
import eetac.dsa.model.UsuarioJSON;

public class JuegoActivity extends AppCompatActivity{
    int key;
    //TODO es te que pasar la ket com a "key"
    Mundo mundo;

    RestClient client;

    JuegoView juegoView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        mundo = Mundo.getIns();
        int key = getIntent().getExtras().getInt("key");

        juegoView = (JuegoView)findViewById(R.id.juego_view);

        Button down = (Button)findViewById(R.id.button_down);
        Button up = (Button)findViewById(R.id.button_up);
        Button left = (Button)findViewById(R.id.button_left);
        Button right = (Button)findViewById(R.id.button_right);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.mover(0,+1);
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.mover(0,-1);
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.mover(-1,0);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mundo.mover(+1,0);
            }
        });
        
        client = new RestClient(key,this,mundo);

        mundo.init(key, new ConexionServidor()
        {
            @Override
            public void cambiarMapa(int key, String nombreEscenario, int x, int y)
            {
                client.cambiarMapa(key,nombreEscenario,x,y);
            }

            @Override
            public void updateUsuario(int key, String nombreEscenario, Usuario usuario) {
                try {
                    UsuarioJSON usuarioJSON = new UsuarioJSON();
                    usuarioJSON.fromUsuario(usuario);
                    client.guardar(key,nombreEscenario,usuarioJSON);

                }
                catch (Exception e)
                {
                    Log.e("saveUser","error al pasar de usuario a json cuando se guarda");
                }

            }

            @Override
            public void getLoginArgs(int key) {
                client.getLoginArgs(key);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        juegoView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        juegoView.pause();
    }

}