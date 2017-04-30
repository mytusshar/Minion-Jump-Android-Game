package com.example.mytusshar.minionjump.otherviews;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import com.example.mytusshar.minionjump.Constants;
import com.example.mytusshar.minionjump.CharacterJumpActivity;

/**
 * Created by mytusshar on 8/15/2016.
 */
public class FailView extends View {

    CharacterJumpActivity characterJumpActivity;
    boolean is_running = true;
    private Animation alphAnimation;
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void startEntryAnim(){
        alphAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphAnimation.setDuration(Constants.ANIMATION_TIME);
        startAnimation(alphAnimation);
    }

    public void startExitAnim(){
        alphAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphAnimation.setDuration(Constants.ANIMATION_TIME);
        startAnimation(alphAnimation);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    public FailView(CharacterJumpActivity context) {
        super(context);
        characterJumpActivity = context;
        CharacterJumpActivity.is_game_running = false;
        new Thread(new FailThread()).start();
        startEntryAnim();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FF56FD91"));
        drawButton(canvas);
        super.onDraw(canvas);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawButton(Canvas canvas) {

        float X = CharacterJumpActivity.screen_width/2;
        float Y = CharacterJumpActivity.screen_height/2;
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(30* CharacterJumpActivity.scaled_density);
        paint2.setColor(Color.parseColor("#173403"));
        canvas.drawText("RESTART", X-55* CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height/10+50* CharacterJumpActivity.scaled_density, paint2);
        canvas.drawText("MENU", X-35* CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height/10+150* CharacterJumpActivity.scaled_density, paint2);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            float X = CharacterJumpActivity.screen_width/2;
            float Y = CharacterJumpActivity.screen_height/2;

            if(x>X-50*CharacterJumpActivity.scaled_density && x<X+85*CharacterJumpActivity.scaled_density
                    && y>60*CharacterJumpActivity.scaled_density && y<120*CharacterJumpActivity.scaled_density){
                startExitAnim();
                characterJumpActivity.handler.sendEmptyMessage(CharacterJumpActivity.GAME_START);
            }
            if(x > CharacterJumpActivity.screen_width/2-40* CharacterJumpActivity.scaled_density && x < CharacterJumpActivity.screen_width/2+100* CharacterJumpActivity.scaled_density
                    && y > CharacterJumpActivity.screen_height/10+130* CharacterJumpActivity.scaled_density && y<CharacterJumpActivity.screen_height/10+190* CharacterJumpActivity.scaled_density){
                startExitAnim();
                characterJumpActivity.handler.sendEmptyMessage(CharacterJumpActivity.WELCOME);
                is_running = false;
            }
        }
        return super.onTouchEvent(event);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private class FailThread implements Runnable{
        @Override
        public void run() {
            while(is_running){
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                postInvalidate();
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////    
}
