package eetac.dsa.juego.vista;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import eetac.dsa.juego.Controlador.Escenario;
import eetac.dsa.juego.root.Mundo;

/**
 * Created by oscar on 25/12/2017.
 */

public class MundoDraw {
    final private static int TAMAÑO_CELDA = 100;

    public static void drawMapa(Canvas canvas, Paint paint)
    {
        Escenario escenario = Mundo.getIns().getEscenario();
        for(int x=0;x<escenario.getAncho();x++)
        {
            for(int y=0;y<escenario.getAlto();y++)
                drawCelda(canvas,paint,x,y);
        }
    }

    static void drawCelda(Canvas canvas,Paint paint,int x,int y)
    {
        if(Mundo.getIns().getCelda(x,y).getTipo().equals("CeldaPared"))
            paint.setARGB(255,0,0,0);
        if(Mundo.getIns().getCelda(x,y).getTipo().equals("CeldaCesped"))
            paint.setARGB(255,0,255,0);
        if(Mundo.getIns().getCelda(x,y).getTipo().equals("CeldaCambioEscenario"))
            paint.setARGB(255,255,0,0);
        canvas.drawRect(new Rect(x*TAMAÑO_CELDA,y*TAMAÑO_CELDA,(x+1)*TAMAÑO_CELDA,(y+1)*TAMAÑO_CELDA),paint);
    }
}
