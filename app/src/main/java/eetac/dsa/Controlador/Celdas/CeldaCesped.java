package eetac.dsa.Controlador.Celdas;

import eetac.dsa.Controlador.Celda;
import eetac.dsa.Controlador.Personaje;


public class CeldaCesped extends Celda {
    public CeldaCesped() {
        super(CeldaCesped.class.getSimpleName(), 0.2f, 0.2f, true);
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
