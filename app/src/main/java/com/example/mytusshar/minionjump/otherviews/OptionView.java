package com.example.mytusshar.minionjump.otherviews;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.mytusshar.minionjump.Constants;
import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.resource.SoundPlayer;

/**
 * Created by mytusshar on 8/15/2016.
 */
public class OptionView extends View {

    public static final String DIFFICULTY_TUSH    = "Tush";
    public static final String DIFFICULTY_NORMAL    = "Normal";
    public static final String DIFFICULTY_MASTER    = "Master";//Hard
    public static final String DIFFICULTY_BONE_ASH  = "Bone Ash";

    public static final String PREFS_DIFF   = "difficulty";
    public static final String PREFS_NAME   = "MyPrefsFile";//MyPrefferenceFile
    public static final String SOUND_STRING = "isplay_spund?";//is_play_sound

    CharacterJumpActivity characterJumpActivity;
    boolean isclick_back = false;
    boolean isplay_sound = true;
    SharedPreferences sound_setting;
    SharedPreferences difficulty_setting;
    String difficultyString;
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
    public OptionView(CharacterJumpActivity context) {
        super(context);
        characterJumpActivity = context;
        //getting difficulty settings
        difficulty_setting = context.getSharedPreferences(PREFS_DIFF, 0);
        difficultyString = difficulty_setting.getString(PREFS_DIFF, DIFFICULTY_TUSH);
        //getting sound setting
        sound_setting = context.getSharedPreferences(PREFS_NAME, 0);
        isplay_sound = sound_setting.getBoolean(SOUND_STRING, true);
        new Thread(new OptionThread()).start();
        startEntryAnim();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FFFFFC54"));
        drawTitle(canvas);
        drawSoundButton(canvas);
        drawBackButton(canvas);
        super.onDraw(canvas);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawSoundButton(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(25* CharacterJumpActivity.scaled_density);
        paint.setColor(Color.parseColor("#173403"));
        canvas.drawText("SOUND?", CharacterJumpActivity.screen_width/2 - 40* CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height/2 - 90* CharacterJumpActivity.scaled_density, paint);
        canvas.drawText("DIFFICULTY?", CharacterJumpActivity.screen_width/2 - 55* CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height/2 - 0* CharacterJumpActivity.scaled_density, paint);
        paint.setTextSize(20* CharacterJumpActivity.scaled_density);
        paint.setColor(Color.parseColor("#7f7f7e"));

        canvas.drawText(difficultyString, CharacterJumpActivity.screen_width/2 - 30* CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height/2 + 30* CharacterJumpActivity.scaled_density, paint);
        if(isplay_sound)
            canvas.drawText("ON", CharacterJumpActivity.screen_width/2-10* CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height/2-60* CharacterJumpActivity.scaled_density, paint);
        else
            canvas.drawText("OFF", CharacterJumpActivity.screen_width/2-10* CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height/2-60* CharacterJumpActivity.scaled_density, paint);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawBackButton(Canvas canvas) {
        int y = (int) (CharacterJumpActivity.screen_height-50*CharacterJumpActivity.scaled_density);
        int x = (int) (CharacterJumpActivity.screen_width/4*3);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(25*CharacterJumpActivity.scaled_density);
        paint2.setColor(Color.parseColor("#173403"));
        canvas.drawText("BACK", CharacterJumpActivity.screen_width-77*CharacterJumpActivity.scaled_density, CharacterJumpActivity.screen_height-50* CharacterJumpActivity.scaled_density, paint2);
        paint2.setColor(Color.parseColor("#000000"));
        paint2.setAlpha(60);
        canvas.drawRect(x-20*CharacterJumpActivity.scaled_density, y-40*CharacterJumpActivity.scaled_density, x+70*CharacterJumpActivity.scaled_density, y+20*CharacterJumpActivity.scaled_density, paint2);
        if(isclick_back)
            canvas.drawRect(x-20*CharacterJumpActivity.scaled_density, y-40*CharacterJumpActivity.scaled_density, x+70*CharacterJumpActivity.scaled_density, y+20*CharacterJumpActivity.scaled_density, paint2);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawTitle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(70);
        canvas.drawRect(0, 0, CharacterJumpActivity.screen_width, 60*CharacterJumpActivity.scaled_density, paint);
        paint.setTextSize(40*CharacterJumpActivity.scaled_density);
        paint.setColor(Color.parseColor("#FF0E830F"));
        paint.setAlpha(255);
        int y = (int) (CharacterJumpActivity.screen_height/10);
        canvas.drawText("OPTIONS", CharacterJumpActivity.screen_width/2 -85*CharacterJumpActivity.scaled_density, y, paint);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) (CharacterJumpActivity.screen_height-50* CharacterJumpActivity.scaled_density);
        int x = (int) (CharacterJumpActivity.screen_width/4*3);

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float preX = event.getX();
            float preY = event.getY();
            if(preX > x - 20 * CharacterJumpActivity.scaled_density && preX < x+70 * CharacterJumpActivity.scaled_density && preY > y - 20 * CharacterJumpActivity.scaled_density && preY < y + 40* CharacterJumpActivity.scaled_density)
                isclick_back = true;
            x = (int) (CharacterJumpActivity.screen_width/2);
            y = (int) (CharacterJumpActivity.screen_height/2);

            if(preX > x - 30 * CharacterJumpActivity.scaled_density && preX < x + 40 * CharacterJumpActivity.scaled_density && preY > y - 80 * CharacterJumpActivity.scaled_density && preY < y - 20 * CharacterJumpActivity.scaled_density){
                isplay_sound = isplay_sound == true ? false : true;
                SharedPreferences.Editor editor = sound_setting.edit();
                editor.putBoolean(SOUND_STRING, isplay_sound);
                editor.commit();
                SoundPlayer.isplay_sound = isplay_sound;
            }

            if(preX > x - 50 * CharacterJumpActivity.scaled_density && preX < x + 50 * CharacterJumpActivity.scaled_density && preY > y + 10 * CharacterJumpActivity.scaled_density && preY < y + 70 * CharacterJumpActivity.scaled_density){
                difficultyString = getNewDifficulty();
                SharedPreferences.Editor editor = difficulty_setting.edit();
                editor.putString(PREFS_DIFF, difficultyString);
                editor.commit();
            }
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            if(isclick_back){
                startExitAnim();
                characterJumpActivity.handler.sendEmptyMessage(CharacterJumpActivity.WELCOME);
            }
        }
        return true;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    // getting new difficulty
    private String getNewDifficulty() {
        if(difficultyString.equalsIgnoreCase(DIFFICULTY_TUSH))
            return DIFFICULTY_NORMAL;
        else if(difficultyString.equalsIgnoreCase(DIFFICULTY_NORMAL))
            return DIFFICULTY_MASTER;
        else if(difficultyString.equalsIgnoreCase(DIFFICULTY_MASTER))
            return DIFFICULTY_BONE_ASH;
        return DIFFICULTY_TUSH;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private class OptionThread implements Runnable{
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(50);
                } catch (Exception e) {  }
                postInvalidate();
            }
        }

    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
