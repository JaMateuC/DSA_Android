package eetac.dsa.model;

import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Objetos.OrdenParametro;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;

public class CeldaJSON {
    String tipo;
    int numArgs;
    String args;

    public CeldaJSON() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNumArgs() {
        return numArgs;
    }

    public void setNumArgs(int numArgs) {
        this.numArgs = numArgs;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }



    public Celda toCelda() throws Exception
    {
        Class clase = Class.forName("eetac.dsa.juego.Controlador.Celdas." + tipo);
        if(numArgs!=0) {
            JSONObject extraArgs = new JSONObject(args);
            Iterator<String> parametros = extraArgs.keys();
            Class[] tiposDeParametros = new Class[numArgs];
            Object[] valorParametros = new Object[numArgs];
            while (parametros.hasNext()) {
                String nombre = parametros.next();
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
                    valorParametros[numeroParametro] = (Object) extraArgs.getDouble(nombre);
                    continue;
                }
            }
            return (Celda) clase.getDeclaredConstructor(tiposDeParametros).newInstance(valorParametros);
        }
        return (Celda) clase.getDeclaredConstructor().newInstance();
    }

    public void fromCelda(Celda celda) throws Exception
    {
        if(celda==null)return;
        Class objetoClass = celda.getClass();
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

        tipo = objetoClass.getSimpleName();

        if(numeroMetodos==0){
            return;
        }
        numArgs = numeroMetodos;

        StringBuffer args = new StringBuffer("{");
        for(int i =0;i<numeroMetodos;i++)
        {
            if(metodosFinales[i].getReturnType() == String.class)
            {
                args.append("\"Str");
                args.append(i);
                args.append("\":\"");
                args.append((String)metodosFinales[i].invoke(celda,null));
                args.append("\",");
            }
            if(metodosFinales[i].getReturnType() == int.class)
            {
                args.append("\"int");
                args.append(i);
                args.append("\":");
                args.append((int)metodosFinales[i].invoke(celda,null));
                args.append(',');
            }
            if(metodosFinales[i].getReturnType() == float.class)
            {
                args.append("\"flo");
                args.append(i);
                args.append("\":");
                args.append((float)metodosFinales[i].invoke(celda,null));
                args.append(',');
            }
            if(metodosFinales[i].getReturnType() == double.class)
            {
                args.append("\"dou");
                args.append(i);
                args.append("\":");
                args.append((double)metodosFinales[i].invoke(celda,null));
                args.append(',');
            }
        }
        args.deleteCharAt(args.length()-1);
        args.append("}");
        this.args = args.toString();

    }
}
