package eetac.dsa.Controlador;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CargadorJSON {

    /*

    public static Escenario jsonAEscenario(String json) throws CargarDeJsonException {
        JSONObject object = new JSONObject(json.toString());
        object = object.getJSONObject("escenario");
        Escenario escenario = new Escenario(object.optString("nombre"), object.getInt("ancho"), object.getInt("alto"),object.getInt("nivelDeEscenario"));
        JSONArray celdas = object.getJSONArray("celdas");
        try {
            for (int i = 0; i < celdas.length(); i++) {
                JSONObject obj = celdas.getJSONObject(i);
                Class clase = Class.forName("eetac.dsa.Servidor.Controlador.Celdas." + obj.getString("tipo"));
                int x = obj.getInt("x");
                int y = obj.getInt("y");
                if (obj.isNull("extraArgs")) {
                    escenario.setCelda((Celda) clase.newInstance(), x, y);
                } else {
                    JSONObject extraArgs = obj.getJSONObject("extraArgs");
                    Iterator<String> parametros = extraArgs.keys();
                    Class[] tiposDeParametros = new Class[extraArgs.getInt("numeroParametros")];
                    Object[] valorParametros = new Object[extraArgs.getInt("numeroParametros")];
                    while (parametros.hasNext()) {
                        String nombre = parametros.next();
                        if (nombre.equals("numeroParametros")) continue;

                        int numeroParametro = Integer.parseInt(nombre.substring(3));
                        if (nombre.startsWith("Str")) {
                            tiposDeParametros[numeroParametro] = String.class;
                            valorParametros[numeroParametro] = (Object) extraArgs.getString(nombre);
                            continue;
                        }
                        if (nombre.startsWith("int")) {
                            tiposDeParametros[numeroParametro] = int.class;
                            valorParametros[numeroParametro] = (Object) extraArgs.getInt(nombre);
                        }
                        if (nombre.startsWith("dou")) {
                            tiposDeParametros[numeroParametro] = double.class;
                            valorParametros[numeroParametro] = (Object) extraArgs.getDouble(nombre);
                            continue;
                        }
                        if (nombre.startsWith("flo")) {
                            tiposDeParametros[numeroParametro] = float.class;
                            valorParametros[numeroParametro] = (Object) extraArgs.getFloat(nombre);
                            continue;
                        }
                    }
                    escenario.setCelda((Celda) clase.getDeclaredConstructor(tiposDeParametros).newInstance(valorParametros), x, y);
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            logger.error("error: " + e.getLocalizedMessage());
            throw new CargarDeJsonException(e.getLocalizedMessage());
        }
        return escenario;
    }

    public static Monstruo jsonAMonstruo(String json) throws CargarDeJsonException {
        try {
            JSONObject monstruoJson = new JSONObject(json);
            Class monstruoClass = Class.forName("eetac.dsa.Servidor.Controlador.Monstruos." + monstruoJson.getString("tipo"));
            Monstruo monstruo = (Monstruo) monstruoClass.getDeclaredConstructor(int.class, int.class).newInstance((Object) monstruoJson.getInt("nivel"), (Object) monstruoJson.getInt("experiencia"));
            monstruo.setVidaActual(monstruoJson.getInt("vida"));
            return monstruo;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new CargarDeJsonException(e.getLocalizedMessage());
        }
    }

    public static Objeto jsonAObjeto(String json) throws CargarDeJsonException {
        try {
            JSONObject objetoJson = new JSONObject(json);
            Class objetoClass = Class.forName("eetac.dsa.Servidor.Controlador.Objetos." + objetoJson.getString("tipo"));

            JSONObject extraArgs = objetoJson.getJSONObject("extraArgs");
            Iterator<String> parametros = extraArgs.keys();
            Class[] tiposDeParametros = new Class[extraArgs.getInt("numeroParametros")];
            Object[] valorParametros = new Object[extraArgs.getInt("numeroParametros")];
            while (parametros.hasNext()) {
                String nombre = parametros.next();
                if (nombre.equals("numeroParametros")) continue;

                int numeroParametro = Integer.parseInt(nombre.substring(3));
                if (nombre.startsWith("Str")) {
                    tiposDeParametros[numeroParametro] = String.class;
                    valorParametros[numeroParametro] = (Object) extraArgs.getString(nombre);
                    continue;
                }
                if (nombre.startsWith("int")) {
                    tiposDeParametros[numeroParametro] = int.class;
                    valorParametros[numeroParametro] = (Object) extraArgs.getInt(nombre);
                }
                if (nombre.startsWith("dou")) {
                    tiposDeParametros[numeroParametro] = double.class;
                    valorParametros[numeroParametro] = (Object) extraArgs.getDouble(nombre);
                    continue;
                }
                if (nombre.startsWith("flo")) {
                    tiposDeParametros[numeroParametro] = float.class;
                    valorParametros[numeroParametro] = (Object) extraArgs.getFloat(nombre);
                    continue;
                }
            }
            return (Objeto) objetoClass.getDeclaredConstructor(tiposDeParametros).newInstance(valorParametros);
        } catch (Exception e) {
            throw new CargarDeJsonException(e.getLocalizedMessage());
        }
    }

    public static Personaje jsonAPersonaje(String json) throws CargarDeJsonException {
        JSONObject personajeJson = new JSONObject(json);
        Personaje personaje = null;
        int x = personajeJson.getInt("x");
        int y = personajeJson.getInt("y");
        String nombrePersonaje = personajeJson.getString("nombre");
        boolean genero = personajeJson.getBoolean("genero");
        if (personajeJson.getString("tipo").equals("Usuario")) {

            personaje = new Usuario(nombrePersonaje, x, y, genero, personajeJson.getString("email"), personajeJson.getString("password"));
        }
        if (personajeJson.getString("tipo").equals("Enemigo")) {

            personaje = new Enemigo(nombrePersonaje, x, y, genero);
        }
        JSONArray monstruosJson = personajeJson.getJSONArray("monstruos");
        for (int k = 0; k < monstruosJson.length(); k++) {
            JSONObject monstruoJson = monstruosJson.getJSONObject(k);

            personaje.añadirMonstruo(CargadorJSON.jsonAMonstruo(monstruoJson.toString()));
        }

        JSONArray objetosJson = personajeJson.getJSONArray("objetos");
        for (int k = 0; k < objetosJson.length(); k++) {
            JSONObject objetoJson = objetosJson.getJSONObject(k);
            personaje.getInventario().añadirObeto(CargadorJSON.jsonAObjeto(objetoJson.toString()));
        }
        return personaje;
    }

    public static HashMap<String,Personaje> jsonAPersonajes(String json)throws CargarDeJsonException
    {
        HashMap<String,Personaje> personajes = new HashMap<String, Personaje>();
        JSONObject object = new JSONObject(json.toString());
        JSONArray personajesJson = object.getJSONArray("personajes");
        for (int i = 0;i<personajesJson.length();i++) {
            Personaje p = CargadorJSON.jsonAPersonaje(personajesJson.getJSONObject(i).toString());
            personajes.put(p.getNombre(),p);
        }
        return personajes;
    }

    public static String ficheroAJSON(String direccion) throws IOException
    {


        BufferedReader br = null;
        FileReader fr = null;
        StringBuffer jsonString = null;

        //br = new BufferedReader(new FileReader(FILENAME));
        fr = new FileReader(direccion);
        br = new BufferedReader(fr);

        String sCurrentLine;
        jsonString = new StringBuffer();

        while ((sCurrentLine = br.readLine()) != null) {
            jsonString.append(sCurrentLine);
        }
        return jsonString.toString();

    }

    public static String objetoAJSON(Objeto o) throws CargarDeJsonException
    {
        try {
            Class objetoClass = o.getClass();
            Method[] metodos = objetoClass.getDeclaredMethods();
            Method[] metodosFinales = new Method[metodos.length];
            int numeroMetodos = 0;

            for (Method m:metodos) {
                Annotation[] annotations = m.getDeclaredAnnotations();
                Annotation a = m.getAnnotation(OrdenParametro.class);
                if (a != null && a instanceof OrdenParametro){
                    final OrdenParametro anotacion = (OrdenParametro) a;
                    metodosFinales[anotacion.indice()] = m;
                    numeroMetodos++;
                }

            }

            StringBuffer resultado = new StringBuffer("{\"tipo\":\"");
            resultado.append(objetoClass.getSimpleName());

            if(numeroMetodos==0){
            resultado.append("\"}");
            return resultado.toString();
            }


            resultado.append("\",\"extraArgs\":{\"numeroParametros\":");
            resultado.append(numeroMetodos);
            resultado.append(',');
            for(int i =0;i<numeroMetodos;i++)
            {
                if(metodosFinales[i].getReturnType() == String.class)
                {
                    resultado.append("\"Str");
                    resultado.append(i);
                    resultado.append("\":\"");
                    resultado.append((String)metodosFinales[i].invoke(o,null));
                    resultado.append("\",");
                }
                if(metodosFinales[i].getReturnType() == int.class)
                {
                    resultado.append("\"int");
                    resultado.append(i);
                    resultado.append("\":");
                    resultado.append((int)metodosFinales[i].invoke(o,null));
                    resultado.append(',');
                }
                if(metodosFinales[i].getReturnType() == float.class)
                {
                    resultado.append("\"flo");
                    resultado.append(i);
                    resultado.append("\":");
                    resultado.append((float)metodosFinales[i].invoke(o,null));
                    resultado.append(',');
                }
                if(metodosFinales[i].getReturnType() == double.class)
                {
                    resultado.append("\"dou");
                    resultado.append(i);
                    resultado.append("\":");
                    resultado.append((double)metodosFinales[i].invoke(o,null));
                    resultado.append(',');
                }
            }
            resultado.deleteCharAt(resultado.length()-1);
            resultado.append("}}");

            return resultado.toString();
        }
        catch (IllegalAccessException|InvocationTargetException e)
        {
            throw new CargarDeJsonException(e.getLocalizedMessage());
        }
    }

    public static String monstruoAJson(Monstruo monstruo) throws CargarDeJsonException
    {
        try {
            StringBuffer resultado = new StringBuffer("{\"tipo\":\"");
            resultado.append(monstruo.getClass().getSimpleName());
            resultado.append("\",\"nivel\":");
            resultado.append(monstruo.getNivel());
            resultado.append(",\"vida\":");
            resultado.append(monstruo.getVidaActual());
            resultado.append(",\"experiencia\":");
            resultado.append(monstruo.getExperiencia());
            resultado.append("}");
            return resultado.toString();
        }
        catch (Exception e)
        {
            throw new CargarDeJsonException(e.getLocalizedMessage());
        }
    }

    public static String personajeAJson(Personaje personaje) throws CargarDeJsonException
    {
        try {
            StringBuffer resultado = new StringBuffer("{");

            if(personaje.getClass()==Usuario.class)
            {
                Usuario tmp = (Usuario)personaje;
                resultado.append("\"tipo\":\"Usuario\",\"password\":\"");
                resultado.append(tmp.getPassword());
                resultado.append("\",\"email\":\"");
                resultado.append(tmp.getEmail());
                resultado.append("\",");
            }
            if(personaje.getClass()==Enemigo.class)
            {
                resultado.append("\"tipo\":\"Enemigo\",");
            }
            resultado.append("\"nombre\":\"");
            resultado.append(personaje.getNombre());
            resultado.append("\",\"x\":");
            resultado.append((int)personaje.getPosicion().getX());
            resultado.append(",\"y\":");
            resultado.append((int)personaje.getPosicion().getY());
            resultado.append(",\"genero\":");
            resultado.append(personaje.getGenero());
            resultado.append(",\"monstruos\":[");
            for(int i = 0;i< personaje.getLista_montruos().getTamaño();i++)
            {
                resultado.append(CargadorJSON.monstruoAJson(personaje.getLista_montruos().obtenerMonstruo(i)));
                resultado.append(',');
            }
            if(personaje.getLista_montruos().getTamaño()>0)
            {
                resultado.deleteCharAt(resultado.length()-1);
            }

            resultado.append("],\"objetos\":[");
            for(int i = 0;i< personaje.getInventario().obtenerTamaño();i++)
            {
                resultado.append(CargadorJSON.objetoAJSON(personaje.getInventario().buscarObjeto(i)));
                resultado.append(',');
            }
            if(personaje.getInventario().obtenerTamaño()>0)
            {
                resultado.deleteCharAt(resultado.length()-1);
            }
            resultado.append("]}");
            return resultado.toString();

        }
        catch (Exception e)
        {
            throw new CargarDeJsonException(e.getLocalizedMessage());
        }
    }

    public static String personajesAJson(List<Personaje> personajes) throws CargarDeJsonException
    {
        StringBuffer resultado = new StringBuffer("{\"personajes\":[");
        int i;
        for (i = 0;i<personajes.size();i++)
        {
            resultado.append(CargadorJSON.personajeAJson(personajes.get(i)));
            resultado.append(',');
        }
        if(i!=0)resultado.deleteCharAt(resultado.length()-1);


        resultado.append("]}");
        return resultado.toString();
    }

    public static String escenarioParaClienteJson(Escenario escenario)
    {
        StringBuffer resultado = new StringBuffer("{\"nombre\":");
        resultado.append(escenario.getNombre());
        resultado.append(",\"ancho\":");
        resultado.append(escenario.getAncho());
        resultado.append(",\"alto\":");
        resultado.append(escenario.getAlto());
        resultado.append(",\"nivelDeEscenario\":");
        resultado.append(escenario.getNivelDeZona());
        resultado.append(",\"celdas\":[");
        int x=0;
        int y=0;
        for(x =0;x<escenario.getAncho();x++) {
            for (y = 0; y < escenario.getAlto(); y++) {
                resultado.append("{\"tipo\":\"");
                resultado.append(escenario.getCelda(x, y).getTipo());
                resultado.append("\",\"x\":");
                resultado.append(x);
                resultado.append("\",\"y\":");
                resultado.append(y);
                resultado.append("},");

            }
        }

        if(x!=0||y!=0)
        {
            resultado.deleteCharAt(resultado.length()-1);
        }
        resultado.append("]}");
        return  resultado.toString();
    }

    public static String Lista_MonstruosAJson(Lista_Monstruos monstruos) throws CargarDeJsonException
    {
        StringBuffer resultado = new StringBuffer("{\"monstruos\":[");
        int x =0;
        for(x=0;x<monstruos.getTamaño();x++)
        {
          resultado.append(CargadorJSON.monstruoAJson(monstruos.obtenerMonstruo(x)));
        }
        resultado.append("]}");
        return resultado.toString();
    }
    */
}
