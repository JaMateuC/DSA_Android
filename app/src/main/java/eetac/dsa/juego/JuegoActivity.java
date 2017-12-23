package eetac.dsa.juego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import eetac.dsa.juego.Controlador.Usuario;
import eetac.dsa.R;
import eetac.dsa.juego.root.ConexionServidor;
import eetac.dsa.juego.root.Mundo;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.model.querysclient.QueryCambiarEscenario;
import eetac.dsa.model.querysclient.QueryUpdateUsuario;
import eetac.dsa.model.resultsserver.ResultCambiarEscenario;
import eetac.dsa.model.resultsserver.ResultLoginArgs;
import eetac.dsa.model.resultsserver.ResultadoAceptar;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JuegoActivity extends AppCompatActivity{
    int key;
    //TODO es te que pasar la ket com a "key"
    Mundo mundo;

    RestClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        mundo = Mundo.getIns();
        int key = getIntent().getExtras().getInt("key");
        
        client = new RestClient(key,this,mundo);

        mundo.init(key, new ConexionServidor() {
            @Override
            public void cambiarMapa(int key, String nombreEscenario, int x, int y) {
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

}


