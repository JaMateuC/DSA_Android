package eetac.dsa.model.querysclient;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eetac.dsa.model.UsuarioJSON;

public class QueryUpdateUsuario
{
    @Expose
    @SerializedName("key")
    int key;

    @Expose
    @SerializedName("usuarioJson")
    UsuarioJSON usuarioJson;

    @Expose
    @SerializedName("nomEscenari")
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
