package com.example.mytusshar.minionjump.monsters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.GameView;
import com.example.mytusshar.minionjump.R;
import com.example.mytusshar.minionjump.gamecharacter.AbstractGameCharacter;

import java.util.Random;

/**
 * Created by mytusshar on 8/17/2016.
 */
public class EatingHead extends AbstractMonster {

    public Bitmap[] eatinghead;
////////////////////////////////////////////////////////////////////////////////////////////////////
    public EatingHead(float f, CharacterJumpActivity context){
        this.CoorX = new Random().nextInt(271) + 25;
        this.direction = new Random().nextInt(2) + 1;
        bitmap_index = direction == DIRECTION_LEFT ? 0 : 2;
        this.CoorY = f;
        this.horizontal_speed = direction == DIRECTION_LEFT ? -10 : 10;
        intiBitmap(context);
        is_destroy = false;
        is_running = true;
        new Thread(new MoveThread()).start();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void intiBitmap(CharacterJumpActivity context) {
        eatinghead = new Bitmap[4];
        eatinghead[0] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.monster1_left_close)).getBitmap();
        eatinghead[1] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.monster1_left_open)).getBitmap();
        eatinghead[2] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.monster1_right_close)).getBitmap();
        eatinghead[3] =  ((BitmapDrawable)context.getResources().getDrawable(R.drawable.monster1_right_open)).getBitmap();

    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(eatinghead[bitmap_index], CoorX-25* CharacterJumpActivity.height_factor, CoorY-25* CharacterJumpActivity.height_factor, null);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void move(){
        this.CoorX += this.horizontal_speed;
        if(this.CoorX < 25* CharacterJumpActivity.height_factor){
            this.direction = DIRECTION_RIGHT;
            this.horizontal_speed *= -1;
        }
        if(this.CoorX > CharacterJumpActivity.screen_width - 25* CharacterJumpActivity.height_factor){
            this.direction = DIRECTION_LEFT;
            this.horizontal_speed *= -1;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private class MoveThread implements Runnable{
        @Override
        public void run() {
            while(is_running){
                try {
                    Thread.sleep(150);
                } catch (Exception e) {    }
                finally{
                    if(!GameView.ispause){
                        if(direction == DIRECTION_LEFT){
                            if(bitmap_index == 0)
                                bitmap_index = 1;
                            else
                                bitmap_index = 0;
                        }
                        else{
                            if(bitmap_index == 2)
                                bitmap_index = 3;
                            else
                                bitmap_index = 2;
                        }
                        move();
                    }
                }
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int getX() {
        return CoorX;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private int getDistance(float temp_x, float temp_y, int x, float y) {
        double x_length = temp_x - x;
        double y_length = temp_y - y;
        return (int) Math.sqrt(x_length*x_length + y_length*y_length);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void checkDistance(AbstractGameCharacter gameCharacter) {
        float temp_x = gameCharacter.LTCoorX + 22;
        float temp_y = gameCharacter.LTCoorY + 22;
        if(getDistance(temp_x, temp_y, CoorX, CoorY) <= 40* CharacterJumpActivity.height_factor){
            gameCharacter.minusLife();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean isDestroy() {
        return is_destroy;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void beingAttacked() {
        is_destroy = true;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
