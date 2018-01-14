package eetac.dsa.juego.Controlador.combate;

import android.util.Log;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import eetac.dsa.juego.Controlador.Monstruo;
import eetac.dsa.juego.Controlador.Monstruos.MonstruoEjemplo;
import eetac.dsa.juego.root.CombatCall;

public class Combate {
    World mundo;
    MonstruoEntidad monstruo;
    MonstruoEntidad enemigo;
    EntidadEscenario escenario;
    CombatCall combatCall;

    public final static int ANCHOESCENARIO = 40;
    public final static int ALTOESCENARIO = 20;

    public MonstruoEntidad getMonstruo() {
        return monstruo;
    }



    public MonstruoEntidad getEnemigo() {
        return enemigo;
    }


    public Combate(Monstruo monstruo, Monstruo enemigo, CombatCall combatCall) {
        Vec2 gravedad = new Vec2(0.f,-9.8f);
        mundo = new World(gravedad);
        mundo.setAllowSleep(true);
        mundo.setContactListener(new CombatCollisionListener());
        escenario = new EntidadEscenario(ANCHOESCENARIO,ALTOESCENARIO,mundo);
        if(monstruo==null)
            monstruo = new MonstruoEjemplo(1,0);
        this.monstruo = new MonstruoEntidad(0,0,0.75f,0.5f,monstruo,mundo);
        this.enemigo = new MonstruoEntidad(-10,0,0.75f,0.5f,enemigo,mundo);
        this.combatCall = combatCall;
        combatCall.init();
    }

    public void atacar(int index)
    {
        monstruo.setAtaqueSeleccionado1(index);
        monstruo.ataque1();
    }

    public void izquierda()
    {
        monstruo.andarIzquierda();
    }

    public void derecha()
    {
        monstruo.andarDerecha();
    }

    public void step(float time)
    {
        mundo.step(time,8,3);
        monstruo.step(time);
        enemigo.step(time);
        Log.d("vida",String.valueOf(enemigo.getMonstruo().getVidaActual()));
        Log.d("vida",String.valueOf(monstruo.getMonstruo().getVidaActual()));
        if(enemigo.getMonstruo().getVidaActual()<=0)
            combatCall.resultado(false);
        if(monstruo.getMonstruo().getVidaActual()<=0)
            combatCall.resultado(true);
    }


    public World getMundo() {
        return mundo;
    }
}
