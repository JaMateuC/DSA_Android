package eetac.dsa.Controlador;

import java.util.Random;

import eetac.dsa.model.EscenarioJSON;
import eetac.dsa.model.MonstruoJSON;
import eetac.dsa.model.ObjetoJSON;
import eetac.dsa.model.resultsserver.ResultLoginArgs;

/**
 * Created by oscar on 20/12/2017.
 */

public class Mundo {
    Usuario usuario;
    Escenario escenario;
    ConexionServidor conexionServidor;
    int key;
    static Mundo mundo;

    MonstruoJSON[][] monstruosEncontrables;
    ObjetoJSON[][] objetosEncontrables;

    public void init(int key, ConexionServidor conexionServidor) throws Exception {
        this.conexionServidor = conexionServidor;
        this.key = key;

        ResultLoginArgs resultLoginArgs = conexionServidor.getLoginArgs(key);
        usuario = resultLoginArgs.getUsuarioJSON().toUsario();
        escenario = resultLoginArgs.getEscenarioJSON().toEscenario();

        monstruosEncontrables = conexionServidor.getMonstruosEncontrables();

        objetosEncontrables= conexionServidor.getObjetosEncontrables();
    }

    public Monstruo getRandomMonstruo() throws Exception
    {
        Random random = new Random();
        int indice = (int)random.nextFloat()*monstruosEncontrables[escenario.getNivelDeZona()].length;
        return monstruosEncontrables[escenario.getNivelDeZona()][indice].toMonstruo();
    }

    public Objeto getRadomObjeto() throws Exception
    {
        Random random = new Random();
        int indice = (int)random.nextFloat()*objetosEncontrables[escenario.getNivelDeZona()].length;
        return objetosEncontrables[escenario.getNivelDeZona()][indice].toObjeto();
    }

    public Objeto getRandomObjeto() throws Exception
    {
        Random random = new Random();
        int indice = (int)random.nextFloat()*objetosEncontrables[escenario.getNivelDeZona()].length;
        return objetosEncontrables[escenario.getNivelDeZona()][indice].toObjeto();
    }

    public void mover(int x,int y)
    {
        usuario.mover(x,y);
    }

    private Mundo(){};

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

    Celda getCelda(int x,int y)
    {
        return escenario.getCelda(x,y);
    }

    public void updateUsuario()
    {
        conexionServidor.updateUsuario(key,escenario.getNombre(),usuario);
    }

    public void cambiarMapa(String nombreEscenario,int x,int y) throws Exception
    {
        escenario = conexionServidor.cambiarMapa(key,nombreEscenario,x,y).toEscenario();
        usuario.setPosicion(x,y);
    }

    public interface ConexionServidor
    {
        EscenarioJSON cambiarMapa(int key,String nombreEscenario,int x,int y);
        void updateUsuario(int key,String nombreEscenario,Usuario usuario);
        ResultLoginArgs getLoginArgs(int key);
        MonstruoJSON[][] getMonstruosEncontrables();
        ObjetoJSON[][] getObjetosEncontrables();
    }
}
