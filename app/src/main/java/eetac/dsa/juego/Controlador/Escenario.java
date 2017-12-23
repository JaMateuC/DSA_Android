package eetac.dsa.juego.Controlador;

import eetac.dsa.juego.Controlador.Excepciones.CeldaFueraDeRangoException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Escenario {

    private Celda[][] escenario;
    private String nombre;
    private int ancho;
    private int alto;
    private int nivelDeZona;

    public Escenario(String nombre, int ancho, int alto,int nivelDeZona) {
        this.nombre = nombre;
        this.ancho = ancho;
        this.alto = alto;
        escenario = new Celda[ancho][alto];
        this.nivelDeZona = nivelDeZona;
    }

    public Escenario(){}

    public String getNombre() {
        return nombre;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getNivelDeZona() {
        return nivelDeZona;
    }

    public void setNivelDeZona(int nivelDeZona) {
        this.nivelDeZona = nivelDeZona;
    }

    public Celda getCelda(int x, int y)
    {
        if(x<0||y<0||x>ancho||y>alto)throw new CeldaFueraDeRangoException();
        return escenario[x][y];
    }

    public void setCelda(Celda celda,int x,int y)
    {

        if(x<0||y<0||x>ancho||y>alto)throw new CeldaFueraDeRangoException();
        escenario[x][y] = celda;
    }

    public static Escenario cargarEscenario(String direccion)
    {
            BufferedReader br = null;
            FileReader fr = null;
            StringBuffer jsonString=null;

            try {

                //br = new BufferedReader(new FileReader(FILENAME));
                fr = new FileReader(direccion);
                br = new BufferedReader(fr);

                String sCurrentLine;
                jsonString = new StringBuffer();

                while ((sCurrentLine = br.readLine()) != null) {
                    jsonString.append(sCurrentLine);
                }

            } catch (IOException e) {

                e.printStackTrace();
                //throw new CargarDeJsonException(e.getLocalizedMessage());
            }
            /*try {
                //return CargadorJSON.jsonAEscenario(jsonString.toString());
                return null;
            }
            catch (CargarDeJsonException e)
            {
                return null;
            }*/
            return null;
    }

}
