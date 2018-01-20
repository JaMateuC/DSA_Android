package eetac.dsa.juego.Controlador;




import android.graphics.Point;

import eetac.dsa.juego.root.Mundo;


public abstract class Personaje
{
    private String nombre;
    protected Point posicion;
    private Lista_Monstruos lista_montruos;
    private Inventario inventario;
    private boolean genero;

    public Personaje(String nombre, int x, int y, boolean genero)
    {
        this.nombre = nombre;
        posicion = new Point(x,y);
        this.lista_montruos = new Lista_Monstruos();
        this.inventario = new Inventario();
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPosicion(int x,int y) {
        this.posicion.set(x,y);
    }

    public Point getPosicion() {
        return posicion;
    }

    public Lista_Monstruos getLista_montruos() {
        return lista_montruos;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public boolean getGenero() {
        return genero;
    }


    public boolean mover(int x,int y)
    {
        Celda celda = Mundo.getIns().getCelda(x,y);
        if(celda.accion(this,x,y,this.getPosicion().x,this.getPosicion().y))
        {
            return true;
        }
        else return false;
    }


    public void hacerAccion(Celda celda)
    {
        celda.accionActivar(this);
    }

    public boolean usarObjetoAMonstruo(int indiceInventario,int indiceMonstruo)
    {
        if(indiceInventario<inventario.obtenerTamaño()&&indiceMonstruo<lista_montruos.getTamaño())
        {
            Objeto obj= inventario.buscarObjeto(indiceInventario);
            obj.funcion(lista_montruos.obtenerMonstruo(indiceMonstruo));
            inventario.quitarObjeto(obj);
            return true;
        }
        return false;
    }

    public boolean usarObjeto(int indiceInventario)
    {
        if(indiceInventario<inventario.obtenerTamaño())
        {
            Objeto obj= inventario.buscarObjeto(indiceInventario);
            obj.funcion(this);
            inventario.quitarObjeto(obj);
            return true;
        }
        return false;
    }


    public boolean añadirMonstruo(Monstruo monstruo)
    {
        return lista_montruos.añadirMonstruo(monstruo);
    }

    public boolean quitarMonstruo(Monstruo monstruo)
    {
        return lista_montruos.añadirMonstruo(monstruo);
    }

    public Monstruo obtenerMonstruo(int i)
    {
        return lista_montruos.obtenerMonstruo(i);
    }
}
