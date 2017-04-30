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
public class ExitView extends View {
    
    boolean[] is_click = new boolean[2];
    CharacterJumpActivity context;
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
    public ExitView(CharacterJumpActivity context) {
        super(context);
        this.context = context;
        for(int i=0; i<2; i++)
            is_click[i] = false;
        new Thread(new ExitThread()).start();
        startEntryAnim();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FF56FD91"));
        drawTitle(canvas);
        drawButton(canvas);
        super.onDraw(canvas);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawButton(Canvas canvas) {
        int X = (int) (CharacterJumpActivity.screen_width/2 -70*CharacterJumpActivity.scaled_density);
        int Y = (int) (CharacterJumpActivity.screen_height/2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#a0f60b"));
        paint.setAlpha(60);
        if(is_click[0]){
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        if(is_click[1]){
            context.handler.sendEmptyMessage(CharacterJumpActivity.WELCOME);
        }
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(30*CharacterJumpActivity.scaled_density);
        paint2.setColor(Color.parseColor("#173403"));
        canvas.drawText("EXIT", X-20*CharacterJumpActivity.scaled_density, Y, paint2);
        canvas.drawText("MENU", X+80*CharacterJumpActivity.scaled_density, Y, paint2);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawTitle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(50* CharacterJumpActivity.scaled_density);
        paint.setColor(Color.parseColor("#f97f09"));
        int y = (int) (CharacterJumpActivity.screen_height/12);
        canvas.drawText("EXIT?", CharacterJumpActivity.screen_width/2 - 50*CharacterJumpActivity.scaled_density, y+100* CharacterJumpActivity.scaled_density, paint);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int X = (int) (CharacterJumpActivity.screen_width / 2 - 70* CharacterJumpActivity.scaled_density);
            int Y = (int) (CharacterJumpActivity.screen_height / 2);
            int x = (int) event.getX();
            int y = (int) event.getY();
            if(x > X-20 * CharacterJumpActivity.scaled_density&& x < X + 50* CharacterJumpActivity.scaled_density&& y > Y - 40* CharacterJumpActivity.scaled_density && y < Y + 40* CharacterJumpActivity.scaled_density)
                is_click[0] = true;
            if(x > X+80 * CharacterJumpActivity.scaled_density&& x < X + 150* CharacterJumpActivity.scaled_density&& y > Y - 40* CharacterJumpActivity.scaled_density&& y < Y + 40* CharacterJumpActivity.scaled_density)
                is_click[1] = true;
        }
        return super.onTouchEvent(event);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private class ExitThread implements Runnable{
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(50);
                } catch (Exception e) { }
                postInvalidate();
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////    
}
