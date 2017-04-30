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
import com.example.mytusshar.minionjump.gamecharacter.GameCharacter;

/**
 * Created by mytusshar on 8/15/2016.
 */
public class AboutView extends View {

    CharacterJumpActivity characterJumpActivity;
    boolean is_click = false;
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
    public AboutView(CharacterJumpActivity context) {
        super(context);
        characterJumpActivity = context;
        new Thread(new AboutThread()).start();
        startEntryAnim();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#25c786"));
        drawTitle(canvas);
        drawButton(canvas);
        drawAbout(canvas);
        super.onDraw(canvas);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawAbout(Canvas canvas) {
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(20 * CharacterJumpActivity.scaled_density);
        paint2.setColor(Color.parseColor("#FFFDEF56"));
        int Y = (int) (CharacterJumpActivity.screen_height/8 + 100*CharacterJumpActivity.scaled_density);
        int X = (int) (CharacterJumpActivity.screen_width /2 - 60*CharacterJumpActivity.scaled_density);
        canvas.drawText("MinioN  JumP", X, Y, paint2);
        canvas.drawText("Version 1.1", X+7*CharacterJumpActivity.scaled_density, Y+30*CharacterJumpActivity.scaled_density, paint2);
        canvas.drawText("Publisher", X+25*CharacterJumpActivity.scaled_density, Y+80*CharacterJumpActivity.scaled_density, paint2);
        canvas.drawText("and" , X+50*CharacterJumpActivity.scaled_density, Y+110*CharacterJumpActivity.scaled_density, paint2);
        canvas.drawText("Game Designer", X+5*CharacterJumpActivity.scaled_density, Y+140*CharacterJumpActivity.scaled_density, paint2);
        canvas.drawText("TUSHAR BOCHARE", X-13*CharacterJumpActivity.scaled_density, Y+170*CharacterJumpActivity.scaled_density, paint2);
        canvas.drawText("email: mytusshar@gmail.com", X-71*CharacterJumpActivity.scaled_density, Y+200*CharacterJumpActivity.scaled_density, paint2);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawButton(Canvas canvas) {
        int y = (int) (CharacterJumpActivity.screen_height-50*CharacterJumpActivity.scaled_density);
        int x = (int) (CharacterJumpActivity.screen_width/4*3);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(25*CharacterJumpActivity.scaled_density);
        paint2.setColor(Color.parseColor("#173403"));
        canvas.drawText("BACK", CharacterJumpActivity.screen_width -75*CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height-50* CharacterJumpActivity.scaled_density, paint2);
        paint2.setColor(Color.parseColor("#000000"));
        paint2.setAlpha(60);
        canvas.drawRect(x-20*CharacterJumpActivity.scaled_density, y-40*CharacterJumpActivity.scaled_density,
                x+70*CharacterJumpActivity.scaled_density, y+20*CharacterJumpActivity.scaled_density, paint2);
        paint2.setColor(Color.parseColor("#a0f60b"));
        paint2.setAlpha(100);
        if(is_click)
            canvas.drawRect(x-20*CharacterJumpActivity.scaled_density, y-40*CharacterJumpActivity.scaled_density,
                x+70*CharacterJumpActivity.scaled_density, y+20*CharacterJumpActivity.scaled_density, paint2);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawTitle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(40*CharacterJumpActivity.scaled_density);
        paint.setColor(Color.parseColor("#FF2D6C53"));
        int y = (int) (CharacterJumpActivity.screen_height/5);
        canvas.drawText("ABOUT", CharacterJumpActivity.screen_width/2 - 60* CharacterJumpActivity.scaled_density, y, paint);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) (CharacterJumpActivity.screen_height-50* CharacterJumpActivity.scaled_density);
        int x = (int) (CharacterJumpActivity.screen_width/4*3);
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float preX = event.getX();
            float preY = event.getY();
            if(preX > x - 20 * CharacterJumpActivity.scaled_density && preX < x + 70* CharacterJumpActivity.scaled_density && preY > y - 40* CharacterJumpActivity.scaled_density&& preY < y + 20* CharacterJumpActivity.scaled_density)
                is_click = true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(is_click){
                startExitAnim();
                characterJumpActivity.handler.sendEmptyMessage(CharacterJumpActivity.WELCOME);
            }
        }
        return true;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    private class AboutThread implements Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                postInvalidate();
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
