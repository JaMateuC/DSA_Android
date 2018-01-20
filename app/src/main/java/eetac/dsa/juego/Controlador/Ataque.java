package eetac.dsa.juego.Controlador;

import eetac.dsa.juego.Controlador.combate.MonstruoEntidad;

public class Ataque
{
    String nombre;
    float multiplicador;
    protected Tipo tipo;

    public static enum Tipo{normal,fisico};

    protected AccionAtaque accionActual;

    public Ataque(String nombre, float multiplicador)
    {
        this.nombre = nombre;
        this.multiplicador = multiplicador;
        tipo = Tipo.normal;
        accionActual = acercar;
    }

    public String getNombre() {
        return nombre;
    }

    public float getMultiplicador() {
        return multiplicador;
    }

    public Tipo getTipo() {
        return tipo;
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

    public boolean inteligenciaAtaque(MonstruoEntidad monstruo,MonstruoEntidad enemigo)
    {

        return accionActual.accion(monstruo,enemigo,(Object)3);
    }


    protected interface AccionAtaque
    {
        public boolean accion(MonstruoEntidad monstruo,MonstruoEntidad enemigo,Object...params);
    }

    protected static AccionAtaque acercar = new AccionAtaque() {
        @Override
        public boolean accion(MonstruoEntidad monstruo, MonstruoEntidad enemigo,Object...params) {

            int margen = (int)params[0];

            if(enemigo.getBody().getPosition().x-margen>monstruo.getBody().getPosition().x)
            {
                monstruo.andarDerecha();
                return false;
            }
            if(enemigo.getBody().getPosition().x+margen<monstruo.getBody().getPosition().x)
            {
                monstruo.andarIzquierda();
                return false;
            }
            return true;
        }
    };

    protected static AccionAtaque saltar = new AccionAtaque() {
        @Override
        public boolean accion(MonstruoEntidad monstruo, MonstruoEntidad enemigo,Object... params) {
            monstruo.saltar();
            return true;
        }
    };

    protected  static AccionAtaque aplastar = new AccionAtaque() {
        @Override
        public boolean accion(MonstruoEntidad monstruo, MonstruoEntidad enemigo,Object...params) {
            if(enemigo.getBody().getPosition().x-2<monstruo.getBody().getPosition().x||
                    enemigo.getBody().getPosition().x-2>monstruo.getBody().getPosition().x)
            {
                //if(monstruo.getBody().getPosition().y-1>enemigo.getBody().getPosition().y)
                {
                    return true;
                }
            }
            return false;

        }
    };


}