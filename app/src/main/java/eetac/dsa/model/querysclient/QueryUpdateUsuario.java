package eetac.dsa.model.querysclient;


import eetac.dsa.model.UsuarioJSON;

public class QueryUpdateUsuario {
    int key;
    UsuarioJSON usuarioJson;
    String nomEscenari;

    public String getNomEscenari() {
        return nomEscenari;
    }

    public void setNomEscenari(String nomEscenari) {
        this.nomEscenari = nomEscenari;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public UsuarioJSON getUsuarioJson() {
        return usuarioJson;
    }

    public void setUsuarioJson(UsuarioJSON usuarioJson) {
        this.usuarioJson = usuarioJson;
    }
}
