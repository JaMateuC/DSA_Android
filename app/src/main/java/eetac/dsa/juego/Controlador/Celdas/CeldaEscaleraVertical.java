package eetac.dsa.juego.Controlador.Celdas;

import android.util.Log;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Personaje;
import eetac.dsa.juego.root.Mundo;

/**
 * Created by oscar on 21/01/2018.
 */

public class CeldaEscaleraVertical extends Celda {
    int x;
    int y;
    String escenario;
    boolean cambio;

    public CeldaEscaleraVertical() {
        super("CeldaEscaleraVertical", 0, 0, true, REST_LEFT+REST_RIGHT);
        cambio = false;
    }

    public CeldaEscaleraVertical(String escenario,int x,int y) {
        super("CeldaEscaleraVertical", 0, 0, true, REST_LEFT+REST_RIGHT);
        this.escenario = escenario;
        this.x = x;
        this.y = y;
        cambio = true;
    }

    @Override
    public boolean accionEncima(Personaje personaje) {

        if(!cambio)return true;
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
