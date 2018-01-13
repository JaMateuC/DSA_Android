package eetac.dsa.juego.vista;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import eetac.dsa.R;
import eetac.dsa.juego.Controlador.Escenario;
import eetac.dsa.juego.JuegoActivity;
import eetac.dsa.juego.root.Mundo;

public class MundoDraw
{
    final private static int TAMAÑO_CELDA = 100;
    private static SpritePersonaje personaje;

    public static void drawMapa(Canvas canvas, Paint paint,int ancPant,int altPant,
                                Resources mResource, int direccion)
    {
        Escenario escenario = Mundo.getIns().getEscenario();
        int offsetx = Mundo.getIns().getUsuario().getPosicion().x*TAMAÑO_CELDA;
        int offsety = Mundo.getIns().getUsuario().getPosicion().y*TAMAÑO_CELDA;

        //Celda
        for(int x=0;x<escenario.getAncho();x++)
        {
            for(int y=0;y<escenario.getAlto();y++)
                drawCelda(canvas,paint,x,y,offsetx,offsety,ancPant,altPant);
        }

        //Personaje
        //paint.setARGB(255,100,100,100);
        //canvas.drawCircle(ancPant/2+TAMAÑO_CELDA/2,altPant/2+TAMAÑO_CELDA/2,TAMAÑO_CELDA/4, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(mResource,R.drawable.personaje_hombre);
        personaje = new SpritePersonaje(bitmap, direccion, ancPant/2+TAMAÑO_CELDA/2, altPant/2+TAMAÑO_CELDA/2, TAMAÑO_CELDA);
        personaje.onDraw(canvas);
        /*      FUTURO PERSONAJE
        Bitmap btm_hombre = BitmapFactory.decodeResource(  AQUI VA EL CONTEXT DE JuegoActivity  , R.drawable.personaje_hombre);
        canvas.setBitmap(btm_hombre);
        */
    }

    static void drawCelda(Canvas canvas,Paint paint,int x,int y,int offsetx,int offsety,int ancPant,int altPant)
    {
        //Establece el contenido de la celda
        if(Mundo.getIns().getCelda(x,y).getTipo().equals("CeldaPared"))
            paint.setARGB(255,0,0,0);
        if(Mundo.getIns().getCelda(x,y).getTipo().equals("CeldaCesped"))
            paint.setARGB(255,0,255,0);
        if(Mundo.getIns().getCelda(x,y).getTipo().equals("CeldaCambioEscenario"))
            paint.setARGB(255,255,0,0);

        //Dibuja la celda
        canvas.drawRect(new Rect(ancPant/2-offsetx+x*TAMAÑO_CELDA,altPant/2-offsety+y*TAMAÑO_CELDA,
                ancPant/2-offsetx+(x+1)*TAMAÑO_CELDA,altPant/2-offsety+(y+1)*TAMAÑO_CELDA),paint);
    }
}