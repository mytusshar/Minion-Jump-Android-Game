package com.example.mytusshar.minionjump.bulletandexplode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.R;


/**
 * Created by mytusshar on 8/17/2016.
 */
public class Bullet {
    private static final int INITIAL_VERTICAL_SPEED = (int)(-20* CharacterJumpActivity.height_factor);
    public Bitmap bullet;

    int CoorX;
    int CoorY;
    int vertical_speed;
////////////////////////////////////////////////////////////////////////////////////////////////////
    public Bullet(int _CoorX, int _CoorY, int _horizontal_speed, CharacterJumpActivity context){
        this.CoorX = _CoorX;
        this.CoorY = _CoorY;
        this.vertical_speed = INITIAL_VERTICAL_SPEED;
        bullet = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.bullet)).getBitmap();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    //moves the bullet vertically
    public void move(){
        CoorY += vertical_speed;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    // draws bullet on canvas
    public void drawSelf(Canvas canvas){
        canvas.drawBitmap(bullet, CoorX - 5* CharacterJumpActivity.height_factor, CoorY - 5* CharacterJumpActivity.height_factor, null);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
