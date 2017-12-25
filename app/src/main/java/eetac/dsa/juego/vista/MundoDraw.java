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

    public static void drawMapa(Canvas canvas, Paint paint,int ancPant,int altPant)
    {
        Escenario escenario = Mundo.getIns().getEscenario();
        int offsetx = Mundo.getIns().getUsuario().getPosicion().x*TAMAÑO_CELDA;
        int offsety = Mundo.getIns().getUsuario().getPosicion().y*TAMAÑO_CELDA;

        for(int x=0;x<escenario.getAncho();x++)
        {
            for(int y=0;y<escenario.getAlto();y++)
                drawCelda(canvas,paint,x,y,offsetx,offsety,ancPant,altPant);
        }
        paint.setARGB(255,100,100,100);
        canvas.drawCircle(ancPant/2+TAMAÑO_CELDA/2,altPant/2+TAMAÑO_CELDA/2,TAMAÑO_CELDA/4,paint);
    }

    static void drawCelda(Canvas canvas,Paint paint,int x,int y,int offsetx,int offsety,int ancPant,int altPant)
    {
        if(Mundo.getIns().getCelda(x,y).getTipo().equals("CeldaPared"))
            paint.setARGB(255,0,0,0);
        if(Mundo.getIns().getCelda(x,y).getTipo().equals("CeldaCesped"))
            paint.setARGB(255,0,255,0);
        if(Mundo.getIns().getCelda(x,y).getTipo().equals("CeldaCambioEscenario"))
            paint.setARGB(255,255,0,0);
        canvas.drawRect(new Rect(ancPant/2-offsetx+x*TAMAÑO_CELDA,altPant/2-offsety+y*TAMAÑO_CELDA,
                ancPant/2-offsetx+(x+1)*TAMAÑO_CELDA,altPant/2-offsety+(y+1)*TAMAÑO_CELDA),paint);
    }
}
