package com.jnu.student;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;
public class GameEngine  {
    private ImageView targetImageView;
    private Bitmap bookBitmap;
    private SurfaceHolder surfaceHolder;
    private Thread gameThread;
    private boolean isRunning;
    private GameEventListener gameEventListener;
    private Random random;
    private boolean isHit;
    private int targetX, targetY;
    private int targetRadius;
    public interface GameEventListener {
        void onHit();
    }
    public void updateSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public GameEngine(SurfaceHolder surfaceHolder, GameEventListener gameEventListener,Bitmap bookBitmap) {
        this.surfaceHolder = surfaceHolder;
        this.gameEventListener = gameEventListener;
        random = new Random();
        targetRadius = 50;
        this.bookBitmap = bookBitmap;
    }
    void moveTarget() {
        targetX = random.nextInt(surfaceHolder.getSurfaceFrame().width() - targetRadius * 2) + targetRadius;
        targetY = random.nextInt(surfaceHolder.getSurfaceFrame().height() - targetRadius * 2) + targetRadius;
    }
    public void start() {
        isRunning = true;
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        });
        gameThread.start();
    }
    public void stop() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void gameLoop() {
        while (isRunning) {
            update();
            draw();
        }
    }
    private void update() {
        if (isHit) {
            moveTarget();
            isHit = false;
        }
    }
    private void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            if (bookBitmap != null) {
                canvas.drawBitmap(bookBitmap, targetX, targetY, null);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();
            if (touchX >= targetX && touchX <= targetX + bookBitmap.getWidth()
                    && touchY >= targetY && touchY <= targetY + bookBitmap.getHeight()) {
                isHit = true;
                gameEventListener.onHit();
            }
        }
        return true;
    }
}
