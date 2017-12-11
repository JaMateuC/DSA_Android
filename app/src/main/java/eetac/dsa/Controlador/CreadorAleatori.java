package eetac.dsa.Controlador;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

//TODO(a medida que creemos objetos y monstruos hay que añadirlos en el creadorAleatorio)
public class CreadorAleatori
{
    /*
    static Objeto crearObjeto(int rango) throws CargarDeJsonException, IOException
    {
        JSONObject object = new JSONObject(CargadorJSON.ficheroAJSON("src/main/resources/objetosAleatorios.json"));
        JSONArray listaNiveles = object.getJSONArray("nivelesDeEscenario");

        JSONArray objetos = listaNiveles.getJSONObject(rango).getJSONArray("objetos");


        double aleatori = Math.random();

        aleatori *= objetos.length();
        int index = (int)aleatori;

        return  CargadorJSON.jsonAObjeto(objetos.getJSONObject(index).toString());
    }

    static Monstruo crearMonstruo(int rango)throws CargarDeJsonException, IOException
    {
        JSONObject object = new JSONObject(CargadorJSON.ficheroAJSON("src/main/resources/monstruosAleatorios.json"));
        JSONArray listaNiveles = object.getJSONArray("nivelesDeEscenario");

        JSONArray objetos = listaNiveles.getJSONObject(rango).getJSONArray("monstruos");


        double aleatori = Math.random();

        aleatori *= objetos.length();
        int index = (int)aleatori;

        return  CargadorJSON.jsonAMonstruo(objetos.getJSONObject(index).toString());
    }
    */
}
