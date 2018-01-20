package eetac.dsa.juego.Controlador.Celdas;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Personaje;

/**
 * Created by oscar on 19/01/2018.
 */

public class CeldaFueraEscenario extends Celda {
    public CeldaFueraEscenario() {
        super("CeldaFueraEscenario", 0, 0, false);
    }

    @Override
    public boolean accionEncima(Personaje personaje) {
        return false;
    }

    @Override
    public boolean accionActivar(Personaje activador) {
        return false;
    }
}
