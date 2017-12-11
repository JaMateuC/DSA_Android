package eetac.dsa.Controlador.Celdas;

import eetac.dsa.Controlador.Celda;
import eetac.dsa.Controlador.Personaje;


public class CeldaPared extends Celda {

    public CeldaPared() {
        super(CeldaPared.class.getSimpleName(), 0, 0, false);
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
