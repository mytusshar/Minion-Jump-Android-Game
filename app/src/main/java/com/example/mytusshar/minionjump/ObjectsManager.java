package com.example.mytusshar.minionjump;

/**
 * Created by mytusshar on 9/11/2016.
 */

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.mytusshar.minionjump.Items.AbstractItem;
import com.example.mytusshar.minionjump.Items.ItemBullet;
import com.example.mytusshar.minionjump.Items.ItemFruit;
import com.example.mytusshar.minionjump.Items.ItemUpgradeBullet;
import com.example.mytusshar.minionjump.gamecharacter.AbstractGameCharacter;
import com.example.mytusshar.minionjump.bars.AbstractBar;
import com.example.mytusshar.minionjump.bars.InstantShiftBar;
import com.example.mytusshar.minionjump.bars.NormalBar;
import com.example.mytusshar.minionjump.bars.ShiftBar;
import com.example.mytusshar.minionjump.monsters.AbstractMonster;
import com.example.mytusshar.minionjump.monsters.EatingHead;
import com.example.mytusshar.minionjump.monsters.RotateMonster;
import com.example.mytusshar.minionjump.otherviews.OptionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ObjectsManager {

    public static long score;
    private int shift_bar_appear_rate = 65;
    private int instant_shift_bar_appear_rate = 85;
    public int monster_appear_rate = 80;
    public int item_appear_rate = 75;
    private int monsters_appear = 0;
    public int item_appear = 0;
    private Map<String, AbstractBar> barMap;
    public Map<String, AbstractMonster> monsterMap;
    private long bar_identifier = 0;
    private long monster_identifier = 0;
    public int touch_bar_type = AbstractBar.TYPE_NORMAL;
    private CharacterJumpActivity context;
    private Paint paint;
    SharedPreferences get_Difficulty_Setting;
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public ObjectsManager(CharacterJumpActivity context){
        this.context = context;
        barMap = new HashMap<String, AbstractBar>();
        monsterMap = new HashMap<String, AbstractMonster>();
        initBarMap();
        initPaint();
        score = 0;
        setDifficulty(context);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20* CharacterJumpActivity.scaled_density);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setDifficulty(CharacterJumpActivity context) {
        get_Difficulty_Setting = context.getSharedPreferences(OptionView.PREFS_DIFF, 0);
        String diffString = get_Difficulty_Setting.getString(OptionView.PREFS_DIFF, "Tush");
        if(diffString.equalsIgnoreCase(OptionView.DIFFICULTY_TUSH)){
            instant_shift_bar_appear_rate = 5;
            shift_bar_appear_rate = 15;
            monster_appear_rate = 50;
            item_appear_rate = 70;
        }
        else if(diffString.equalsIgnoreCase(OptionView.DIFFICULTY_NORMAL)){
            instant_shift_bar_appear_rate = 10;
            shift_bar_appear_rate = 30;
            monster_appear_rate = 10;
            item_appear_rate = 70;
        }
        else if(diffString.equalsIgnoreCase(OptionView.DIFFICULTY_MASTER)){
            instant_shift_bar_appear_rate = 10;
            shift_bar_appear_rate = 50;
            monster_appear_rate = 10;
            item_appear_rate = 80;
        }
        else{
            instant_shift_bar_appear_rate = 20;
            shift_bar_appear_rate = 70;
            monster_appear_rate = 10;
            item_appear_rate = 95;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void clear(){
        barMap.clear();
        monsterMap.clear();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    //mapping Normal Bars on gameView
    // when game is first started
    public void initBarMap(){
        int count = 0;
        int factor = 100;
        //resolving different screen size issue
        if(CharacterJumpActivity.density <=2.3)
        {
            factor = 160;
        }
        else if(CharacterJumpActivity.density >2.3 && CharacterJumpActivity.density <3)
        {
            factor = 250;
        }
        else
        {
            factor = 350;
        }

        //////////////////////////////////////////
        while(count < CharacterJumpActivity.screen_height / (20 * CharacterJumpActivity.height_factor)){
            AbstractBar bar = new NormalBar((int)(CharacterJumpActivity.screen_width/2) ,100+count*factor, getRandomItem(), context);
            barMap.put(""+bar_identifier, bar);
            bar_identifier ++;
            count ++;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean hasMonster() {
        int temp = new Random().nextInt(100);
        if(temp > monster_appear_rate)
            return true;
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    //draws new bars and monsters on gameView
    // when minion starts jumping
    public void drawBarsAndMonsters(Canvas canvas){
        for(String key : barMap.keySet()){
            barMap.get(key).drawSelf(canvas);
        }
        for(String key : monsterMap.keySet()){
            monsterMap.get(key).drawSelf(canvas);
        }
        drawHeight(canvas);
        removeOuterBarsAndMonsters();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawHeight(Canvas canvas) {
        canvas.drawText(""+score, 5*CharacterJumpActivity.width_factor, 20*CharacterJumpActivity.scaled_density, paint);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isTouchBars(float CoorX, float CoorY){
        try{
            for(String key : barMap.keySet()){
                if(barMap.get(key).isBeingStep(CoorX, CoorY)){
                    touch_bar_type = barMap.get(key).type;
                    return true;
                }
            }
        }catch (Exception e){}
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void moveBarsAndMonstersDown(){
        float vertical_speed ;
        float add ;
        //resolving different screen size issue
        if(CharacterJumpActivity.density <=2.3)
        {
            vertical_speed = -6f;
            add = 6f;
        }
        else if(CharacterJumpActivity.density >2.3 && CharacterJumpActivity.density <3)
        {
            vertical_speed = -9f;
            add = 9f;
        }
        else
        {
            vertical_speed = -12f;
            add = 12f;
        }

        //////////////////////////////////////////
        try{
            for(String key : barMap.keySet()){
                barMap.get(key).TLCoorY -= vertical_speed;
                barMap.get(key).TLCoorY += add;
            }
            for(String key : monsterMap.keySet()){
                monsterMap.get(key).CoorY -= vertical_speed;
                monsterMap.get(key).CoorY += add;
            }
        }catch(Exception e){}
        score++;
        monsters_appear = item_appear += add - vertical_speed;
        addNewBarsAndMonsters();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void removeOuterBarsAndMonsters() {
        List<String> temp = new ArrayList<String>();
        for (String key : barMap.keySet()) {
            if (barMap.get(key).TLCoorY > CharacterJumpActivity.screen_height - 40 * CharacterJumpActivity.height_factor) {
                barMap.get(key).clear();
                temp.add(key);
            }
        }
        for (String key : temp) {
            barMap.remove(key);
        }
        temp.clear();
        for (String key : monsterMap.keySet()) {
            if (monsterMap.get(key).CoorY > CharacterJumpActivity.screen_height + 20) {
                monsterMap.get(key).is_running = false;
                temp.add(key);
            }
        }
        for (String key : temp) {
            monsterMap.remove(key);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    //this is adding new bars at the top of gameView while game running
    public void addNewBarsAndMonsters(){
        AbstractBar bar;
        float tempY = getTopCoorY();
        if(tempY > (60*CharacterJumpActivity.height_factor)){
            int CoorX = new Random().nextInt((int) (CharacterJumpActivity.screen_width-50));
            while(CoorX < 5*CharacterJumpActivity.width_factor && CoorX > CharacterJumpActivity.screen_height -5*CharacterJumpActivity.width_factor )
            {
                CoorX = new Random().nextInt((int)(CharacterJumpActivity.screen_width-50));
            }

            float CoorY = 20;
            for(int i=0; i<2; i++) {
                if(appearShiftBar()){
                    bar = new ShiftBar(CoorX, (int)CoorY, getRandomItem(), context);
                }
                else if(appearInstantShiftBar()){
                    bar = new InstantShiftBar(CoorX, (int)CoorY, context);
                }
                else{
                    bar = new NormalBar(CoorX, CoorY, getRandomItem(), context);
                }
                barMap.put(""+bar_identifier, bar);
                bar_identifier++;
                //resolving different screen size issue
                if(CharacterJumpActivity.density <=2.3)
                {
                    CoorY = -130;
                }
                else if(CharacterJumpActivity.density >2.3 && CharacterJumpActivity.density <3)
                {
                    CoorY = -170;
                }
                else
                {
                    CoorY = -200;
                }

                //////////////////////////////////////////
            }
            if(hasMonster() && monsters_appear >= 300*CharacterJumpActivity.height_factor){
                monsters_appear = 0;
                int temp2 = new Random().nextInt(100);
                if(temp2 < 40)
                    monsterMap.put(""+monster_identifier, new EatingHead(-10*CharacterJumpActivity.height_factor, context));
                else
                    monsterMap.put(""+monster_identifier, new RotateMonster(-10*CharacterJumpActivity.height_factor, context));

                monster_identifier ++;
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private float getTopCoorY() {
        float tempY = 100;
        try{
            for(String key : barMap.keySet()){
                if(barMap.get(key).item == null){
                    if(barMap.get(key).TLCoorY <= tempY)
                        tempY = barMap.get(key).TLCoorY;
                }
                else if(barMap.get(key).TLCoorY - 20 * CharacterJumpActivity.height_factor <= tempY){
                    tempY = -1111;
                }
            }
            for(String key : monsterMap.keySet()){
                if(monsterMap.get(key).CoorY - 25  * CharacterJumpActivity.height_factor <= tempY) {
                    tempY = -1111;
                }
            }
        }catch(Exception e){}
        return tempY;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean appearShiftBar(){
        int temp = new Random().nextInt(100);
        if(temp < shift_bar_appear_rate)
            return true;
        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean appearInstantShiftBar(){
        int temp = new Random().nextInt(100);
        if(temp < instant_shift_bar_appear_rate)
            return true;

        return false;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private AbstractItem getRandomItem() {
        if(item_appear >= 400 * CharacterJumpActivity.height_factor){
            item_appear = 0;
            int temp = new Random(bar_identifier).nextInt(100);
            if(temp > monster_appear_rate){
                temp = new Random().nextInt(100);
                if(temp < 10)
                    return new ItemUpgradeBullet(context);
                else if(temp < 30)
                    return new ItemBullet(context);
                else
                    return new ItemFruit(context);
            }
        }
        return null;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void checkIsTouchItems(AbstractGameCharacter gameCharacter){
        try{
            float temp_x = gameCharacter.LTCoorX + 22 * CharacterJumpActivity.height_factor;
            float temp_y = gameCharacter.LTCoorY + 22 * CharacterJumpActivity.height_factor;

            for(String key : barMap.keySet()){
                if(barMap.get(key).is_item_eaten == false && barMap.get(key).item != null){
                    int X = (int) (barMap.get(key).TLCoorX + 25 * CharacterJumpActivity.height_factor);
                    float Y = barMap.get(key).TLCoorY - 10 * CharacterJumpActivity.height_factor;
                    if(getLength(temp_x, temp_y, X, Y) < 30 * CharacterJumpActivity.height_factor){
                        barMap.get(key).item.modifyGameCharacter(gameCharacter);
                        barMap.get(key).is_item_eaten = true;
                    }
                }
            }
        }catch (Exception e){}
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private int getLength(float temp_x, float temp_y, int x, float y) {
        double x_length = temp_x - x;
        double y_length = temp_y - y;
        return (int) Math.sqrt(x_length*x_length + y_length*y_length);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    public void checkIsTouchMonsters(AbstractGameCharacter gameCharacter) {
        for (String key : monsterMap.keySet()) {
            monsterMap.get(key).checkDistance(gameCharacter);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
