package eetac.dsa.dsa.Servidor.Controlador.Celdas;

import eetac.dsa.dsa.Flag;
import eetac.dsa.dsa.Servidor.Controlador.Celda;
import eetac.dsa.dsa.Servidor.Controlador.Personaje;
import eetac.dsa.dsa.Servidor.MundoControlador;
import eetac.dsa.dsa.Servidor.ResultadoServidor;
import eetac.dsa.dsa.Servidor.Sesion;

public class CeldaCambioEscenario extends Celda {

    String escenario;
    int x;
    int y;

    public CeldaCambioEscenario(String escenario,int x,int y) {
        super(CeldaCambioEscenario.class.getSimpleName(), 0, 0, true);
        this.x = x;
        this.y = y;
        this.escenario = escenario;
    }

    @Override
    public boolean accionEncima(Personaje personaje, ResultadoServidor rel) {
        Sesion sesion = MundoControlador.getInstance().getSesion(personaje.getNombre());

        if(sesion!=null)
        {
            sesion.cargarEscenarioFichero(escenario);
            rel.getFlag().addFlag(Flag.cargarEscenario);
            rel.setEscenario(sesion.getEscenario());

            return true;
        }
        return false;
    }

    @Override
    public boolean accionActivar(Personaje activador,ResultadoServidor rel) {
        return false;
    }
}
