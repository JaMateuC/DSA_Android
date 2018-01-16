package eetac.dsa.model.resultsserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultadoAceptar
{
    @Expose
    @SerializedName("permitido")
    private boolean permitido;

    public ResultadoAceptar() {  }

    public boolean isPermitido() {
        return permitido;
    }

    public void setPermitido(boolean permitido) {
        this.permitido = permitido;
    }
}
