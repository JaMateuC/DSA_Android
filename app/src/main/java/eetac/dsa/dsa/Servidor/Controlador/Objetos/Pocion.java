package eetac.dsa.dsa.Servidor.Controlador.Objetos;

import eetac.dsa.dsa.Servidor.Controlador.Monstruo;
import eetac.dsa.dsa.Servidor.Controlador.Objeto;
import eetac.dsa.dsa.Servidor.Controlador.Personaje;
import eetac.dsa.dsa.Servidor.ResultadoServidor;

public class Pocion extends Objeto
{

    int vida;

    public Pocion(String nombre,int vida) {
        super(nombre, "perimite recuperar "+vida+" puntos de salud de un monstruo","Pocion",Destino.Monstruo);
        this.vida = vida;
    }


    @OrdenParametro(indice = 1)
    public int getVida() {
        return vida;
    }

    @Override
    @OrdenParametro(indice=0)
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public void funcion(Monstruo monstruo, ResultadoServidor rel) {
        monstruo.añadirVida(vida);
    }

    @Override
    public void funcion(Personaje personaje, ResultadoServidor rel) {

    }
}
