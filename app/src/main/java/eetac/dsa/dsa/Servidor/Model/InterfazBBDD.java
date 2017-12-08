package eetac.dsa.dsa.Servidor.Model;

import eetac.dsa.dsa.Servidor.Controlador.Escenario;
import eetac.dsa.dsa.Servidor.Controlador.Monstruo;
import eetac.dsa.dsa.Servidor.Controlador.Objeto;
import eetac.dsa.dsa.Servidor.Controlador.Usuario;

import java.util.Vector;

public interface InterfazBBDD
{
    Usuario getUsuario(String nombre);

    Vector<Objeto> getlistobjetos(String nombre);

    Vector<Monstruo> getlistamonstruo(String nombre);

    Escenario getescenario(String nombreEscenario);
}
