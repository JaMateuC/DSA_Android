package eetac.dsa.juego.Controlador.Celdas;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Personaje;

/**
 * Created by oscar on 21/01/2018.
 */

public class CeldaCamino extends Celda {
    public CeldaCamino() {
        super("CeldaCamino", 0, 0.05f, true);
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
