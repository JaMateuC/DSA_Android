package eetac.dsa.juego.Controlador;


import android.util.Log;

import eetac.dsa.juego.root.Mundo;

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

    int restriction;

    //es poden sumar les restriccions com un numero
    public static final int REST_UP = 2;
    public static final int REST_DOWN = 4;
    public static final int REST_LEFT = 8;
    public static final int REST_RIGHT = 16;




    public Celda(String tipo, float probObjeto, float probMonstruo, Boolean andable) {
        this.tipo = tipo;
        this.probObjeto = probObjeto;
        this.probMonstruo = probMonstruo;
        this.andable = andable;
        restriction = 0;
    }

    public Celda(String tipo, float probObjeto, float probMonstruo, Boolean andable,int restriction) {
        this.tipo = tipo;
        this.probObjeto = probObjeto;
        this.probMonstruo = probMonstruo;
        this.andable = andable;
        this.restriction = restriction;
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

    public int getRestriction() {
        return restriction;
    }

    /**
     * funcion miscelanea que se ejecuta cuando un usuario esta encima
     * @return devuelve si la funcion se ha podido ejecutar
     */
    public abstract boolean accionEncima(Personaje personaje);

    /**
     * funcion miscelanea que se activa por un personaje delante de la celda actual
     * @param activador personaje que activa la accion
     * @return devuelve si la funcion se ha podido ejecutar
     */
    public abstract boolean accionActivar(Personaje activador);

    /**
     * funcion que se usa para poner un personaje en esta posicion, y calcul si aparece un monstruo o un objeto
     * @param personaje personaje que se mueve
     * @return devuelve true si la casilla esta vacia y se puede acceder, en caso contrario devuelve false
     */
    public boolean accion(Personaje personaje,int x,int y,int oldX,int oldY)
    {
        //if(this.personajeEncima!= null||this.andable==false) {

        if(this.andable==false)
            return false;
        Celda oldCelda = Mundo.getIns().getCelda(oldX,oldY);


        if(x-oldX==-1&&oldCelda.getRestriction()==REST_LEFT)
            return false;
        if(x-oldX==1&&oldCelda.getRestriction()==REST_RIGHT)
            return false;

        if(y-oldY==-1&&oldCelda.getRestriction()==REST_UP)
            return false;
        if(y-oldY==1&&oldCelda.getRestriction()==REST_DOWN)
            return false;

        if(x-oldX==1&&restriction==REST_LEFT)
            return false;
        if(x-oldX==-1&&restriction==REST_RIGHT)
            return false;

        if(y-oldY==1&&restriction==REST_UP)
            return false;
        if(y-oldY==-1&&restriction==REST_DOWN)
            return false;

        Mundo.getIns().setObjetoEncontrado(null);

        double numeroObjeto = Math.random();
        double numeroPersonaje = Math.random();
        //this.personajeEncima = personaje;
        if(numeroObjeto<probObjeto)
        {
            obtenerObjeto(personaje);
        }
        if(numeroPersonaje < probMonstruo)
        {
            comenzarCombate(personaje);
        }
        personaje.setPosicion(x,y);
        accionEncima(personaje);


        return true;
    }


    void obtenerObjeto(Personaje personaje)
    {
        try {
            Objeto obj = Mundo.getIns().getRandomObjeto();
            personaje.getInventario().añadirObeto(obj);
            Mundo.getIns().setObjetoEncontrado(obj.getNombre());

        }
        catch (Exception e)
        {
            Log.e("Objeto.java","error al copiar un objeto");
        }
    }

    //TODO(falta crear la classe combate i implementarla)
    /**
     * comienza un combate con un monstruo aleatorio el personaje de la casilla
     */
    void comenzarCombate(Personaje personaje)
    {
        try {
            Monstruo monstruo = Mundo.getIns().getRandomMonstruo();
            Mundo.getIns().initCombate(monstruo);

        }
        catch (Exception e)
        {
            Log.e("Celda.java","error al copiar una nueva instancia de monstruo");
        }
    }

}
