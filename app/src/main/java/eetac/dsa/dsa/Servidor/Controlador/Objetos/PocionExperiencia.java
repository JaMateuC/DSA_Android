package eetac.dsa.dsa.Servidor.Controlador.Objetos;

import eetac.dsa.dsa.Servidor.Controlador.Monstruo;
import eetac.dsa.dsa.Servidor.Controlador.Objeto;
import eetac.dsa.dsa.Servidor.Controlador.Personaje;
import eetac.dsa.dsa.Servidor.ResultadoServidor;

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
    public void funcion(Personaje personaje, ResultadoServidor rel) {

    }

    @Override
    public void funcion(Monstruo monstruo, ResultadoServidor rel) {
        monstruo.añadirExperiencia(experienciaExtra);
    }
}
