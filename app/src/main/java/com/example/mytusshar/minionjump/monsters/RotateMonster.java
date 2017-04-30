package com.example.mytusshar.minionjump.monsters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.GameView;
import com.example.mytusshar.minionjump.R;
import com.example.mytusshar.minionjump.gamecharacter.AbstractGameCharacter;

import java.util.Random;

/**
 * Created by mytusshar on 8/17/2016.
 */
public class RotateMonster extends AbstractMonster {

    private Bitmap[] rotate;
    private Bitmap[] rotate_attacked;
    private boolean is_stop = false;
    private int stop_time = 0;
    private Paint paint;
    private int attacked_times = 0;
    private boolean is_attacked = false;
////////////////////////////////////////////////////////////////////////////////////////////////////
    public RotateMonster(float f, CharacterJumpActivity context){
        this.CoorX = new Random().nextInt(271) + 25;
        this.direction = new Random().nextInt(2) + 1;
        bitmap_index = 0;
        this.CoorY = f;
        this.horizontal_speed = (int)(direction == DIRECTION_LEFT ? -10*CharacterJumpActivity.height_factor : 10*CharacterJumpActivity.height_factor);
        initBitmap(context);
        is_running = true;
        paint = new Paint();
        paint.setAntiAlias(true);
        new Thread(new MoveThread()).start();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void initBitmap(CharacterJumpActivity context) {
        rotate = new Bitmap[3];
        rotate[0] = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.rotate1)).getBitmap();
        rotate[1] = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.rotate2)).getBitmap();
        rotate[2] = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.rotate3)).getBitmap();

        rotate_attacked = new Bitmap[3];
        rotate_attacked[0] = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.rotate1_attacked)).getBitmap();
        rotate_attacked[1] = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.rotate2_attacked)).getBitmap();
        rotate_attacked[2] = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.rotate3_attacked)).getBitmap();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int getX() {
        return CoorX;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void drawSelf(Canvas canvas) {
        if(!is_stop){
            if(!is_attacked)
                canvas.drawBitmap(rotate[bitmap_index], CoorX-25* CharacterJumpActivity.height_factor, CoorY-25* CharacterJumpActivity.height_factor, paint);
            else{
                is_attacked = false;
                canvas.drawBitmap(rotate_attacked[bitmap_index], CoorX-25* CharacterJumpActivity.height_factor, CoorY-25* CharacterJumpActivity.height_factor, paint);
            }
        }
        else{
            if(!is_attacked)
                canvas.drawBitmap(rotate[0], CoorX-25* CharacterJumpActivity.height_factor, CoorY-25* CharacterJumpActivity.height_factor, paint);
            else{
                is_attacked = false;
                canvas.drawBitmap(rotate_attacked[0], CoorX-25* CharacterJumpActivity.height_factor, CoorY-25* CharacterJumpActivity.height_factor, paint);
            }
        }
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
        float temp_x = gameCharacter.LTCoorX + 22* CharacterJumpActivity.height_factor;
        float temp_y = gameCharacter.LTCoorY + 22* CharacterJumpActivity.height_factor;
        if(getDistance(temp_x, temp_y, CoorX, CoorY) <= 40* CharacterJumpActivity.height_factor){
            gameCharacter.minusLife();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void move(){
        this.CoorX += this.horizontal_speed;
        if(this.CoorX <= 25* CharacterJumpActivity.height_factor){
            this.direction = DIRECTION_RIGHT;
            this.horizontal_speed *= -1;
            is_stop = true;
        }
        if(this.CoorX >= CharacterJumpActivity.screen_width - 25* CharacterJumpActivity.height_factor){
            this.direction = DIRECTION_LEFT;
            this.horizontal_speed *= -1;
            is_stop = true;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private class MoveThread implements Runnable{
        @Override
        public void run() {
            while(is_running){
                try {
                    Thread.sleep(50);
                } catch (Exception e) { }
                finally{
                    if(!GameView.ispause){
                        if(!is_stop){
                            move();
                            bitmap_index ++;
                            if(bitmap_index > 2)
                                bitmap_index = 0;
                        }
                        else{
                            stop_time ++;
                            if(stop_time > 20){
                                is_stop = false;
                                stop_time = 0;
                            }
                        }
                    }
                }
            }
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
        if(!is_attacked){
            is_attacked = true;
            attacked_times ++;
            if(attacked_times > 1)
                is_destroy = true;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
