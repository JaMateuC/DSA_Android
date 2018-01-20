package eetac.dsa.juego.vista;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Jaume on 07/01/2018.
 */

public class SpritePersonaje {

    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int direccion;
    private int x;
    private int y;
    private int tamanoCelda;


    public SpritePersonaje(Bitmap bmp, int direccion, int x, int y, int tamanoCelda) {

        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.direccion = direccion;
        this.y = y;
        this.x = x;
        this.tamanoCelda = tamanoCelda;

    }

    public void update(int direccion, int x, int y) {

        this.direccion = direccion;
        this.y = y;
        this.x = x;
    }

    private void update() {

        //currentFrame = ++currentFrame % BMP_COLUMNS;

    }

    public void onDraw(Canvas canvas) {

        update();
        int srcX = currentFrame * width;
        int srcY = direccion * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x - tamanoCelda/2, y- tamanoCelda/2, x + tamanoCelda/2, y + tamanoCelda/2);
        canvas.drawBitmap(bmp, src, dst, null);

    }

}
