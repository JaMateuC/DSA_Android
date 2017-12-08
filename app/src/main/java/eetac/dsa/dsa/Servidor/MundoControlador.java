package eetac.dsa.dsa.Servidor;

import eetac.dsa.dsa.Flag;
import eetac.dsa.dsa.Servidor.Controlador.Escenario;
import eetac.dsa.dsa.Servidor.Controlador.Monstruos.MonstruoEjemplo;
import eetac.dsa.dsa.Servidor.Controlador.Personaje;
import eetac.dsa.dsa.Servidor.Controlador.Usuario;

import java.util.HashMap;

public class MundoControlador {
    HashMap<String,Sesion> sesiones;

    static MundoControlador mundoControlador;

    static public MundoControlador getInstance() {
        if(mundoControlador==null)
            mundoControlador = new MundoControlador();
        return mundoControlador;
    }

    private MundoControlador() {
        sesiones = new HashMap<>();
    }

    public Sesion getSesion(String nombreusuario)
    {
        return sesiones.get(nombreusuario);
    }

    public ResultadoServidor loggin(String nombre,String password)
    {
        if(nombre.equals("oscar")&&password.equals("oscar"))
        {
            Usuario oscar = new Usuario("oscar",1,1,true,"oscar","oscar");
            oscar.añadirMonstruo(new MonstruoEjemplo(1,1));
            oscar.añadirMonstruo(new MonstruoEjemplo(2,2));
            Sesion sesion = new Sesion(oscar,"Escenario1");
            mundoControlador.sesiones.put("oscar",sesion);

            ResultadoServidor resultadoServidor = new ResultadoServidor();
            resultadoServidor.getFlag().addFlag(Flag.cargarEscenario);
            resultadoServidor.getFlag().addFlag(Flag.cargarProtagonista);
            resultadoServidor.setEscenario(sesion.getEscenario());
            resultadoServidor.setProtagonista(oscar);
            return resultadoServidor;
        }

        ResultadoServidor resultadoServidor = new ResultadoServidor();
        resultadoServidor.getFlag().addFlag(Flag.ERROR_LOGGING);
        return resultadoServidor;
    }
}
