package eetac.dsa.Controlador.Celdas;


import android.util.Log;

import eetac.dsa.Controlador.Celda;
import eetac.dsa.Controlador.Mundo;
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
        try {
            Mundo.getIns().cambiarMapa(escenario, x, y);
            Mundo.getIns().updateUsuario();
        }
        catch (Exception e)
        {
           Log.d("celdaCambioEscenario","error al cambiar el escenario");
        }
        return true;
    }


    @Override
    public boolean accionActivar(Personaje activador) {
        return false;
    }
}
