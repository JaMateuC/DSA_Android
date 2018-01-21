package eetac.dsa.juego.Controlador.Celdas;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Personaje;

/**
 * Created by oscar on 21/01/2018.
 */

public class CeldaArena extends Celda {
    public CeldaArena() {
        super("CeldaArena", 0.01f, 0.05f, true);
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
