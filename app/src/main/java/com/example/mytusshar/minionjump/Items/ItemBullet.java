package com.example.mytusshar.minionjump.Items;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.R;
import com.example.mytusshar.minionjump.gamecharacter.AbstractGameCharacter;

/**
 * Created by mytusshar on 8/17/2016.
 */
public class ItemBullet extends AbstractItem {

    Bitmap item_bullet;

    public ItemBullet(CharacterJumpActivity context){
        this.type = TYPE_BULLET;
        item_bullet = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.bullet_item)).getBitmap();
    }

    @Override
    public void drawSelf(Canvas canvas, float CoorX, float CoorY) {
        canvas.drawBitmap(item_bullet, CoorX, CoorY, null);

    }

    @Override
    public void modifyGameCharacter(AbstractGameCharacter gameCharacter) {
        gameCharacter.bullet_times += 20;
    }

}
