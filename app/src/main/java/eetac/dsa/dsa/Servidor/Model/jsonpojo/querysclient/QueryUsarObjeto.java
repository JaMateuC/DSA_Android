package eetac.dsa.dsa.Servidor.Model.jsonpojo.querysclient;

public class QueryUsarObjeto {
    boolean objetivo;//true=usuario, false=monstruo
    String usuario;
    int indice;
    int indiceMonstruo;

    public int getIndiceMonstruo() {
        return indiceMonstruo;
    }

    public void setIndiceMonstruo(int indiceMonstruo) {
        this.indiceMonstruo = indiceMonstruo;
    }

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
