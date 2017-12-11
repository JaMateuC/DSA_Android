package eetac.dsa.Controlador.Celdas;


import eetac.dsa.Controlador.Celda;
import eetac.dsa.Controlador.Personaje;



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
    public boolean accionEncima(Personaje personaje) {
       // Sesion sesion = MundoControlador.getInstance().getSesion(personaje.getNombre());

        /*if(sesion!=null)
        {
            sesion.cargarEscenarioFichero(escenario);
            rel.getFlag().addFlag(Flag.cargarEscenario);
            rel.setEscenario(sesion.getEscenario());
            sesion.getProtagonista().mover(x,y,rel);
            return true;
        }
        return false;
        */
        return false;
    }


    @Override
    public boolean accionActivar(Personaje activador) {
        return false;
    }
}
