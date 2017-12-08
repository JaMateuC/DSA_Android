package eetac.dsa.dsa.Servidor.Model.dao;

public class MonstruoDAO extends DAO{

    @OrderFields(indice = 0)
    String id;
    @OrderFields(indice = 1)
    String tipo;
    @OrderFields(indice = 2)
    int experiencia;
    @OrderFields(indice = 3)
    int nivel;
    @OrderFields(indice = 4)
    int vidaActual;
    @OrderFields(indice = 5)
    String nombreUsuario;

    public MonstruoDAO() {
    }

    public MonstruoDAO(String id,String tipo, int experiencia, int nivel, int vidaActual, String nombreUsuario) {
        this.id = id;
        this.tipo = tipo;
        this.experiencia = experiencia;
        this.nivel = nivel;
        this.vidaActual = vidaActual;
        this.nombreUsuario = nombreUsuario;
    }

    @OrderGetMethods(indice = 0)
    public String getId() {
        return id;
    }

    @OrderSetMethods(indice = 0)
    public void setId(String id) {
        this.id = id;
    }

    @OrderGetMethods(indice = 1)
    public String getTipo() {
        return tipo;
    }

    @OrderSetMethods(indice = 1)
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @OrderGetMethods(indice = 2)
    public int getExperiencia() {
        return experiencia;
    }

    @OrderSetMethods(indice = 2)
    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    @OrderGetMethods(indice = 3)
    public int getNivel() {
        return nivel;
    }

    @OrderSetMethods(indice = 3)
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @OrderGetMethods(indice = 4)
    public int getVidaActual() {
        return vidaActual;
    }

    @OrderSetMethods(indice = 4)
    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    @OrderGetMethods(indice = 5)
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @OrderSetMethods(indice = 5)
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public String toString(){

        return "id: " + this.id + ", tipo: " + this.tipo + ", experiencia: " + this.experiencia +
                ", nivel: " + this.nivel + ", vidaActual: " + this.vidaActual + ", nombreUsuario: " +
                this.nombreUsuario;

    }

}
