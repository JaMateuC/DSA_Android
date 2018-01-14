package eetac.dsa.juego.Controlador.ataque;


import org.jbox2d.common.Vec2;

import eetac.dsa.juego.Controlador.combate.AtaqueFisica;
import eetac.dsa.juego.Controlador.combate.MonstruoEntidad;

public class SaltoAereo extends AtaqueFisica {
    public SaltoAereo() {
        super("SaltoAereo", 0f, 1f);
        this.comportamiento = Comportamiento.SoloFisica;
    }

    @Override
    public void soloFisica(MonstruoEntidad atacante) {
        atacante.getBody().applyLinearImpulse(new Vec2(0,10), atacante.getBody().getWorldCenter());
    }
}
