package com.example.mytusshar.minionjump;

import android.graphics.Canvas;

import com.example.mytusshar.minionjump.gamecharacter.AbstractGameCharacter;
import com.example.mytusshar.minionjump.gamecharacter.GameCharacter;
import com.example.mytusshar.minionjump.bulletandexplode.BulletSet;
import com.example.mytusshar.minionjump.bulletandexplode.Explode;

import java.util.ArrayList;
import java.util.List;

public class LogicManager {

    private static final int SLEEP_TIME = 40;
    private ObjectsManager objectsManager;
    private AbstractGameCharacter gameCharacter;
    private List<BulletSet> bulletSets = new ArrayList<BulletSet>();
    private List<Explode> explodes = new ArrayList<Explode>();
    public boolean game_started = false;
    public static boolean is_running;
    CharacterJumpActivity context;
////////////////////////////////////////////////////////////////////////////////////////////////////
    public LogicManager(CharacterJumpActivity context){
        this.context = context;
        objectsManager = new ObjectsManager(context);
        gameCharacter = GameCharacter.GameCharacterFactory(context);
        is_running = true;
        new Thread(new LogicThread()).start();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void clear(){
        is_running = false;
        gameCharacter = null;
        bulletSets.clear();
        explodes.clear();
        objectsManager.clear();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void drawAndroidAndBars(Canvas canvas){
        objectsManager.drawBarsAndMonsters(canvas);
        gameCharacter.drawSelf(canvas);
        for(BulletSet bulletSet : bulletSets){
            bulletSet.drawBullets(canvas);
        }
        for(Explode explode : explodes){
            explode.drawSelf(canvas);
        }
        removeBulletSets();
        removeExplodes();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void removeExplodes() {
        for(int i=0; i<explodes.size(); i++){
            if(explodes.get(i) != null){
                if(explodes.get(i).isDone())
                    explodes.remove(i);
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void removeBulletSets() {
        for(int i=0; i<bulletSets.size(); i++){
            if(bulletSets.get(i).getY() < 0)
                bulletSets.remove(i);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addNewBulletSet(){
        try{
            if(gameCharacter.bullet_times > 0){
                bulletSets.add(new BulletSet(gameCharacter, context));
                gameCharacter.bullet_times --;
            }
        }catch(Exception e){}
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void settGameCharacterHands(){
        gameCharacter.bitmap_index = GameCharacter.HANDS_UP;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setGameCharacterHorizontalSpeed(float horizontal_speed){
        gameCharacter.horizontal_speed = - horizontal_speed;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private class LogicThread implements Runnable{
        float add;
        @Override
        public void run() {
            while(is_running){
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (Exception e) { }

                finally{
                    try{
                        settGameCharacterHands();
                        gameCharacter.move();
                        gameCharacter.checkGameCharacterCoor(objectsManager);
                    }catch(Exception e){  }

                    checkVertivalSpeed();
                    isBulletTouchMonsters();
                    objectsManager.checkIsTouchItems(gameCharacter);
                    objectsManager.checkIsTouchMonsters(gameCharacter);

                    if(gameCharacter.life_num<0 || gameCharacter.is_fall_down){
                        is_running = false;
                        GameView.isGameOver = true;
                    }
                }
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        private void checkVertivalSpeed() {
            if(!GameView.ispause){
                //gameCharacter speed control factor
                try{
                    if(gameCharacter.current_state == GameCharacter.STATE_GO_UP){
                        gameCharacter.LTCoorY += gameCharacter.vertical_speed;
                        if(game_started){
                            objectsManager.moveBarsAndMonstersDown();
                            for(int i=0; i<explodes.size(); i++){
                                if(explodes.get(i) != null){
                                    explodes.get(i).CoorY -= gameCharacter.vertical_speed;
                                    explodes.get(i).CoorY += add;
                                }
                            }
                        }
                        if(gameCharacter.vertical_speed >= 0){
                            gameCharacter.vertical_speed = 0;
                            gameCharacter.current_state = GameCharacter.STATE_GO_DOWN;
                            game_started = true;
                        }
                    }

                    if(gameCharacter.current_state == GameCharacter.STATE_GO_DOWN) {
                        if(gameCharacter.vertical_speed >= GameCharacter.MAX_VERTICAL_SPEED)
                            gameCharacter.vertical_speed = GameCharacter.MAX_VERTICAL_SPEED;
                        float temp =  gameCharacter.vertical_speed;
                        for(float i=0; i<=temp; i += 0.5){
                            gameCharacter.LTCoorY += 0.5;
                            if(objectsManager.isTouchBars(gameCharacter.LTCoorX, gameCharacter.LTCoorY)){
                                add = getAdd(gameCharacter.LTCoorY);
                                gameCharacter.vertical_speed = GameCharacter.INITIAL_VERTICAL_SPEED;
                                gameCharacter.current_state = GameCharacter.STATE_GO_UP;
                                break;
                            }
                        }
                    }
                }catch (Exception e){}
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        private int getAdd(float lTCoorY) {
            if(lTCoorY >=  350* CharacterJumpActivity.height_factor)
                return (int) (0 * CharacterJumpActivity.height_factor);
            else
                return (int) (3* CharacterJumpActivity.height_factor);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        private void isBulletTouchMonsters() {
            List<String> DestroyMonsters = new ArrayList<String>();
            try{
                for(BulletSet bulletSet : bulletSets){
                    for(String key : objectsManager.monsterMap.keySet()){
                        if(bulletSet.isTouchBullet(objectsManager.monsterMap.get(key))){
                            DestroyMonsters.add(key);
                            ObjectsManager.score+=10;
                            explodes.add(new Explode((int) (objectsManager.monsterMap.get(key).getX() - 25* CharacterJumpActivity.height_factor), (int) (objectsManager.monsterMap.get(key).CoorY - 25* CharacterJumpActivity.height_factor), context));
                        }
                    }
                    for(String key : DestroyMonsters){
                        objectsManager.monsterMap.remove(key);
                    }
                    DestroyMonsters.clear();
                }
            }catch(Exception e){}
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
