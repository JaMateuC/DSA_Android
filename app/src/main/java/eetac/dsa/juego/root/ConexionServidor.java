package eetac.dsa.juego.root;

import eetac.dsa.juego.Controlador.Usuario;

/**
 * Created by oscar on 23/12/2017.
 */

public interface ConexionServidor
{
    void cambiarMapa(int key,String nombreEscenario,int x,int y);
    void updateUsuario(int key,String nombreEscenario,Usuario usuario);
    void getLoginArgs(int key);
}