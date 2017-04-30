package com.example.mytusshar.minionjump.bars;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import com.example.mytusshar.minionjump.CharacterJumpActivity;
import com.example.mytusshar.minionjump.GameView;
import com.example.mytusshar.minionjump.Items.AbstractItem;
import com.example.mytusshar.minionjump.R;


public class ShiftBar extends AbstractBar {

	int horizontal_speed = 3;
	Bitmap shift_bar;
	boolean is_running = true;
////////////////////////////////////////////////////////////////////////////////////////////////////
	public ShiftBar(int CoorX, int CoorY, AbstractItem item, CharacterJumpActivity context){
		this.TLCoorX = CoorX;
		this.TLCoorY = CoorY;
		this.type = TYPE_SHIFT;
		this.item = item;
		this.is_item_eaten = false;
		shift_bar = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.shift_bar)).getBitmap();
		new Thread(new ShiftThread()).start();
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void drawSelf(Canvas canvas) {
		if(this.item != null && !this.is_item_eaten)
			this.item.drawSelf(canvas, TLCoorX + 15* CharacterJumpActivity.height_factor, TLCoorY - 20* CharacterJumpActivity.height_factor);
		canvas.drawBitmap(shift_bar, TLCoorX, TLCoorY, null);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean isBeingStep(float CoorX, float CoorY) {
		/////////////////////////////////////
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
		///////////////////////////////////
		if(CoorX >= TLCoorX - 35 * CharacterJumpActivity.width_factor && CoorX <= TLCoorX + 45 * CharacterJumpActivity.width_factor && CoorY + 45 * CharacterJumpActivity.height_factor <= TLCoorY && TLCoorY - CoorY <= 45 * CharacterJumpActivity.height_factor){
			return true;
		}
		return false;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void clear() {
		is_running = false;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	private class ShiftThread implements Runnable{
		@Override
		public void run() {
			while(is_running){
				try {
					Thread.sleep(40);
				} catch (Exception e) { }
				finally{
					if(!GameView.ispause){
						TLCoorX += horizontal_speed;
						if(TLCoorX >= CharacterJumpActivity.screen_width - 40* CharacterJumpActivity.width_factor){
							TLCoorX = (int)(CharacterJumpActivity.screen_width - 40* CharacterJumpActivity.width_factor);
							horizontal_speed = -3;
						}
						if(TLCoorX <= 0){
							TLCoorX = 0;
							horizontal_speed = 3;
						}
					}
				}
			}
		}

	}
////////////////////////////////////////////////////////////////////////////////////////////////////
}
