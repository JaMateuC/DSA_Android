package eetac.dsa.dsa.Servidor.Model.jsonpojo.querysclient;

public class QueryBorrar {
    boolean objetivo;//true=objeto, false=monstruo
    String usuario;
    int indice;

    public boolean isObjetivo() {
        return objetivo;
    }

    public void setObjetivo(boolean objetivo) {
        this.objetivo = objetivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}
