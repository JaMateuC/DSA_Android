package eetac.dsa.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import eetac.dsa.Controlador.Mundo;
import eetac.dsa.Controlador.Usuario;
import eetac.dsa.R;
import eetac.dsa.model.EscenarioJSON;
import eetac.dsa.model.KeyUser;
import eetac.dsa.model.MonstruoJSON;
import eetac.dsa.model.ObjetoJSON;
import eetac.dsa.model.UsuarioJSON;
import eetac.dsa.model.resultsserver.ResultLoginArgs;
import eetac.dsa.rest.APIservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JuegoActivity extends AppCompatActivity implements Mundo.ConexionServidor{
    int key;
    //TODO es te que pasar la ket com a "key"
    Mundo mundo;

    private String BASE_URL;
    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        key = getIntent().getExtras().getInt("key");
        BASE_URL = getString(R.string.URL_BASE);

        mundo = Mundo.getIns();
        try {
            mundo.init(key,this);
        }
        catch (Exception e)
        {
            Log.d("Controlador","error en el controlador");
            finish();
        }
    }

    @Override
    public EscenarioJSON cambiarMapa(int key, String nombreEscenario, int x, int y) {
        return null;
    }

    @Override
    public void updateUsuario(int key, String nombreEscenario, Usuario usuario) {

    }

    @Override
    public ResultLoginArgs getLoginArgs(int key) {
        return null;
    }

    @Override
    public MonstruoJSON[][] getMonstruosEncontrables() {
        return new MonstruoJSON[0][];
    }

    @Override
    public ObjetoJSON[][] getObjetosEncontrables() {
        return new ObjetoJSON[0][];
    }
}
