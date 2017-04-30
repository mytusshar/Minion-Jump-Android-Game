package com.example.mytusshar.minionjump;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import com.example.mytusshar.minionjump.otherviews.AboutView;
import com.example.mytusshar.minionjump.otherviews.ExitView;
import com.example.mytusshar.minionjump.otherviews.FailView;
import com.example.mytusshar.minionjump.otherviews.OptionView;
import com.example.mytusshar.minionjump.otherviews.ScoreView;
import com.example.mytusshar.minionjump.otherviews.WelcomeView;

/**
 * Created by mytusshar on 8/15/2016.
 */
public class CharacterJumpActivity extends Activity {

    private GameView game_view;
    private WelcomeView welcome_view;
    private ExitView exit_view;
    private ScoreView scoreView;
    private AboutView about_view;
    private FailView fail_view;
    private OptionView option_view;
    private SensorManager sensor_manager;
    private MySensorEventListener sensor_event_listener;

    public static float aspect_ratio;
    public static float density;
    public static int density_dpi;
    public static float scaled_density;
    public static float xdpi;
    public static float ydpi;

    public static float screen_width;
    public static float screen_height;
    public static float width_factor;
    public static float height_factor;
    public static boolean is_game_running = false;
    int previous_speed = 0;
    View current_view;

    public static final int GAME_OVER  = 0;
    public static final int GAME_START = 1;
    public static final int SCORE      = 2;
    public static final int ABOUT      = 3;
    public static final int EXIT       = 4;
    public static final int WELCOME    = 5;
    public static final int OPTION     = 6;
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getting device dimensions
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screen_width = dm.widthPixels;
        screen_height = dm.heightPixels;
        width_factor = screen_width/320;
        height_factor = screen_height/480;
        // android device properties
        aspect_ratio = screen_width/screen_height;
        density = dm.density;
        density_dpi = dm.densityDpi;
        scaled_density = height_factor;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        //////////////////////////
        if(screen_height >= 800){
            height_factor = (float) 1.5;
        }
        // initiating sounds
        com.example.mytusshar.minionjump.resource.SoundPlayer.initSound(this);
        //initiating welcome-view
        initViews();
        setContentView(welcome_view);
        //object of using SensorManager for using sensor activities
        sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    // message handler which handles msg for EXIT or PAUSE the game
    public Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what == GAME_OVER){
                current_view = null;
                initFailView();
            }
            if(msg.what == GAME_START){
                is_game_running = true;
                welcome_view = null;
                initGameView();
            }
            if(msg.what == SCORE){
                current_view = null;
                initScoreView();
            }
            if(msg.what == EXIT){
                current_view = null;
                initExitView();
            }
            if(msg.what == WELCOME){
                is_game_running = false;
                current_view = null;
                initWelcomeView();
            }
            if(msg.what == ABOUT){
                current_view = null;
                initAboutView();

            }
            if(msg.what == OPTION){
                current_view = null;
                initOptionView();
            }
        }
    };
////////////////////////////////////////////////////////////////////////////////////////////////////
    //initiating views
    private  void initViews(){
        welcome_view = new WelcomeView(this);
    }

    private void initOptionView() {
        option_view = new OptionView(CharacterJumpActivity.this);
        current_view = option_view;
        setContentView(option_view);
        welcome_view = null;
    }

    private void initFailView() {
        fail_view = new FailView(CharacterJumpActivity.this);
        current_view = fail_view;
        setContentView(fail_view);
        game_view = null;
    }

    private void initAboutView() {
        about_view = null;
        about_view = new AboutView(CharacterJumpActivity.this);
        current_view = about_view;
        setContentView(about_view);
        welcome_view = null;
    }

    private void initScoreView() {
        scoreView = new ScoreView(CharacterJumpActivity.this);
        current_view = scoreView;
        setContentView(scoreView);
        welcome_view = null;
    }

    private void initExitView() {
        exit_view = new ExitView(CharacterJumpActivity.this);
        current_view = exit_view;
        setContentView(exit_view);
        welcome_view = null;
    }

    private void initGameView() {
        game_view = new GameView(CharacterJumpActivity.this);
        current_view = game_view;
        setContentView(game_view);
    }

    private void initWelcomeView(){
        welcome_view = new WelcomeView(CharacterJumpActivity.this);
        current_view = welcome_view;
        setContentView(welcome_view);
        fail_view = null;
        exit_view = null;
        scoreView = null;
        option_view = null;
        about_view = null;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    // if user presses home button or back button provided on device
    //in the current view this block handles the operations
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(current_view == game_view){
            if( (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) || (keyCode == KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0)){
                GameView.ispause = true;
                return true;
            }
        }
        else if(current_view == welcome_view){
            if( (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) || (keyCode == KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0)){
                handler.sendEmptyMessage(EXIT);
                return true;
            }
        }
        else{
            if( (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) || (keyCode == KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0)){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onResume() {
        Sensor sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor_event_listener = new MySensorEventListener();
        sensor_manager.registerListener(sensor_event_listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensor_manager.unregisterListener(sensor_event_listener);
        super.onPause();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    //changes the speed of android in game_view
    private final class MySensorEventListener implements SensorEventListener {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

        @Override
        public void onSensorChanged(SensorEvent event) {
            try{
                if(current_view == game_view){
                    float X = event.values[SensorManager.DATA_X];
                    previous_speed += X;
                    if(X > 0.45 || X < -0.45){
                        int temp = X > 0 ? 4 : -4;
                        if(previous_speed > 7 || previous_speed < -7)
                            previous_speed = previous_speed > 0 ? 7 : -7;
                        game_view.logicManager.setGameCharacterHorizontalSpeed(previous_speed + temp);
                    }
                }
            }catch(Exception e){ }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
