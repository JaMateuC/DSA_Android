package eetac.dsa.juego.vista;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import eetac.dsa.R;
import eetac.dsa.juego.Controlador.Celda;
import eetac.dsa.juego.Controlador.Escenario;
import eetac.dsa.juego.Controlador.Monstruo;
import eetac.dsa.juego.Controlador.combate.Combate;
import eetac.dsa.juego.Controlador.combate.MonstruoEntidad;
import eetac.dsa.juego.root.Mundo;

public class MundoDraw
{
    final private static int TAMAÑO_CELDA = 100;
    private SpritePersonaje personaje;
    Resources mResource;
    Bitmap personajeBitmap;
    Bitmap mapaBitmap;
    Bitmap combatBitmap;
    Bitmap robot;
    Rect dst;
    Rect src;


    public MundoDraw(Resources mResource, boolean genero) {
        this.mResource = mResource;
        if(genero){
            personajeBitmap = BitmapFactory.decodeResource(mResource,R.drawable.personaje_hombre);
        }else{
            personajeBitmap = BitmapFactory.decodeResource(mResource,R.drawable.personaje_mujer);
        }

        mapaBitmap = BitmapFactory.decodeResource(mResource,R.drawable.map_32x32);
        combatBitmap = BitmapFactory.decodeResource(mResource,R.drawable.combat_fons);
        robot = BitmapFactory.decodeResource(mResource,R.drawable.robot);
        dst = new Rect();
        src = new Rect();
    }


