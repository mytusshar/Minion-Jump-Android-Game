package com.example.mytusshar.minionjump.bars;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.GameView;
import com.example.mytusshar.minionjump.R;

public class InstantShiftBar extends AbstractBar {

	private static final int STATE_DISAPPEAR  = 1;
	private static final int STATE_APPEAR = 2;
	private Bitmap InstantShift;
	private int interval = 0;
	private static final int DEFAULT_INTERVAL = 15;
	private static final int DISAPPEAR_RATE = 20;
	private boolean is_disappear = false;
	private int appear_time = 0;
	private int appear_state = STATE_APPEAR;
	boolean is_running = true;
////////////////////////////////////////////////////////////////////////////////////////////////////
	public InstantShiftBar(int CoorX, int CoorY, CharacterJumpActivity context){
		this.TLCoorX = CoorX;
		this.TLCoorY = CoorY;
		InstantShift = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.instantshift)).getBitmap();
		new Thread(new ShiftThread()).start();
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void drawSelf(Canvas canvas) {
		if(!is_disappear)
			canvas.drawBitmap(InstantShift, TLCoorX, TLCoorY, null);
		else{
			Paint paint = new Paint();
			paint.setAlpha(appear_time * DISAPPEAR_RATE);
			canvas.drawBitmap(InstantShift, TLCoorX, TLCoorY, paint);
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean isBeingStep(float CoorX, float CoorY) {
		////////////////////////////////////////////
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
		//////////////////
		////////////////////////////////
		if(is_disappear){
			return false;
		}
		if(CoorX >= TLCoorX - 35 * CharacterJumpActivity.width_factor && CoorX <= TLCoorX + 45 * CharacterJumpActivity.width_factor && CoorY + 45 * CharacterJumpActivity.height_factor <= TLCoorY && TLCoorY - CoorY <= 45 * CharacterJumpActivity.height_factor){
			return true;
		}
		return false;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	private class ShiftThread implements Runnable{
		@Override
		public void run() {
			while(is_running){
				try {
					Thread.sleep(100);
				} catch (Exception e) { }
				if(!GameView.ispause){
					if(is_disappear){
						if(appear_state == STATE_APPEAR){
							appear_time ++;
							if(appear_time > 3){
								appear_state = STATE_DISAPPEAR;
							}
						}
						else{
							appear_time --;
							if(appear_time < 0){
								appear_time = 0;
								appear_state = STATE_APPEAR;
								is_disappear = false;
								interval = 0;
							}
						}
					}
					else{
						interval ++;
						if(interval > DEFAULT_INTERVAL){
							is_disappear = true;
							appear_state = STATE_APPEAR;
						}
					}
				}
			}
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void clear() {
		is_running = false;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
}
