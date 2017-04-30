package com.example.mytusshar.minionjump.bulletandexplode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.R;


/**
 * Created by mytusshar on 8/17/2016.
 */
public class Explode {

    int index;
    boolean is_done;
    int CoorX;
    public int CoorY;
    public Bitmap[] explodes;
////////////////////////////////////////////////////////////////////////////////////////////////////
    public Explode(int _CoorX, int _CoorY, CharacterJumpActivity context){
        index = 0;
        is_done = false;
        this.CoorX = _CoorX;
        this.CoorY = _CoorY;
        initBitmap(context);
        new Thread(new ExplodeThread()).start();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void initBitmap(CharacterJumpActivity context) {
        explodes = new Bitmap[6];
        explodes[0] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.explode1)).getBitmap();
        explodes[1] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.explode2)).getBitmap();
        explodes[2] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.explode3)).getBitmap();
        explodes[3] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.explode4)).getBitmap();
        explodes[4] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.explode5)).getBitmap();
        explodes[5] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.explode6)).getBitmap();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void drawSelf(Canvas canvas){
        if(index <= 5)
            canvas.drawBitmap(explodes[index], CoorX, CoorY, null);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isDone(){
        return is_done;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private class ExplodeThread implements Runnable{
        @Override
        public void run() {
            while(!is_done){
                try {
                    Thread.sleep(90);
                } catch (Exception e) { }
                if(!is_done){
                    index ++;
                    if(index > 5)
                        is_done = true;
                }
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
