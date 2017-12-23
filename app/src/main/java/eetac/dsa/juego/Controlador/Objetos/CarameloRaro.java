package eetac.dsa.juego.Controlador.Objetos;

import eetac.dsa.juego.Controlador.Monstruo;
import eetac.dsa.juego.Controlador.Objeto;
import eetac.dsa.juego.Controlador.Personaje;


public class CarameloRaro extends Objeto{

    public CarameloRaro() {
        super("Caramelo Raro","Sube al monstruo 1 nivel", "Caramelo",Destino.Monstruo);
    }

    @Override
    public void funcion(Personaje personaje) {

    }

    @Override
    public void funcion(Monstruo monstruo) {
        monstruo.setNivel(monstruo.getNivel()+1);
    }
}
