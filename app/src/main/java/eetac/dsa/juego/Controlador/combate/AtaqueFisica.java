package eetac.dsa.juego.Controlador.combate;


import eetac.dsa.juego.Controlador.Ataque;
import eetac.dsa.juego.Controlador.Monstruo;

public class AtaqueFisica extends Ataque {
    /**
     * PorDefecto: Se ejecuta el ataque si existe contacto entre los monstruos
     * SoloFisica: El atacante varia los atributos fisicos, no hace daño a otro enemigo
     * FisicaLuegoAtaque: El atacante varia los atributos fisicos, luego si hace contacto dentro de un tiempo hace daño al enemigo
     */
    public enum Comportamiento{PorDefecto,SoloFisica,FisicaLuegoAtaque}

    protected Comportamiento comportamiento;

    float duracionAtaque;


    public AtaqueFisica(String nombre, float multiplicador,float duracionAtaque) {
        super(nombre, multiplicador);
        this.tipo = Tipo.fisico;
        comportamiento = Comportamiento.PorDefecto;
        this.duracionAtaque = duracionAtaque;
    }


    public Comportamiento getComportamiento() {
        return comportamiento;
    }

    public float getDuracionAtaque() {
        return duracionAtaque;
    }


    /**
     * funcion prevista para possibles efectos especiales de un ataque a un enemigo
     * @param enemigo enemigo el cual sera afectado por el extra
     */
    public void extraEnemigo(Monstruo enemigo)
    {

    }

    /**
     * funcion prevista para afectar al atacante
     * @param atacante
     */
    public void extraAtacante(Monstruo atacante)
    {

    }

    /**
     * metodo para modificar los atributos fisicos del monstruo, se ejecuta al hacer contacto con el enemigo
     * @param atacante monstruo que ejecuta el ataque
     */
    public void extraAtacanteFisica(MonstruoEntidad atacante)
    {
    }

    /**
     * metodo para modificar los atributos fisicos del monstruo, se ejecuta al hacer contacto con el enemigo
     * @param enemigo monstruo que recibe el ataque
     */
    public void extraEnemigoFisica(MonstruoEntidad enemigo)
    {

    }

    /**
     * callback que se debe usar si el comportamiento es SoloFisica o FisicaLuegoAtaque
     * @param atacante
     */
    public void soloFisica(MonstruoEntidad atacante)
    {

    }
}
