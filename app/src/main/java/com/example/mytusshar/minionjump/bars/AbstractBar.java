package com.example.mytusshar.minionjump.bars;

import android.graphics.Canvas;
import com.example.mytusshar.minionjump.Items.AbstractItem;

/**
 * Created by mytusshar on 8/17/2016.
 */
public abstract class AbstractBar {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SHIFT  = 1;
    public int TLCoorX;
    public float TLCoorY;
    public int type;
    public AbstractItem item;
    public boolean is_item_eaten;
    public abstract void clear();
    public abstract void drawSelf(Canvas canvas);
    public abstract boolean isBeingStep(float CoorX, float CoorY);
}