package eetac.dsa.juego.Controlador;

import java.util.Vector;

public class Inventario {
    Vector<Objeto> objetos;

    public Inventario() {
        this.objetos = new Vector<Objeto>();
    }

    public Vector<Objeto> getListObjetos()
    {
        return objetos;
    }

    public Objeto buscarObjeto(int index)
    {
        if(index<objetos.size())
            return objetos.get(index);
        return null;
    }

    /**
     * busca un objeto a partir del nombre. NO LO ElIMINA DE LA LISTA
     * @param nombre
     * @return
     */
    public Objeto buscarObjetoPorNombre(String nombre)
    {
        for (Objeto obj:objetos) {
            if(obj.getNombre().equals(nombre))
                return obj;
        }
        return null;
    }

    /**
     * Obten un objeto a partir del nombre. LO ElIMINA DE LA LISTA
     * usar este metodo cuando se quiera usar un objeto
     * @param nombre
     * @return
     */
    public Objeto obtenerObjetoPorNombre(String nombre)
    {
        for (Objeto obj:objetos) {
            if(obj.getNombre()==nombre) {
                Objeto tmp = obj;
                objetos.remove(obj);
                return tmp;
            }
        }
        return null;
    }

    public boolean quitarObjeto(Objeto o)
    {
        return objetos.remove(o);
    }
    public void quitarObjeto(int o)
    {
        objetos.remove(o);
    }
    
    public void añadirObeto(Objeto objeto)
    {
        objetos.add(objeto);
    }

    public Vector<Objeto> buscarObjetosPorTipo(String tipo) {
        Vector<Objeto>tmp = new Vector<Objeto>();
        for (Objeto obj:objetos) {
            if(obj.getTipo()==tipo)
            {
                tmp.add(obj);
            }
        }
        return tmp;
    }

    public int obtenerTamaño()
    {
        return objetos.size();
    }
}