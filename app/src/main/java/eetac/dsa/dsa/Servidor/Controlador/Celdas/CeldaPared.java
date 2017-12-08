package eetac.dsa.dsa.Servidor.Controlador.Celdas;

import eetac.dsa.dsa.Servidor.Controlador.Celda;
import eetac.dsa.dsa.Servidor.Controlador.Personaje;
import eetac.dsa.dsa.Servidor.ResultadoServidor;

public class CeldaPared extends Celda {

    public CeldaPared() {
        super(CeldaPared.class.getSimpleName(), 0, 0, false);
    }

    @Override
    public boolean accionEncima(Personaje personaje, ResultadoServidor rel) {
        return false;
    }

    @Override
    public boolean accionActivar(Personaje activador,ResultadoServidor rel) {
        return false;
    }
}
