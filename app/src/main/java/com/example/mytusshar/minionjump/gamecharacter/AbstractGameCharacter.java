package com.example.mytusshar.minionjump.gamecharacter;

import android.graphics.Canvas;

import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.ObjectsManager;

/**
 * Created by mytusshar on 9/11/2016.
 */

public abstract class AbstractGameCharacter {

    public static final float DEFAULT_VERTICAL_ACCELERATE       = 1;
    protected static final float INITIAL_COORX                  = CharacterJumpActivity.screen_width/2;
    protected static final float INITIAL_COORY                  = CharacterJumpActivity.screen_height/2;

    public static  float INITIAL_VERTICAL_SPEED            = -13;
    public static  float MAX_VERTICAL_SPEED                = 13;

    public static final int   STATE_GO_UP                       = 1;
    public static final int   STATE_GO_DOWN                     = 2;
    public static final int   INITIAL_LIFE_NUM                  = 3;

    public static final int   INITIAL_LUANCHER_BULLET_TIMES     = 50;
    public static final int   HANDS_UP                          = 1;
    public static final int   HANDS_DOWN                        = 0;

    public float LTCoorX;
    public float LTCoorY;

    public float accelerameter;
    public float horizontal_speed;
    public float vertical_speed;

    public int current_state;
    public int bullet_times;
    public int life_num;
    public boolean is_fall_down;
    public int bitmap_index;

    public abstract void drawSelf(Canvas canvas);
    public abstract void move();
    public abstract void minusLife();
    public abstract void checkGameCharacterCoor(ObjectsManager objectsManager);

}
