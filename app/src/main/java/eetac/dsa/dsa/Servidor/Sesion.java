package eetac.dsa.dsa.Servidor;

import android.graphics.Point;

import eetac.dsa.dsa.Flag;
import eetac.dsa.dsa.Servidor.Controlador.*;

import java.util.HashMap;

public class Sesion {
    Escenario escenario;
    Usuario protagonista;
    HashMap<String,Personaje> personajesDelEscenario;

    public Sesion(Usuario protagonista, String nombreEscenario) {
        this.protagonista = protagonista;

        cargarEscenarioFichero(nombreEscenario);
        escenario.getCelda(1,2).setPersonajeEncima(protagonista); //(int)protagonista.getPosicion().getX() (int)protagonista.getPosicion().getY())
    }

    public Escenario getEscenario() {
        return escenario;
    }

    public Usuario getProtagonista() {
        return protagonista;
    }


    public HashMap<String, Personaje> getPersonajesDelEscenario() {
        return personajesDelEscenario;
    }


    public ResultadoServidor mover(int x, int y)
    {
        ResultadoServidor rel = new ResultadoServidor();
        protagonista.mover(1+x,1+y,rel);//protagonista.getPosicion().x protagonista.getPosicion().y

        return  rel;
    }

    public ResultadoServidor usarObjetoProtagonista(int index)
    {
        ResultadoServidor rel = new ResultadoServidor();
        if(protagonista.getInventario().buscarObjeto(index).getDestino() == Objeto.Destino.Personaje)
        {
            rel.getFlag().addFlag(Flag.borrarObjeto);
            rel.setIndiceObjeto(index);
            protagonista.usarObjeto(index,rel);
        }
        return rel;
    }

    public ResultadoServidor borrarObjeto(int index)
    {
        ResultadoServidor rel = new ResultadoServidor();
        if(index<protagonista.getInventario().obtenerTamaño()) {
            rel.getFlag().addFlag(Flag.borrarObjeto);
            rel.setIndiceObjeto(index);
            protagonista.getInventario().quitarObjeto(index);
        }
        rel.getFlag().isFlag(Flag.ERROR);
        return rel;
    }

    public ResultadoServidor borrarMonstruo(int index)
    {
        ResultadoServidor rel = new ResultadoServidor();
        if(index<protagonista.getLista_montruos().getTamaño()) {
            rel.getFlag().addFlag(Flag.borrarMonstruo);
            rel.setIndiceObjeto(index);
            protagonista.getLista_montruos().quitarMonstruoPorPosicion(index);
        }
        rel.getFlag().isFlag(Flag.ERROR);
        return rel;
    }

    public ResultadoServidor usarObjetoMonstruo(int indexObjeto, int indexMonstruo)
    {
        ResultadoServidor rel = new ResultadoServidor();
        if(protagonista.getInventario().buscarObjeto(indexObjeto).getDestino() == Objeto.Destino.Monstruo)
        {
            rel.getFlag().addFlag(Flag.borrarObjeto);
            rel.setIndiceObjeto(indexObjeto);
            protagonista.usarObjetoAMonstruo(indexObjeto,indexMonstruo,rel);
        }
        return rel;
    }

    public ResultadoServidor hacerAccion(int x, int y)
    {

        return null;
    }

    //public ResultadoServidor resultadoCombate()


    public boolean cargarEscenarioFichero(String nombre)
    {
        try {
            escenario = CargadorJSON.jsonAEscenario(CargadorJSON.ficheroAJSON("src/main/resources/Escenarios/" + nombre + ".json"));
            //personajesDelEscenario = CargadorJSON.jsonAPersonajes(CargadorJSON.ficheroAJSON("src/main/resources/Escenarios/Personajes"+nombre+".json"));
        }
        catch (Exception e)
        {
            //logger.error("error al cargar un escenario");
            return false;
        }
        /*for (Personaje p:personajesDelEscenario.values()) {
            escenario.getCelda((int)p.getPosicion().getX(),(int)p.getPosicion().getY()).setPersonajeEncima(p);
        }*/
        return true;
    }
}
