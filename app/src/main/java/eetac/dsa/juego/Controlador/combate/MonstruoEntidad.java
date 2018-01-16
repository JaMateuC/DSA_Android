package eetac.dsa.juego.Controlador.combate;


import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.Random;

import eetac.dsa.juego.Controlador.Ataque;
import eetac.dsa.juego.Controlador.Monstruo;

public class MonstruoEntidad extends EntidadCombate {
    Monstruo monstruo;
    Ataque ataqueSeleccionado1;
    Ataque ataqueSeleccionado2;

    Ataque ataquePendiente;

    boolean puedeSaltar;

    boolean apuntaDerecha;

    MonstruoEntidad enemigo;

    Random r;


    /**
     * es un timmer, mientras esta variable no sea 0 si existe una colision entre los personajes, se efectuara el ataque.
     * en caso contrario no se ataca
     */
    float tiempoDuracionAtaque;

    public MonstruoEntidad(float x, float y, float radio, float densidad, Monstruo monstruo, World mundo) {
        super(Tipos.Monstruo, x, y, radio, densidad, 0.3f, 0.1f, mundo);

        PolygonShape cs = new PolygonShape();
        cs.setAsBox(radio / 3, radio / 3, new Vec2(0, -radio), 0);

        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.isSensor = true;
        fd.userData = this;
        fd.filter.categoryBits = Tipos.SensorSalto.getTag();
        fd.filter.maskBits = Tipos.Escenario.getTag();

        body.createFixture(fd);
        body.setSleepingAllowed(false);

        CircleShape sensorEnemicProxim = new CircleShape();
        sensorEnemicProxim.m_radius = radio*2;

        FixtureDef fixtureSensorEnemic = new FixtureDef();
        fixtureSensorEnemic.shape = sensorEnemicProxim;
        fixtureSensorEnemic.isSensor = true;
        fixtureSensorEnemic.userData = this;
        fixtureSensorEnemic.filter.categoryBits = Tipos.SensorMonstruo.getTag();
        fixtureSensorEnemic.filter.maskBits = Tipos.SensorMonstruo.getTag();

        body.createFixture(fixtureSensorEnemic);


        this.monstruo = monstruo;
        ataqueSeleccionado1 = null;
        ataqueSeleccionado2 = null;
        tiempoDuracionAtaque = 0;
        puedeSaltar = false;
        apuntaDerecha = true;
        ataquePendiente = null;

        r = new Random();
    }

    public Monstruo getMonstruo() {
        return monstruo;
    }

    public Ataque getAtaquePendiente() {
        return ataquePendiente;
    }

    public void setAtaquePendiente(Ataque ataquePendiente) {
        this.ataquePendiente = ataquePendiente;
    }

    public boolean isApuntaDerecha() {
        return apuntaDerecha;
    }

    public void setAtaqueSeleccionado1(int i) {
        Ataque ataque = monstruo.getLista_ataques().getAtaque(i);

        ataqueSeleccionado1 = ataque;
    }

    /*public void setAtaqueSeleccionado2(int i) {
        Ataque ataque = monstruo.getLista_ataques().getAtaque(i);
        ataqueSeleccionado2 = ataque;
    }*/

    public void ataque1() {
        if (tiempoDuracionAtaque == 0&&ataqueSeleccionado1!=null) {

            if (ataqueSeleccionado1.getTipo() == Ataque.Tipo.fisico) {
                AtaqueFisica atk = (AtaqueFisica)ataqueSeleccionado1;
                switch (atk.comportamiento)
                {
                    case PorDefecto:
                        if(enemigo==null)break;
                        tiempoDuracionAtaque = atk.getDuracionAtaque();
                        monstruo.atacar(enemigo.monstruo, ataqueSeleccionado1);
                        ataqueSeleccionado1.extraEnemigo(enemigo.monstruo);
                        ataqueSeleccionado1.extraAtacante(monstruo);

                        atk.extraAtacanteFisica(this);
                        atk.extraEnemigoFisica(enemigo);
                        break;

                    case SoloFisica:
                        tiempoDuracionAtaque = atk.getDuracionAtaque();
                        atk.soloFisica(this);
                        break;

                    case FisicaLuegoAtaque:
                        tiempoDuracionAtaque =atk.getDuracionAtaque();
                        atk.soloFisica(this);
                        ataquePendiente = atk;
                        break;
                }

            }
            else if(enemigo!=null)
            {
                tiempoDuracionAtaque = 0.2f;
                monstruo.atacar(enemigo.monstruo, ataqueSeleccionado1);
                ataqueSeleccionado1.extraEnemigo(enemigo.monstruo);
                ataqueSeleccionado1.extraAtacante(monstruo);
            }

        }
    }

    /*public void ataque2() {
        if (tiempoDuracionAtaque == 0) {
            ataqueActivo = ataqueSeleccionado2;
            tiempoDuracionAtaque = 0.5f;
        }
    }*/

    public void step(float tiempo) {
        tiempoDuracionAtaque -= tiempo;
        if (tiempoDuracionAtaque < 0) {
            tiempoDuracionAtaque = 0;
            ataquePendiente = null;
        }
    }

    public void saltar() {
        if (puedeSaltar) {
            float impulse = body.getMass() * 7;
            body.applyLinearImpulse(new Vec2(0, impulse), body.getWorldCenter());
        }
    }

    public void andarDerecha() {
        if(puedeSaltar)
        {
            float velChange = 10 - body.m_linearVelocity.x;
            float impulse = body.getMass() * velChange; //disregard time factor
            body.applyLinearImpulse(new Vec2(impulse, 0), body.getWorldCenter());
        }
        apuntaDerecha = true;
    }

    public void andarIzquierda() {
        if(puedeSaltar)
        {
            float velChange = -10 - body.m_linearVelocity.x;
            float impulse = body.getMass() * velChange; //disregard time factor
            body.applyLinearImpulse(new Vec2(impulse, 0), body.getWorldCenter());
        }
        apuntaDerecha = false;
    }

    public void IA(MonstruoEntidad enemigo)
    {
        if(ataqueSeleccionado1==null)
        {
            int i = Math.round(r.nextFloat()*4);
            setAtaqueSeleccionado1(i);
            return;
        }
        if(ataqueSeleccionado1.inteligenciaAtaque(this,enemigo))
        {
            ataque1();
            ataqueSeleccionado1 = null;
        }
    }
}
