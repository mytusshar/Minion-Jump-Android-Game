package com.example.mytusshar.minionjump.otherviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import com.example.mytusshar.minionjump.Constants;
import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.sqlite.DataBaseOperation;
import com.example.mytusshar.minionjump.sqlite.MyString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mytusshar on 8/15/2016.
 */
public class ScoreView extends View implements GestureDetector.OnGestureListener {


    List<MyString> scoreList = new ArrayList<MyString>();
    List<Coordinates> list = new ArrayList<Coordinates>();
    private DataBaseOperation dataBaseOperation;
    private boolean is_click = false;
    CharacterJumpActivity characterJumpActivity;
    Coordinates current_point = new Coordinates(0, 0);
    Coordinates previous_point = new Coordinates(0, 0);
    float preX;
    float preY;
    Animation alphAnimation;
 ////////////////////////////////////////////////////////////////////////////////////////////////////
    public ScoreView(CharacterJumpActivity context) {
        super(context);
        getScoreMap(context);
        characterJumpActivity = context;
        new Thread(new ScoreThread()).start();
        startEntryAnim();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void startEntryAnim(){
        alphAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphAnimation.setDuration(Constants.ANIMATION_TIME);
        startAnimation(alphAnimation);
    }

    public void startExitAnim(){
        alphAnimation = new AlphaAnimation(1.0f, 0.1f);
        alphAnimation.setDuration(Constants.ANIMATION_TIME);
        startAnimation(alphAnimation);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getScoreMap(Context context) {
        int X = (int) (CharacterJumpActivity.screen_width/4-60* CharacterJumpActivity.scaled_density);
        int Y = (int) (CharacterJumpActivity.screen_height/8+20* CharacterJumpActivity.scaled_density);
        dataBaseOperation = new DataBaseOperation(context);
        scoreList = dataBaseOperation.GetScoreList();
        for(int i=0; i<scoreList.size(); i++){
            list.add(new Coordinates(X, Y));
            Y += 60 * CharacterJumpActivity.scaled_density;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FFC17ED9"));
        drawTitle(canvas);
        drawButton(canvas);
        drawScore(canvas);
        super.onDraw(canvas);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawScore(Canvas canvas) {
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(20* CharacterJumpActivity.scaled_density);
        paint2.setColor(Color.parseColor("#ffffff"));
        int num = 0;
        for(int i=0; i<scoreList.size(); i++){
            if(list.get(num).Y >= CharacterJumpActivity.screen_height/8+20* CharacterJumpActivity.scaled_density && list.get(num).Y <= CharacterJumpActivity.screen_height-80* CharacterJumpActivity.scaled_density)
                canvas.drawText(scoreList.get(i).getLine_one(), list.get(num).X, list.get(num).Y, paint2);
            if(list.get(num).Y + 20  * CharacterJumpActivity.scaled_density  >= CharacterJumpActivity.screen_height/8+20 * CharacterJumpActivity.scaled_density && list.get(num).Y + 20  * CharacterJumpActivity.scaled_density <= CharacterJumpActivity.screen_height-80* CharacterJumpActivity.scaled_density)
                canvas.drawText(scoreList.get(i).getLine_two(), list.get(num).X + 20* CharacterJumpActivity.scaled_density, list.get(num).Y + 20 * CharacterJumpActivity.scaled_density, paint2);

            num++;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawButton(Canvas canvas) {
        int y = (int) (CharacterJumpActivity.screen_height-50* CharacterJumpActivity.scaled_density);
        int x = (int) (CharacterJumpActivity.screen_width/4*3);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(30* CharacterJumpActivity.scaled_density);
        paint2.setColor(Color.parseColor("#173403"));
        canvas.drawText("BACK", x-15*CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height-50*CharacterJumpActivity.scaled_density, paint2);
        paint2.setColor(Color.parseColor("#000000"));
        paint2.setAlpha(60);
        canvas.drawRect(x-30*CharacterJumpActivity.scaled_density, y-40*CharacterJumpActivity.scaled_density,
                x+70*CharacterJumpActivity.scaled_density, y+20*CharacterJumpActivity.scaled_density, paint2);
        if(is_click)
            canvas.drawRect(x-30*CharacterJumpActivity.scaled_density, y-40*CharacterJumpActivity.scaled_density,
                    x+70*CharacterJumpActivity.scaled_density, y+20*CharacterJumpActivity.scaled_density, paint2);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawTitle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(80);
        canvas.drawRect(0, 0, CharacterJumpActivity.screen_width, 65*CharacterJumpActivity.scaled_density, paint);
        paint.setTextSize(40*CharacterJumpActivity.scaled_density);
        paint.setColor(Color.parseColor("#f97f09"));
        paint.setAlpha(255);
        int y = (int)(CharacterJumpActivity.screen_height/10);
        canvas.drawText("SCORE", CharacterJumpActivity.screen_width/2 -65*CharacterJumpActivity.scaled_density, y, paint);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) (CharacterJumpActivity.screen_height-50* CharacterJumpActivity.scaled_density);
        int x = (int) (CharacterJumpActivity.screen_width/4*3);
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            preX = event.getX();
            preY = event.getY();
            if(preX > x - 20* CharacterJumpActivity.scaled_density && preX < x+70* CharacterJumpActivity.scaled_density && preY > y - 40* CharacterJumpActivity.scaled_density && preY < y + 20* CharacterJumpActivity.scaled_density)
                is_click = true;

            previous_point.Y = (int) event.getY();
            for(int i=0; i<list.size(); i++)
                list.get(i).length_y = list.get(i).Y - previous_point.Y;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            current_point.Y = (int) event.getY();
            if(list.size() > 0){
                if((list.get(0).Y < CharacterJumpActivity.screen_height/8+20* CharacterJumpActivity.scaled_density && list.get(list.size() - 1).Y <= CharacterJumpActivity.screen_height-80* CharacterJumpActivity.scaled_density)
                        || (list.get(0).Y <= CharacterJumpActivity.screen_height/8+20* CharacterJumpActivity.scaled_density && list.get(list.size() - 1).Y >= CharacterJumpActivity.screen_height-80* CharacterJumpActivity.scaled_density)
                        || (list.get(0).Y >= CharacterJumpActivity.screen_height/8+20* CharacterJumpActivity.scaled_density && list.get(list.size() - 1).Y >= CharacterJumpActivity.screen_height-80* CharacterJumpActivity.scaled_density)){
                    for(int i=0; i<list.size(); i++){
							list.get(i).Y = current_point.Y + list.get(i).length_y;
                    }
                }
            }
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
    private class ScoreThread implements Runnable{
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
    private class Coordinates{
        int X;
        int Y;
        int length_y;
        public Coordinates(int X, int Y){
            this.X = X;
            this.Y = Y;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(list.size() > 0){

        }
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////    
}

