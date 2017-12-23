package eetac.dsa.model.resultsserver;


import eetac.dsa.model.EscenarioJSON;

public class ResultCambiarEscenario {
    boolean permitido;
    EscenarioJSON escenarioJSON;
    int x;
    int y;

    public ResultCambiarEscenario() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isPermitido() {
        return permitido;
    }

    public void setPermitido(boolean permitido) {
        this.permitido = permitido;
    }

    public EscenarioJSON getEscenarioJSON() {
        return escenarioJSON;
    }

    public void setEscenarioJSON(EscenarioJSON escenarioJSON) {
        this.escenarioJSON = escenarioJSON;
    }
}
