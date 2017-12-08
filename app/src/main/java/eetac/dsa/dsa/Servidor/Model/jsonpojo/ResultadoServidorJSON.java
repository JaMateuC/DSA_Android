package eetac.dsa.dsa.Servidor.Model.jsonpojo;

import eetac.dsa.Flag;
import eetac.dsa.Servidor.Controlador.Escenario;
import eetac.dsa.Servidor.Controlador.Lista_Monstruos;
import eetac.dsa.Servidor.Controlador.Monstruo;
import eetac.dsa.Servidor.Controlador.Usuario;
import eetac.dsa.Servidor.ResultadoServidor;

import java.awt.*;

public class ResultadoServidorJSON {
    long flag;
    EscenarioJSON escenario;
    int x;
    int y;
    UsuarioJSON protagonista;
    ObjetoJSON objeto;
    int indiceObjeto;
    MonstruoJSON monstruo;

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    public EscenarioJSON getEscenario() {
        return escenario;
    }

    public void setEscenario(EscenarioJSON escenario) {
        this.escenario = escenario;
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

    public UsuarioJSON getProtagonista() {
        return protagonista;
    }

    public void setProtagonista(UsuarioJSON protagonista) {
        this.protagonista = protagonista;
    }

    public ObjetoJSON getObjeto() {
        return objeto;
    }

    public void setObjeto(ObjetoJSON objeto) {
        this.objeto = objeto;
    }

    public int getIndiceObjeto() {
        return indiceObjeto;
    }

    public void setIndiceObjeto(int indiceObjeto) {
        this.indiceObjeto = indiceObjeto;
    }

    public MonstruoJSON getMonstruo() {
        return monstruo;
    }

    public void setMonstruo(MonstruoJSON monstruo) {
        this.monstruo = monstruo;
    }


    public ResultadoServidorJSON() {
    }

    public ResultadoServidor toResultadoServidor() throws Exception
    {
        ResultadoServidor resultadoServidor = new ResultadoServidor();
        resultadoServidor.setEscenario(escenario.toEscenario());
        resultadoServidor.setFlag(new Flag(flag));
        resultadoServidor.setIndiceObjeto(indiceObjeto);
        resultadoServidor.setMonstruo(monstruo.toMonstruo());
        resultadoServidor.setProtagonista(protagonista.toUsario());
        resultadoServidor.setPosicion(new Point(x,y));
        return resultadoServidor;
    }

    public void fromResultadoServidor(ResultadoServidor resultadoServidor) throws Exception {
        if (resultadoServidor == null) return;
        EscenarioJSON escenarioJSON = new EscenarioJSON();
        escenarioJSON.fromEscenario(resultadoServidor.getEscenario());
        this.escenario = escenarioJSON;
        this.flag = resultadoServidor.getFlag().toLong();
        if (resultadoServidor.getPosicion() != null) {
            this.x = (int) resultadoServidor.getPosicion().getX();
            this.y = (int) resultadoServidor.getPosicion().getY();
        }

        this.indiceObjeto = resultadoServidor.getIndiceObjeto();
        if (resultadoServidor.getObjeto() != null) {
            ObjetoJSON objetoJSON = new ObjetoJSON();
            objetoJSON.fromObjeto(resultadoServidor.getObjeto());
            this.objeto = objetoJSON;
        }

        if (resultadoServidor.getMonstruo() != null) {
            MonstruoJSON monstruoJSON = new MonstruoJSON();
            monstruoJSON.fromMonstruo(resultadoServidor.getMonstruo());
            this.monstruo = monstruoJSON;
        }

        if (resultadoServidor.getProtagonista() != null)
        {
            UsuarioJSON usuarioJSON = new UsuarioJSON();
        usuarioJSON.fromUsuario(resultadoServidor.getProtagonista());
        this.protagonista = usuarioJSON;
        }

    }

    public static ResultadoServidorJSON fromResultadoServidorStatic(ResultadoServidor resultadoServidor) throws Exception
    {
        ResultadoServidorJSON resultado = new ResultadoServidorJSON();
        resultado.fromResultadoServidor(resultadoServidor);
        return resultado;
    }
}
