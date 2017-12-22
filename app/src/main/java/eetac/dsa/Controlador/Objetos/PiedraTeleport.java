package eetac.dsa.Controlador.Objetos;


import android.util.Log;

import eetac.dsa.Controlador.Monstruo;
import eetac.dsa.Controlador.Mundo;
import eetac.dsa.Controlador.Objeto;
import eetac.dsa.Controlador.Personaje;


public class PiedraTeleport extends Objeto
{

    private int posicionX;
    private int posicionY;
    private String escenario;

    public PiedraTeleport(int posicionX, int posicionY, String escenario) {
        super("Piedra de teletransporte", "Teleporta a una posicion neutral deseada", "Teleport",Destino.Personaje);
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.escenario = escenario;
    }

    @OrdenParametro(indice = 0)
    public int getPosicionX() {
        return posicionX;
    }

    @OrdenParametro(indice = 1)
    public int getPosicionY() {
        return posicionY;
    }

    @OrdenParametro(indice = 2)
    public String getEscenario() {
        return escenario;
    }


    @Override
    public void funcion(Personaje personaje) {

        try {
            Mundo.getIns().cambiarMapa(escenario, posicionX, posicionY);
            Mundo.getIns().updateUsuario();
        }
        catch (Exception e)
        {
            Log.d("celdaCambioEscenario","error al cambiar el escenario");
        }
    }

    @Override
    public void funcion(Monstruo monstruo) {

    }
}