    public void drawMapa(Canvas canvas, Paint paint,int ancPant,int altPant, int direccion)
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
        if(personaje==null)
            personaje = new SpritePersonaje(personajeBitmap, direccion, ancPant/2+TAMAÑO_CELDA/2, altPant/2+TAMAÑO_CELDA/2, TAMAÑO_CELDA);
        else personaje.update(direccion,ancPant/2+TAMAÑO_CELDA/2, altPant/2+TAMAÑO_CELDA/2);
        personaje.onDraw(canvas);
    }

    static int xCombateToCanvas(float x,float xrel)
    {
        return  (int)((x+Combate.ANCHOESCENARIO/2)*xrel);
    }

    static int yCombateToCanvas(float y,float xrel)
    {
        return (int)(Combate.ALTOESCENARIO/2*xrel-y*xrel);
    }

    void drawCombate(Canvas canvas,Paint paint,int ancPant,int altPant)
    {
        MonstruoEntidad monstruoEnt = Mundo.getIns().getCombate().getMonstruo();
        MonstruoEntidad enemigoEnt = Mundo.getIns().getCombate().getEnemigo();
        float xrel = ancPant/ (float)Combate.ANCHOESCENARIO;
        float yrel = altPant/(float)Combate.ALTOESCENARIO;
        src.set(0,0,combatBitmap.getWidth(),combatBitmap.getHeight());
        dst.set(0,0,ancPant,altPant);

        canvas.drawBitmap(combatBitmap, src, dst, null);

        paint.setARGB(255,255,0,0);
        /*canvas.drawRect(new Rect(0,(int)(Combate.ALTOESCENARIO*xrel-1*xrel),
                (int)(Combate.ANCHOESCENARIO*xrel),(int)(Combate.ALTOESCENARIO*xrel)),paint);*/
        src.set(0,0,robot.getWidth(),robot.getHeight());
        int xCen = xCombateToCanvas(monstruoEnt.getBody().getPosition().x,xrel);
        int yCen = yCombateToCanvas(monstruoEnt.getBody().getPosition().y,xrel);

        dst.set((int)(xCen-1.5*xrel),(int)(yCen-1.5*xrel),(int)(xCen+1.5*xrel),(int)(yCen+1.5*xrel));
        canvas.drawBitmap(robot, src, dst, null);

        xCen = xCombateToCanvas(enemigoEnt.getBody().getPosition().x,xrel);
        yCen = yCombateToCanvas(enemigoEnt.getBody().getPosition().y,xrel);

        dst.set((int)(xCen-1.5*xrel),(int)(yCen-1.5*xrel),(int)(xCen+1.5*xrel),(int)(yCen+1.5*xrel));
        canvas.drawBitmap(robot, src, dst, null);

        /*canvas.drawCircle(
                xCombateToCanvas(monstruoE.getBody().getPosition().x,xrel),
                yCombateToCanvas(monstruoE.getBody().getPosition().y,xrel),
                0.75F*xrel, paint);*/

        /*canvas.drawCircle(
                xCombateToCanvas(Mundo.getIns().getCombate().getEnemigo().getBody().getPosition().x,xrel),
                yCombateToCanvas(Mundo.getIns().getCombate().getEnemigo().getBody().getPosition().y,xrel),
                0.75F*xrel, paint);*/


        paint.setARGB(255,255,0,0);

        Monstruo monstruo = Mundo.getIns().getCombate().getMonstruo().getMonstruo();
        float vidaRestante = monstruo.getVidaActual()/
                (float)monstruo.getVidaEfectiva();

        Monstruo monstruoE = Mundo.getIns().getCombate().getEnemigo().getMonstruo();
        float vidaRestanteE = monstruoE.getVidaActual()/
                (float)monstruoE.getVidaEfectiva();

        //vida del monstruo protagonista
        canvas.drawRect(new Rect((int)(ancPant*0.05),20,
                (int)(ancPant*(0.05+0.4*vidaRestante)),60),paint);


        //vida del monstruo enemigo
        canvas.drawRect(new Rect((int)(ancPant*0.55),20,
                (int)(ancPant*(0.55+0.4*vidaRestanteE)),60),paint);

        paint.setARGB(255,0,0,0);
        paint.setTextSize(25);
        canvas.drawText(monstruoE.getTipo()+" NVL:"+monstruoE.getNivel(),(float)(ancPant*0.55),100,paint);
        canvas.drawText(monstruo.getTipo()+" NVL:"+monstruo.getNivel(),(float)(ancPant*0.05),100,paint);
    }

    void drawCelda(Canvas canvas,Paint paint,int x,int y,int offsetx,int offsety,int ancPant,int altPant)
    {
        //Establece el contenido de la celda
        int rel = mapaBitmap.getWidth()/23;
        Celda celda = Mundo.getIns().getCelda(x,y);

        if(celda.getTipo().equals("CeldaPared")) {
            src.set(rel * 4, rel * 3, rel * 5, rel * 4);
        }
        if(celda.getTipo().equals("CeldaCesped")) {

            switch (celda.getRestriction())
            {
                case 0:
                    src.set(rel * 1, rel * 2, rel * 2, rel * 3);
                    break;
                case Celda.REST_UP:
                    src.set(rel * 1, rel * 1, rel * 2, rel * 2);
                    break;

                case Celda.REST_DOWN:
                    src.set(rel * 1, rel * 3, rel * 2, rel * 4);
                    break;

                case Celda.REST_LEFT:
                    src.set(rel *0, rel * 2, rel * 1, rel * 3);
                    break;

                case Celda.REST_RIGHT:
                    src.set(rel * 2, rel * 2, rel * 3, rel * 3);
                    break;

                case Celda.REST_DOWN+Celda.REST_UP:
                    src.set(rel * 2, rel * 4, rel * 3, rel * 5);
                    break;

                case Celda.REST_LEFT+Celda.REST_RIGHT:
                    src.set(rel * 0, rel * 4, rel * 1, rel * 5);
                    break;

            }

        }
        if(celda.getTipo().equals("CeldaCambioEscenario"))
            src.set(rel*19,rel*12,rel*20,rel*13);

        //Dibuja la celda
        dst.set(ancPant/2-offsetx+x*TAMAÑO_CELDA,altPant/2-offsety+y*TAMAÑO_CELDA,
                ancPant/2-offsetx+(x+1)*TAMAÑO_CELDA,altPant/2-offsety+(y+1)*TAMAÑO_CELDA);
        //canvas.drawRect(new Rect(ancPant/2-offsetx+x*TAMAÑO_CELDA,altPant/2-offsety+y*TAMAÑO_CELDA,
                //ancPant/2-offsetx+(x+1)*TAMAÑO_CELDA,altPant/2-offsety+(y+1)*TAMAÑO_CELDA),paint);
        canvas.drawBitmap(mapaBitmap, src, dst, null);
    }
}
