package eetac.dsa.juego.root;

import android.util.Log;

import java.util.Random;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Escenario;
import eetac.dsa.juego.Controlador.Monstruo;
import eetac.dsa.juego.Controlador.Objeto;
import eetac.dsa.juego.Controlador.Usuario;
import eetac.dsa.model.EscenarioJSON;
import eetac.dsa.model.MonstruoJSON;
import eetac.dsa.model.ObjetoJSON;
import eetac.dsa.model.resultsserver.ResultLoginArgs;

/**
 * Created by oscar on 20/12/2017.
 */

public class Mundo implements ResponseRest , AccionesMapa{
    Usuario usuario;
    Escenario escenario;
    ConexionServidor conexionServidor;
    int key;
    static Mundo mundo;
    FSM estado;

    MonstruoJSON[][] monstruosEncontrables;
    ObjetoJSON[][] objetosEncontrables;



    public void init(int key, ConexionServidor conexionServidor){
        if(estado==FSM.init) {
            this.conexionServidor = conexionServidor;
            this.key = key;
            conexionServidor.getLoginArgs(key);
            estado = FSM.waitLoginArgs;
            Log.d("Estado","waitLoginArgs");
        }

    }

    public Monstruo getRandomMonstruo() throws Exception
    {
        Random random = new Random();
        int indice = (int)random.nextFloat()*monstruosEncontrables[escenario.getNivelDeZona()].length;
        return monstruosEncontrables[escenario.getNivelDeZona()][indice].toMonstruo();
    }


    public Objeto getRandomObjeto() throws Exception
    {
        Random random = new Random();
        int indice = (int)random.nextFloat()*objetosEncontrables[escenario.getNivelDeZona()].length;
        return objetosEncontrables[escenario.getNivelDeZona()][indice].toObjeto();
    }

    public void mover(int x,int y)
    {
        if(estado==FSM.play)
            usuario.mover(x,y);
    }

    private Mundo(){estado = FSM.init;};

    public synchronized void onCambiarMapa(EscenarioJSON escenarioJSON,int x,int y)
    {
        if(estado==FSM.waitMapa)
        {
            try {
                if(escenarioJSON==null) throw new Exception();
                escenario = escenarioJSON.toEscenario();
                usuario.setPosicion(x,y);
                estado = FSM.play;
                Log.d("Estado","play");
            }
            catch (Exception e)
            {
                Log.e("Mapa","error ar recivir el mapa");
            }

        }
    }

    public synchronized void onGetLogingArgs(ResultLoginArgs resultLoginArgs)
    {
        if(estado==FSM.waitLoginArgs)
        {
            try {
                escenario = resultLoginArgs.getEscenarioJSON().toEscenario();
                usuario = resultLoginArgs.getUsuarioJSON().toUsario();
                estado = FSM.play;
                Log.d("Estado","play");
            }
            catch (Exception e)
            {
                Log.e("Init","error al cargar los datos del login");
            }
        }
    }


    public static Mundo getIns()
    {
        if(mundo==null)
            mundo = new Mundo();
        return mundo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Escenario getEscenario() {
        return escenario;
    }

    public Celda getCelda(int x, int y)
    {
        return escenario.getCelda(x,y);
    }

    public void updateUsuario()
    {
        conexionServidor.updateUsuario(key,escenario.getNombre(),usuario);
    }

    public void cambiarMapa(String nombreEscenario,int x,int y)
    {
        if(estado==FSM.play)
        {
            conexionServidor.cambiarMapa(key,nombreEscenario,x,y);
            estado= FSM.waitMapa;
            Log.d("Estado","waitMapa");
        }
    }

    public FSM getEstado() {
        return estado;
    }

    public enum FSM{
        init, waitLoginArgs,play,waitMapa
    }
}
