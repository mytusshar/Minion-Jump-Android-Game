package com.example.mytusshar.minionjump.bulletandexplode;

import android.graphics.Canvas;

import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.gamecharacter.AbstractGameCharacter;
import com.example.mytusshar.minionjump.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mytusshar on 8/17/2016.
 */
public class BulletSet {

    HashMap<String, Bullet> bulletMap = new HashMap<String, Bullet>();

////////////////////////////////////////////////////////////////////////////////////////////////////
    public BulletSet(AbstractGameCharacter gameCharacter, CharacterJumpActivity context){
        bulletMap.put(""+1, new Bullet((int)gameCharacter.LTCoorX+17, (int)gameCharacter.LTCoorY-10, 0, context));
        new Thread(new MoveThread()).start();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getY(){
        for(String key : bulletMap.keySet()){
            return bulletMap.get(key).CoorY;
        }
        return 0;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void drawBullets(Canvas canvas){
        for(String key : bulletMap.keySet()){
            bulletMap.get(key).drawSelf(canvas);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isTouchBullet(AbstractMonster monster){
        List<String> list = new ArrayList<String>();
        for(String key : bulletMap.keySet()){
            int temp_x = Math.abs(monster.getX() - bulletMap.get(key).CoorX);
            float temp_y = Math.abs(monster.CoorY - bulletMap.get(key).CoorY);
            double distance = Math.sqrt((double)(temp_x*temp_x) + (double)(temp_y*temp_y));
            if(distance <= 30* CharacterJumpActivity.height_factor){
                monster.beingAttacked();
                list.add(key);
            }
        }
        for(String key : list){
            bulletMap.remove(key);
        }
        return monster.isDestroy();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private class MoveThread implements Runnable{
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(30);
                } catch (Exception e) { }
                for(String key : bulletMap.keySet()){
                    bulletMap.get(key).move();
                }
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
