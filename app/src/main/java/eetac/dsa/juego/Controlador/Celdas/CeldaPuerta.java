package eetac.dsa.juego.Controlador.Celdas;

import android.util.Log;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Personaje;
import eetac.dsa.juego.root.Mundo;

/**
 * Created by oscar on 21/01/2018.
 */

public class CeldaPuerta extends Celda {
    String escenario;
    int x;
    int y;

    public CeldaPuerta(String escenario, int x, int y) {
        super("CeldaPuerta", 0, 0, true);
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
