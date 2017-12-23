package eetac.dsa.juego.Controlador.Objetos;


import android.util.Log;

import eetac.dsa.juego.Controlador.Monstruo;
import eetac.dsa.juego.root.Mundo;
import eetac.dsa.juego.Controlador.Objeto;
import eetac.dsa.juego.Controlador.Personaje;


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
