package eetac.dsa.juego.Controlador.combate;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

public class CombatCollisionListener implements ContactListener {
    public void beginContact(Contact contact) {
        EntidadCombate obj1 = (EntidadCombate)contact.m_fixtureA.m_userData;
        EntidadCombate obj2 = (EntidadCombate)contact.m_fixtureB.m_userData;

        if(obj1==null||obj2==null)return;


        if(contact.m_fixtureA.isSensor()&&obj1.tipo== EntidadCombate.Tipos.Monstruo&&obj2.tipo== EntidadCombate.Tipos.Escenario)
        {
            MonstruoEntidad monstruo = (MonstruoEntidad)obj1;
            monstruo.puedeSaltar = true;
        }

        if(contact.m_fixtureB.isSensor()&&obj2.tipo== EntidadCombate.Tipos.Monstruo&&obj1.tipo== EntidadCombate.Tipos.Escenario)
        {
            MonstruoEntidad monstruo = (MonstruoEntidad)obj2;
            monstruo.puedeSaltar = true;
        }

        if(contact.m_fixtureA.m_filter.categoryBits== EntidadCombate.Tipos.SensorMonstruo.getTag()
                &&contact.m_fixtureB.m_filter.categoryBits== EntidadCombate.Tipos.SensorMonstruo.getTag())
        {
            MonstruoEntidad monstruo = (MonstruoEntidad)obj1;
            MonstruoEntidad monstruo2 = (MonstruoEntidad)obj2;
            monstruo.enemigo = monstruo2;
            monstruo2.enemigo = monstruo;
        }




    }

    public void endContact(Contact contact) {
        EntidadCombate obj1 = (EntidadCombate)contact.m_fixtureA.m_userData;
        EntidadCombate obj2 = (EntidadCombate)contact.m_fixtureB.m_userData;

        if(obj1==null||obj2==null)return;

        if(contact.m_fixtureA.m_filter.categoryBits== EntidadCombate.Tipos.SensorMonstruo.getTag()
                &&contact.m_fixtureB.m_filter.categoryBits== EntidadCombate.Tipos.SensorMonstruo.getTag())        {
            MonstruoEntidad monstruo = (MonstruoEntidad)obj1;
            MonstruoEntidad monstruo2 = (MonstruoEntidad)obj2;
            monstruo.enemigo = null;
            monstruo2.enemigo = null;
        }

        if(contact.m_fixtureA.isSensor()&&obj1.tipo== EntidadCombate.Tipos.Monstruo&&obj2.tipo== EntidadCombate.Tipos.Escenario)
        {
            MonstruoEntidad monstruo = (MonstruoEntidad)obj1;
            monstruo.puedeSaltar = false;
        }

        if(contact.m_fixtureB.isSensor()&&obj2.tipo== EntidadCombate.Tipos.Monstruo&&obj1.tipo== EntidadCombate.Tipos.Escenario)
        {
            MonstruoEntidad monstruo = (MonstruoEntidad)obj2;
            monstruo.puedeSaltar = false;
        }
    }

    public void preSolve(Contact contact, Manifold manifold) {

    }

    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

        int i =0;
        if(contact.m_fixtureA.m_filter.categoryBits== EntidadCombate.Tipos.Monstruo.getTag()
                &&contact.m_fixtureB.m_filter.categoryBits== EntidadCombate.Tipos.Monstruo.getTag())
        {
            MonstruoEntidad monstruo = (MonstruoEntidad)contact.m_fixtureA.m_userData;
            MonstruoEntidad monstruo2 = (MonstruoEntidad)contact.m_fixtureB.m_userData;

            if(monstruo.getAtaquePendiente()!=null)
            {
                monstruo.getMonstruo().atacar(monstruo2.getMonstruo(),monstruo.getAtaquePendiente());
                AtaqueFisica ataque = (AtaqueFisica)monstruo.getAtaquePendiente();

                ataque.extraEnemigoFisica(monstruo2);
                ataque.extraAtacanteFisica(monstruo);
                ataque.extraEnemigo(monstruo2.getMonstruo());
                ataque.extraAtacante(monstruo.getMonstruo());
                monstruo.setAtaquePendiente(null);
            }

            monstruo = (MonstruoEntidad)contact.m_fixtureB.m_userData;
            monstruo2 = (MonstruoEntidad)contact.m_fixtureA.m_userData;

            if(monstruo.getAtaquePendiente()!=null)
            {
                monstruo.getMonstruo().atacar(monstruo2.getMonstruo(),monstruo.getAtaquePendiente());
                AtaqueFisica ataque = (AtaqueFisica)monstruo.getAtaquePendiente();

                ataque.extraEnemigoFisica(monstruo2);
                ataque.extraAtacanteFisica(monstruo);
                ataque.extraEnemigo(monstruo2.getMonstruo());
                ataque.extraAtacante(monstruo.getMonstruo());
                monstruo.setAtaquePendiente(null);
            }
        }
    }
}
