package com.example.mytusshar.minionjump.Items;

import android.graphics.Canvas;
import com.example.mytusshar.minionjump.gamecharacter.AbstractGameCharacter;

/**
 * Created by mytusshar on 8/17/2016.
 */

public abstract class AbstractItem {
    public static final int TYPE_FRUIT  = 1;
    public static final int TYPE_BULLET = 2;
    public int type;
    public abstract void drawSelf(Canvas canvas, float CoorX, float CoorY);
    public abstract void modifyGameCharacter(AbstractGameCharacter android);
}
