package eetac.dsa.juego.Controlador.Celdas;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Personaje;

/**
 * Created by oscar on 21/01/2018.
 */

public class CeldaAgua extends Celda {
    public CeldaAgua() {
        super("CeldaAgua", 0, 0, false);
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
