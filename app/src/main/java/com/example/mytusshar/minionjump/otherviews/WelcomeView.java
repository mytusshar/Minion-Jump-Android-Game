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
public class WelcomeView extends View {

    private boolean[] is_click;
    CharacterJumpActivity characterJumpActivity;

    private boolean is_view_running = true;
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
////////////////////////////////////////////////////////////////////////////////////////////////////
    public WelcomeView(CharacterJumpActivity context) {
        super(context);
        this.characterJumpActivity = context;
        is_click = new boolean[5];
        for(int i=0; i<5; i++)
            is_click[i] = false;
        new Thread(new WelcomeThread()).start();
        startEntryAnim();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FF3FD73C"));
        drawTitle(canvas);
        drawButton(canvas);
        super.onDraw(canvas);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawButton(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int X = (int) (CharacterJumpActivity.screen_width/2);
        int Y = (int) (CharacterJumpActivity.screen_height/3) - (int)(5*CharacterJumpActivity.scaled_density);
        paint.setColor(Color.parseColor("#56f7fd"));
        paint.setAlpha(60);
        //these if blocks draws green rectangle around the options in
        if(is_click[0]){
            canvas.drawRect(X - 60*CharacterJumpActivity.scaled_density, Y - 40 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 20 * CharacterJumpActivity.scaled_density, paint);
        }
        if(is_click[1]){
            canvas.drawRect(X - 60 * CharacterJumpActivity.scaled_density, Y + 30 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 90 * CharacterJumpActivity.scaled_density, paint);
        }
        if(is_click[2]){
            canvas.drawRect(X - 60 * CharacterJumpActivity.scaled_density, Y + 100 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 160 * CharacterJumpActivity.scaled_density, paint);
        }
        if(is_click[3]){
            canvas.drawRect(X - 60 * CharacterJumpActivity.scaled_density, Y + 170 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 230 * CharacterJumpActivity.scaled_density, paint);
        }
        if(is_click[4]){
            canvas.drawRect(X - 60 * CharacterJumpActivity.scaled_density, Y + 240 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 300 * CharacterJumpActivity.scaled_density, paint);
        }

        paint.setColor(Color.parseColor("#FFFFCC00"));
        paint.setAlpha(255);
        // drawing options on canvas
        canvas.drawRect(X - 60 * CharacterJumpActivity.scaled_density, Y - 40 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 20 * CharacterJumpActivity.scaled_density, paint);
        canvas.drawRect(X - 60 * CharacterJumpActivity.scaled_density, Y + 30 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 90 * CharacterJumpActivity.scaled_density, paint);
        canvas.drawRect(X - 60 * CharacterJumpActivity.scaled_density, Y + 100 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 160 * CharacterJumpActivity.scaled_density, paint);
        canvas.drawRect(X - 60 * CharacterJumpActivity.scaled_density, Y + 170 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 230 * CharacterJumpActivity.scaled_density, paint);
        canvas.drawRect(X - 60 * CharacterJumpActivity.scaled_density, Y + 240 * CharacterJumpActivity.scaled_density, X + 75 * CharacterJumpActivity.scaled_density, Y + 300 * CharacterJumpActivity.scaled_density, paint);

        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(30* CharacterJumpActivity.scaled_density);
        paint2.setColor(Color.parseColor("#173403"));
        canvas.drawText("START", X - 38* CharacterJumpActivity.scaled_density, Y, paint2);
        canvas.drawText("SCORE", X - 40* CharacterJumpActivity.scaled_density, Y+70* CharacterJumpActivity.scaled_density, paint2);
        canvas.drawText("OPTIONS", X - 55* CharacterJumpActivity.scaled_density, Y+140* CharacterJumpActivity.scaled_density, paint2);
        canvas.drawText("ABOUT", X - 40* CharacterJumpActivity.scaled_density, Y+210* CharacterJumpActivity.scaled_density, paint2);
        canvas.drawText("EXIT", X - 20* CharacterJumpActivity.scaled_density, Y+280* CharacterJumpActivity.scaled_density, paint2);
       }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawTitle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(40* CharacterJumpActivity.scaled_density);
        paint.setFakeBoldText(true);
        paint.setColor(Color.parseColor("#FFFFCC00"));
        int y = (int) (CharacterJumpActivity.screen_height / 9);

        canvas.drawText("MinioN JumP", 25*CharacterJumpActivity.scaled_density, y, paint);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int X = (int) (CharacterJumpActivity.screen_width/2);
            int Y = (int) (CharacterJumpActivity.screen_height/3);
            int touch_x = (int) event.getX();
            int touch_Y= (int) event.getY();

            if(touch_x > X - 60* CharacterJumpActivity.scaled_density && touch_x < X + 60 * CharacterJumpActivity.scaled_density&& touch_Y > Y - 40 * CharacterJumpActivity.scaled_density&& touch_Y < Y + 20* CharacterJumpActivity.scaled_density)
                is_click[0] = true;
            else if(touch_x > X - 60* CharacterJumpActivity.scaled_density && touch_x < X + 60 * CharacterJumpActivity.scaled_density&& touch_Y > Y + 30* CharacterJumpActivity.scaled_density && touch_Y < Y + 90* CharacterJumpActivity.scaled_density)
                is_click[1] = true;
            else if(touch_x > X - 60* CharacterJumpActivity.scaled_density && touch_x < X + 60 * CharacterJumpActivity.scaled_density&& touch_Y > Y + 100* CharacterJumpActivity.scaled_density && touch_Y < Y + 160* CharacterJumpActivity.scaled_density)
                is_click[2] = true;
            else if(touch_x > X - 60* CharacterJumpActivity.scaled_density && touch_x < X + 60 * CharacterJumpActivity.scaled_density&& touch_Y > Y + 170* CharacterJumpActivity.scaled_density && touch_Y < Y + 230* CharacterJumpActivity.scaled_density)
                is_click[3] = true;
            else if(touch_x > X - 60 * CharacterJumpActivity.scaled_density&& touch_x < X + 60* CharacterJumpActivity.scaled_density && touch_Y > Y + 240* CharacterJumpActivity.scaled_density && touch_Y < Y + 300* CharacterJumpActivity.scaled_density)
                is_click[4] = true;
            else{

            }
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            if(is_click[0]){
                startExitAnim();
                is_view_running = false;
                characterJumpActivity.handler.sendEmptyMessage(CharacterJumpActivity.GAME_START);
            }
            if(is_click[1]){
                startExitAnim();
                is_view_running = false;
                characterJumpActivity.handler.sendEmptyMessage(CharacterJumpActivity.SCORE);
            }
            if(is_click[2]){
                startExitAnim();
                is_view_running = false;
                characterJumpActivity.handler.sendEmptyMessage(CharacterJumpActivity.OPTION);
            }
            if(is_click[3]){
                startExitAnim();
                is_view_running = false;
                characterJumpActivity.handler.sendEmptyMessage(CharacterJumpActivity.ABOUT);
            }
            if(is_click[4]){
                startExitAnim();
                is_view_running = false;
                characterJumpActivity.handler.sendEmptyMessage(CharacterJumpActivity.EXIT);
            }
        }
        return true;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    private class WelcomeThread implements Runnable{
        @Override
        public void run() {
            while(is_view_running){
                try {
                    Thread.sleep(30);
                } catch (Exception e) { }
                postInvalidate();
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
