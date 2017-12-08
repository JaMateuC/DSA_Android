package eetac.dsa.dsa.Servidor.Model.jsonpojo.querysclient;

import eetac.dsa.Servidor.Model.jsonpojo.ObjetoJSON;

public class QueryResultadoCombate {
    int ganar;
    int capturar;
    ObjetoJSON[] objetosRestantes;

    public int getGanar() {
        return ganar;
    }

    public void setGanar(int ganar) {
        this.ganar = ganar;
    }

    public int getCapturar() {
        return capturar;
    }

    public void setCapturar(int capturar) {
        this.capturar = capturar;
    }

    public ObjetoJSON[] getObjetosRestantes() {
        return objetosRestantes;
    }

    public void setObjetosRestantes(ObjetoJSON[] objetosRestantes) {
        this.objetosRestantes = objetosRestantes;
    }
}
