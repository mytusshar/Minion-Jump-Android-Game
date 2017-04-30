package com.example.mytusshar.minionjump.resource;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.R;
import com.example.mytusshar.minionjump.otherviews.OptionView;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

	public static final int SOUND_EAT_FRUIT   = R.raw.eat_fruit;
	public static final int SOUND_BLACK_STONE = R.raw.blackstone;
	public static final int SOUND_EXPLODE     = R.raw.explode;
	public static final int SOUND_FAIL        = R.raw.fail;
	public static final int SOUND_LAUNCH      = R.raw.launch;
	public static final int SOUND_NORMAL_BAR  = R.raw.normal_bar;
	public static final int SOUND_SPRING_BAR  = R.raw.spring_bar;
	public static final int SOUND_GAME_END     = R.raw.fail;

	private MediaPlayer music;
	private static SoundPool soundPool;

	private static boolean musicSt = true;
	private static boolean soundSt = true;
	private static Context context;
	public static boolean isplay_sound = false;
	private static  Map<Integer,Integer> soundMap;

////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void initSound(Context c)
	{

		context = c;
		isplay_sound = context.getSharedPreferences(OptionView.PREFS_NAME, 0).getBoolean(OptionView.SOUND_STRING, true);

		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);

		soundMap = new HashMap<Integer,Integer>();
		soundMap.put(SOUND_EAT_FRUIT, soundPool.load(context, SOUND_EAT_FRUIT, 1));
		soundMap.put(SOUND_BLACK_STONE, soundPool.load(context, SOUND_BLACK_STONE, 1));
		soundMap.put(SOUND_EXPLODE, soundPool.load(context, SOUND_EXPLODE, 1));
		soundMap.put(SOUND_FAIL, soundPool.load(context, SOUND_FAIL, 1));
		soundMap.put(SOUND_LAUNCH, soundPool.load(context, SOUND_LAUNCH, 1));
		soundMap.put(SOUND_NORMAL_BAR, soundPool.load(context, SOUND_NORMAL_BAR, 1));
		soundMap.put(SOUND_SPRING_BAR, soundPool.load(context, SOUND_SPRING_BAR, 1));
		soundMap.put(SOUND_GAME_END, soundPool.load(context, SOUND_GAME_END, 1));
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void playSound(int resId)
	{
		if(soundSt == false)
			return;
		if(CharacterJumpActivity.is_game_running == true && isplay_sound){
			Integer soundId = soundMap.get(resId);
			Log.e("error", ""+soundId);
			if(soundId != null)
				soundPool.play(soundId, 1, 1, 1, 0, 1);
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////

}
