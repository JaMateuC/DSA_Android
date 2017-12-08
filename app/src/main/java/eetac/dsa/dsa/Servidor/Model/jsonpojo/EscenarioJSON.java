package eetac.dsa.dsa.Servidor.Model.jsonpojo;

import eetac.dsa.Servidor.Controlador.Escenario;

public class EscenarioJSON {
    String nombre;
    int nivelDeZona;
    int ancho;
    int alto;
    CeldaJSON celdaJSON[][];

    public EscenarioJSON() {
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public CeldaJSON[][] getCeldaJSON() {
        return celdaJSON;
    }

    public void setCeldaJSON(CeldaJSON[][] celdaJSON) {
        this.celdaJSON = celdaJSON;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivelDeZona() {
        return nivelDeZona;
    }

    public void setNivelDeZona(int nivelDeZona) {
        this.nivelDeZona = nivelDeZona;
    }



    public Escenario toEscenario()throws Exception
    {
        Escenario escenario = new Escenario(nombre,ancho,alto,nivelDeZona);
        for(int x=0;x<ancho;x++){
            for(int y=0;y<alto;y++){
                escenario.setCelda(celdaJSON[x][y].toCelda(),x,y);
            }
        }
        return escenario;
    }

    public void fromEscenario(Escenario escenario) throws Exception
    {
        if(escenario==null)return;
        this.alto = escenario.getAlto();
        this.ancho = escenario.getAncho();
        this.nivelDeZona = escenario.getNivelDeZona();
        this.nombre = escenario.getNombre();
        this.celdaJSON = new CeldaJSON[ancho][alto];
        for(int x=0;x<ancho;x++)
        {
            for (int y=0;y<alto;y++)
            {
                CeldaJSON celdaJSON = new CeldaJSON();
                celdaJSON.fromCelda(escenario.getCelda(x,y));
                this.celdaJSON[x][y] = celdaJSON;
            }
        }
    }
}
