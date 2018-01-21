package eetac.dsa.juego.vista;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import eetac.dsa.juego.root.Mundo;

/**
 * Created by oscar on 24/12/2017.
 */

public class JuegoView extends SurfaceView implements Runnable{

    Context context;

    SurfaceHolder holder;

    Thread gameThread = null;

    Paint paint;

    Resources mResources;

    volatile boolean playing;

    Mundo mundo;

    int direccion;
    MundoDraw mundoDraw;

    public Resources getmResources() {
        return mResources;
    }

    public void setmResources(Resources mResources) {
        this.mResources = mResources;
        mundoDraw = new MundoDraw(mResources);
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public JuegoView(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
        holder = getHolder();
        paint = new Paint();
        playing = true;
        mundo = Mundo.getIns();
    }

    @Override
    public void run() {
        while(playing)
        {
            update();
            draw();
        }
    }

    public void update() {
        if(mundo.getEstado()== Mundo.FSM.combate)
        {
            Mundo.getIns().getCombate().step(1/15f);
        }
    }

    public synchronized void draw() {
        if (holder.getSurface().isValid()) {
            if(mundo.getEstado()== Mundo.FSM.play)
            {
                Canvas canvas = holder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                mundoDraw.drawMapa(canvas,paint,getWidth(),getHeight(), direccion);
                holder.unlockCanvasAndPost(canvas);
            }
            if(mundo.getEstado()== Mundo.FSM.combate)
            {
                Canvas canvas = holder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                mundoDraw.drawCombate(canvas,paint,getWidth(),getHeight());
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // If SimpleGameEngine Activity is started theb
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}
