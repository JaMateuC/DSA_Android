package eetac.dsa.juego.Controlador.ataque;


import eetac.dsa.juego.Controlador.combate.AtaqueFisica;
import eetac.dsa.juego.Controlador.combate.MonstruoEntidad;

import org.jbox2d.common.Vec2;

public class Pisoton extends AtaqueFisica
{
    public Pisoton() {
        super("Pisoton", 3f, 1f);
        this.comportamiento = AtaqueFisica.Comportamiento.FisicaLuegoAtaque;
        accionActual = acercar;
    }

    @Override
    public void soloFisica(MonstruoEntidad atacante) {
        atacante.getBody().applyLinearImpulse(new Vec2(0,-30), atacante.getBody().getWorldCenter());
    }

    @Override
    public boolean inteligenciaAtaque(MonstruoEntidad monstruo, MonstruoEntidad enemigo) {
        if(accionActual==acercar)
        {
            if(accionActual.accion(monstruo,enemigo,(Object)20))
                accionActual = saltar;
            return false;
        }
        if(accionActual==saltar){
            if(accionActual.accion(monstruo, enemigo))
                accionActual = aplastar;
            return false;
        }
        if(accionActual==aplastar)
        {
            if(accionActual.accion(monstruo, enemigo))
            {
                accionActual = acercar;
                return true;
            }
            return false;
        }
        return false;
    }
}
