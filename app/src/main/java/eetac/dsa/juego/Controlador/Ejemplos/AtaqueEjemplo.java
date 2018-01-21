package eetac.dsa.juego.Controlador.Ejemplos;

import eetac.dsa.juego.Controlador.Ataque;
import eetac.dsa.juego.Controlador.Monstruo;

/**
 * ataque de ejemplo, sacrifica la mitad de tu vida actual para dañar la mitad de la vida actual del enemigo
 */
public class AtaqueEjemplo extends Ataque
{
    public AtaqueEjemplo() {
        super("sacrificio", 0.f);
    }

    @Override
    public void extraEnemigo(Monstruo enemigo)
    {
        enemigo.setVidaActual(enemigo.getVidaActual()/4);
    }

    public void extraAtacante(Monstruo atacante)
    {
        atacante.setVidaActual(atacante.getVidaActual()/2);
    }
}
