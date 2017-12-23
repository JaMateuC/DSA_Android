package eetac.dsa.juego.Controlador.Objetos;

import eetac.dsa.juego.Controlador.Monstruo;
import eetac.dsa.juego.Controlador.Objeto;
import eetac.dsa.juego.Controlador.Personaje;


public class PocionExperiencia extends Objeto {
    public PocionExperiencia(String nombre,int experienciaExtra) {
        super(nombre, "añade experiencia a un monstruo dado","Pocion de Experiencia",Destino.Monstruo);
        this.experienciaExtra = experienciaExtra;
    }

    int experienciaExtra;


    @Override
    @OrdenParametro(indice = 0)
    public String getNombre() {
        return super.getNombre();
    }

    @OrdenParametro(indice = 1)
    public int getExperienciaExtra() {
        return experienciaExtra;
    }

    @Override
    public void funcion(Personaje personaje) {

    }

    @Override
    public void funcion(Monstruo monstruo) {
        monstruo.añadirExperiencia(experienciaExtra);
    }
}
