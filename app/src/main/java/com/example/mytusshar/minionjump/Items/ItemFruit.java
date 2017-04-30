package com.example.mytusshar.minionjump.Items;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.ObjectsManager;
import com.example.mytusshar.minionjump.R;
import com.example.mytusshar.minionjump.gamecharacter.AbstractGameCharacter;

/**
 * Created by mytusshar on 8/17/2016.
 */
public class ItemFruit extends AbstractItem {

    private static final int BANANA      = 0;
    public Bitmap fruits;
////////////////////////////////////////////////////////////////////////////////////////////////////
    public ItemFruit(CharacterJumpActivity context){
        this.type = TYPE_FRUIT;
        initBitmap(context);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void initBitmap(CharacterJumpActivity context) {
        fruits = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.fruit_banana)).getBitmap();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void drawSelf(Canvas canvas, float CoorX, float CoorY) {
        canvas.drawBitmap(fruits, CoorX, CoorY, null);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void modifyGameCharacter(AbstractGameCharacter gameCharacter) {
        getAdd();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getAdd() {

        if(this.type == BANANA)
        {
            ObjectsManager.score += 20;
        }


    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
