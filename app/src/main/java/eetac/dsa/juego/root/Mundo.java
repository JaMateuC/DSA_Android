package eetac.dsa.juego.root;

import android.util.Log;

import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Escenario;
import eetac.dsa.juego.Controlador.Monstruo;
import eetac.dsa.juego.Controlador.Objeto;
import eetac.dsa.juego.Controlador.Usuario;
import eetac.dsa.juego.Controlador.combate.Combate;
import eetac.dsa.model.EscenarioJSON;
import eetac.dsa.model.MonstruoJSON;
import eetac.dsa.model.ObjetoJSON;
import eetac.dsa.model.resultsserver.ResultLoginArgs;

public class Mundo implements ResponseRest , AccionesMapa{
    Usuario usuario;
    Escenario escenario;
    ConexionServidor conexionServidor;
    int key;
    static Mundo mundo;
    FSM estado;

    MonstruoJSON[][] monstruosEncontrables;
    ObjetoJSON[][] objetosEncontrables;

    LinkedList<sincro> cua;

    Combate combate;

    CombatCall combatCall;

    String objetoEncontrado;

    public void init(int key, ConexionServidor conexionServidor,CombatCall combatCall){
            this.conexionServidor = conexionServidor;
            this.key = key;
            conexionServidor.getLoginArgs(key);
            estado = FSM.waitLoginArgs;
            Log.d("Estado","waitLoginArgs");
            cua = new LinkedList<>();
            this.combatCall = combatCall;
            objetoEncontrado = null;
    }


    synchronized public void initCombate(Monstruo enemigo)
    {
        combate = new Combate(usuario.obtenerMonstruo(0), enemigo, combatCall);
        estado = FSM.combate;
    }

    synchronized public void endCombat(boolean capturar)
    {
        estado = FSM.play;
        if(capturar) {
            combate.getEnemigo().getMonstruo().setVidaActual(combate.getEnemigo().getMonstruo().getVidaEfectiva());
            usuario.getLista_montruos().añadirMonstruo(combate.getEnemigo().getMonstruo());
        }
    }

    public void atacar(int index)
    {
        if(estado==FSM.combate)
        {
            combate.atacar(index);
        }
    }

    public void comIzq()
    {
        if(estado==FSM.combate)
        {
            combate.izquierda();
        }
    }

    public void comDer()
    {
        if(estado==FSM.combate)
        {
            combate.derecha();
        }
    }

    public void saltar()
    {
        if(estado==FSM.combate)
        {
            combate.getMonstruo().saltar();
        }
    }



    public Monstruo getRandomMonstruo() throws Exception
    {
        Random random = new Random();
        int indice = (int)random.nextFloat()*monstruosEncontrables[escenario.getNivelDeZona()].length;
        Monstruo monstruo = monstruosEncontrables[escenario.getNivelDeZona()][indice].toMonstruo();
        monstruo.setId(UUID.randomUUID().toString());
        Log.i("generador","creat monstre");
        return monstruo;
    }


    public Objeto getRandomObjeto() throws Exception
    {
        Random random = new Random();
        int indice = (int)(random.nextFloat()*objetosEncontrables[escenario.getNivelDeZona()].length);
        Objeto objeto = objetosEncontrables[escenario.getNivelDeZona()][indice].toObjeto();
        objeto.setId(UUID.randomUUID().toString());
        Log.i("generador","creat objecte");
        return objeto;
    }

    public String getObjetoEncontrado() {
        return objetoEncontrado;
    }

    public void setObjetoEncontrado(String objetoEncontrado) {
        this.objetoEncontrado = objetoEncontrado;
    }

    public Combate getCombate() {
        return combate;
    }

    public void mover(int x, int y)
    {
        if(estado==FSM.play)
            usuario.mover(usuario.getPosicion().x+x,usuario.getPosicion().y+y);
    }

    private Mundo(){estado = FSM.init;};

    public synchronized void onCambiarMapa(EscenarioJSON escenarioJSON,int x,int y)
    {
        //if(estado==FSM.waitMapa)
        {
            try {
                if(escenarioJSON==null) throw new Exception();
                escenario = escenarioJSON.toEscenario();
                usuario.setPosicion(x,y);
                /*estado = FSM.play;
                Log.d("Estado","play");*/
                if(cua.size()!=0)
                    cua.poll().sincro();
                else
                    estado = FSM.play;
            }
            catch (Exception e)
            {
                Log.e("Mapa","error ar recivir el mapa");
            }

        }
    }

    public synchronized void onGetLogingArgs(ResultLoginArgs resultLoginArgs)
    {
        //if(estado==FSM.waitLoginArgs)
        {
            try {
                escenario = resultLoginArgs.getEscenarioJSON().toEscenario();
                usuario = resultLoginArgs.getUsuarioJSON().toUsario();
                monstruosEncontrables = resultLoginArgs.getMonstruosEncontrables();
                objetosEncontrables = resultLoginArgs.getObjetosEncontrables();
                /*estado = FSM.play;
                Log.d("Estado","play");*/
                if(cua.size()!=0)
                    cua.poll().sincro();
                else
                    estado = FSM.play;
            }
            catch (Exception e)
            {
                Log.e("Init","error al cargar los datos del login");
            }
        }
    }


    public static Mundo getIns()
    {
        if(mundo==null)
            mundo = new Mundo();
        return mundo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Escenario getEscenario() {
        return escenario;
    }

    public Celda getCelda(int x, int y)
    {
        return escenario.getCelda(x,y);
    }

    public void updateUsuario()
    {
        cua.add(new sincro() {
            @Override
            public void sincro() {
                conexionServidor.updateUsuario(key,escenario.getNombre(),usuario);
                estado = FSM.play;
            }
        });
        if(cua.size()==1)
        {
            conexionServidor.updateUsuario(key,escenario.getNombre(),usuario);
            estado = FSM.play;
        }
    }

    public void cambiarMapa(final String nombreEscenario,final int x,final int y)
    {
        cua.add(new sincro() {
            @Override
            public void sincro() {
                conexionServidor.cambiarMapa(key,nombreEscenario,x,y);
                estado= FSM.waitMapa;
            }
        });
        if(cua.size()==1)
        {
            conexionServidor.cambiarMapa(key,nombreEscenario,x,y);
            estado= FSM.waitMapa;
        }

        /*if(estado==FSM.play)
        {
            conexionServidor.cambiarMapa(key,nombreEscenario,x,y);
            estado= FSM.waitMapa;
            Log.d("Estado","waitMapa");
        }*/
    }

    public FSM getEstado() {
        return estado;
    }

    public enum FSM{
        init, waitLoginArgs,play,waitMapa,combate
    }

    public interface sincro
    {
        void sincro();
    }
}
