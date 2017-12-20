package eetac.dsa.Controlador;

import eetac.dsa.model.EscenarioJSON;
import eetac.dsa.model.UsuarioJSON;

/**
 * Created by oscar on 20/12/2017.
 */

public class Mundo {
    Usuario usuario;
    Escenario escenario;
    ConexionServidor conexionServidor;
    int key;
    static Mundo mundo;

    public void init(int key, ConexionServidor conexionServidor) throws Exception {
        this.conexionServidor = conexionServidor;
        escenario = conexionServidor.cambiarMapa(key).toEscenario();
        usuario = conexionServidor.getUsuario(key).toUsario();
        this.key = key;
    }

    private Mundo(){};

    static Mundo getIns()
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

    interface ConexionServidor
    {
        void updateUsuario(UsuarioJSON usuarioJSON);
        EscenarioJSON cambiarMapa(int key);
        UsuarioJSON getUsuario(int key);
    }
}
