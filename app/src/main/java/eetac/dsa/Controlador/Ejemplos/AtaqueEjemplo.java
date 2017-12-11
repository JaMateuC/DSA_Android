package eetac.dsa.Controlador.Ejemplos;

import eetac.dsa.Controlador.Ataque;
import eetac.dsa.Controlador.Monstruo;

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
        enemigo.setVidaActual(enemigo.getVidaActual()/2);
    }

    public void extraAtacante(Monstruo atacante)
    {
        atacante.setVidaActual(atacante.getVidaActual()/2);
    }
}
