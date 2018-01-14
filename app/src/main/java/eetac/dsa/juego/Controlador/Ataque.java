package eetac.dsa.juego.Controlador;

public class Ataque
{
    String nombre;
    float multiplicador;
    protected Tipo tipo;

    public static enum Tipo{normal,fisico};

    public Ataque(String nombre, float multiplicador)
    {
        this.nombre = nombre;
        this.multiplicador = multiplicador;
        tipo = Tipo.normal;
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
}