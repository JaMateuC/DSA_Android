package eetac.dsa.model;

import eetac.dsa.juego.Controlador.Monstruo;


public class MonstruoJSON{
    String tipo;
    int experiencia;
    int nivel;
    int vidaActual;
    String id;

    public MonstruoJSON() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public Monstruo toMonstruo() throws Exception
    {
        Class monstruoClass = Class.forName("eetac.dsa.juego.Controlador.Monstruos." + tipo);
        Monstruo monstruo = (Monstruo) monstruoClass.getDeclaredConstructor(int.class, int.class).newInstance((Object) nivel, (Object) experiencia);
        monstruo.setId(id);
        return monstruo;
    }

    public void fromMonstruo(Monstruo monstruo){
        this.tipo = monstruo.getClass().getSimpleName();
        this.experiencia = monstruo.getExperiencia();
        this.nivel = monstruo.getNivel();
        this.vidaActual = monstruo.getVidaActual();
        this.id = monstruo.getId();
    }

    @Override
    public String toString()
    {
        return  "  " + tipo + " \n     nivel " + nivel+ "   experiencia " + experiencia + "   vida " + vidaActual;
    }
}