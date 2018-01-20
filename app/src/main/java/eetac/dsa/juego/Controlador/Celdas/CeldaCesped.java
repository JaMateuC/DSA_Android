package eetac.dsa.juego.Controlador.Celdas;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Personaje;


public class CeldaCesped extends Celda {
    public CeldaCesped() {
        super(CeldaCesped.class.getSimpleName(), 0.2f, 0.02f, true);
    }

    public CeldaCesped(int restriccion) {
        super(CeldaCesped.class.getSimpleName(), 0.2f, 0.02f, true,restriccion);
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
