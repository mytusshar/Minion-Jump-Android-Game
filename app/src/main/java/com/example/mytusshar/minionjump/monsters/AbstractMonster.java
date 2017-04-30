package com.example.mytusshar.minionjump.monsters;

import android.graphics.Canvas;

import com.example.mytusshar.minionjump.gamecharacter.AbstractGameCharacter;

public abstract class AbstractMonster {

    public static final int DIRECTION_LEFT  = 1;
    public static final int DIRECTION_RIGHT = 2;

    protected int CoorX;
    public float CoorY;
    int direction;
    int bitmap_index;
    int horizontal_speed;
    protected boolean is_destroy;
    public boolean is_running;

    public abstract void beingAttacked();
    public abstract boolean isDestroy();
    public abstract int getX();
    public abstract void drawSelf(Canvas canvas);
    public abstract void checkDistance(AbstractGameCharacter gameCharacter);
}
