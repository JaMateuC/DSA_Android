package eetac.dsa.dsa.Servidor.Model;

import eetac.dsa.Servidor.Controlador.*;

import java.util.HashMap;
import java.util.Vector;

public class ImplementacionBBDD implements InterfazBBDD
{
    HashMap<String, Usuario> map;

    static ImplementacionBBDD singleton;

    static public ImplementacionBBDD getInstance()
    {
        if(singleton == null)
            singleton = new ImplementacionBBDD();
        return singleton;
    }

    private ImplementacionBBDD()
    {
        map = new HashMap<>();
    }


    @Override
    public Usuario getUsuario(String nombre)
    {
        return map.get(nombre);
    }

    @Override
    public Vector<Objeto> getlistobjetos(String nombre)
    {
        return map.get(nombre).getInventario().getListObjetos();
    }

    @Override
    public Vector<Monstruo> getlistamonstruo(String nombre)
    {
        return map.get(nombre).getLista_montruos().getListMonstruos();
    }

    @Override
    public Escenario getescenario(String nombreEscenario)
    {
        Escenario escenario;

        try
        {
            escenario = CargadorJSON.jsonAEscenario(CargadorJSON.ficheroAJSON("src/main/resources/Escenarios/" + nombreEscenario + ".json"));
        }
        catch (Exception e)
        {
            //logger.error("error al cargar un escenario");
            return null;
        }

        return escenario;
    }
}
