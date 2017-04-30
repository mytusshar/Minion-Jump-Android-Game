package com.example.mytusshar.minionjump.bars;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.Items.AbstractItem;
import com.example.mytusshar.minionjump.R;

/**
 * Created by mytusshar on 8/17/2016.
 */
public class NormalBar extends AbstractBar {

    private Bitmap normal_bar;
///////////////////////////////////////////////////////////////////////////////////////////////////////
    public NormalBar(int CoorX, float CoorY, AbstractItem item, CharacterJumpActivity context){
        this.TLCoorX = CoorX;
        this.TLCoorY = CoorY;
        this.type = TYPE_NORMAL;
        this.item = item;
        this.is_item_eaten = false;
        normal_bar = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.bar)).getBitmap();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
    //this method draws fruit items on normal Bar
    @Override
    public void drawSelf(Canvas canvas) {
        if(this.item != null && !this.is_item_eaten)
            this.item.drawSelf(canvas, TLCoorX + 15 * CharacterJumpActivity.height_factor, TLCoorY - 20 * CharacterJumpActivity.height_factor);
        canvas.drawBitmap(normal_bar, TLCoorX, TLCoorY, null);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean isBeingStep(float CoorX, float CoorY){
        //resolving different screen size issue
        if(CharacterJumpActivity.density <=2.3)
        {
            CoorY+=20;
        }
        else if(CharacterJumpActivity.density >2.3 && CharacterJumpActivity.density <=3.3)
        {
            CoorY+=50;
        }
        else
        {
            CoorY+=90;
        }
        ///////////////////////////////
        if(CoorX >= TLCoorX - 35* CharacterJumpActivity.width_factor && CoorX <= TLCoorX + 45 * CharacterJumpActivity.width_factor && CoorY +45 * CharacterJumpActivity.height_factor <= TLCoorY && TLCoorY - CoorY <= 45 * CharacterJumpActivity.height_factor ){
            return true;
        }
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void clear() {}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
}
