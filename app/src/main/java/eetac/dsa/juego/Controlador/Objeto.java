package eetac.dsa.juego.Controlador;


public abstract class Objeto
{
    private String nombre;
    private String tipo;
    private String descripcion;
    private Destino destino;
    String id;

    public enum Destino{Personaje,Monstruo}

    public Objeto(String nombre, String descripcion, String tipo,Destino destino) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.destino = destino;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public abstract void funcion(Personaje personaje);
    public abstract void funcion(Monstruo monstruo);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
