package eetac.dsa.dsa.Servidor.Controlador;

import eetac.dsa.dsa.Flag;
import eetac.dsa.dsa.Servidor.MundoControlador;
import eetac.dsa.dsa.Servidor.ResultadoServidor;

/**
 * La clase celda es una plantilla para cada conjunto de propiedades que tiene cada una de las posiciones del mapa
 */
public abstract class Celda
{
    String tipo;
    float probObjeto; //probabilidad con la que aparece un objeto en esta celda
    float probMonstruo; //probabilidad con la que aparece un monstruo
    Boolean andable;
    Personaje personajeEncima; //si existe un personaje encima esta propiedad es diferente de null. Solo un personaje por celda.

    public Celda(String tipo, float probObjeto, float probMonstruo, Boolean andable) {
        this.tipo = tipo;
        this.probObjeto = probObjeto;
        this.probMonstruo = probMonstruo;
        this.andable = andable;
    }


    public String getTipo() {
        return tipo;
    }

    public void setAndable(Boolean andable) {
        this.andable = andable;
    }

    public void setPersonajeEncima(Personaje personajeEncima) {
        this.personajeEncima = personajeEncima;
    }

    public Personaje getPersonajeEncima() {
        return personajeEncima;
    }

    public float getProbObjeto() {
        return probObjeto;
    }

    public float getProbMonstruo() {
        return probMonstruo;
    }

    public boolean getAndable() {
        return andable;
    }

    /**
     * funcion miscelanea que se ejecuta cuando un usuario esta encima
     * @return devuelve si la funcion se ha podido ejecutar
     */
    public abstract boolean accionEncima(Personaje personaje, ResultadoServidor rel);

    /**
     * funcion miscelanea que se activa por un personaje delante de la celda actual
     * @param activador personaje que activa la accion
     * @return devuelve si la funcion se ha podido ejecutar
     */
    public abstract boolean accionActivar(Personaje activador,ResultadoServidor rel);

    /**
     * funcion que se usa para poner un personaje en esta posicion, y calcul si aparece un monstruo o un objeto
     * @param personaje personaje que se mueve
     * @return devuelve true si la casilla esta vacia y se puede acceder, en caso contrario devuelve false
     */





    //TODO(falta crear la classe combate i implementarla)
    /**
     * comienza un combate con un monstruo aleatorio el personaje de la casilla
     */



}
