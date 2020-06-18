package com.czy.fish_game.model;

import com.czy.fish_game.base.graphics.Bitmap;

/**
 * 水波纹
 * @author Xiloer
 *
 */
public class WaterRipple extends DrawableAdapter{
	private Bitmap[] ripple;
	private int currentId;
	public WaterRipple(Bitmap[] ripple){
		this.ripple = ripple;
	}
	
	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	@Override
	public Bitmap getCurrentPic() {
		
		return ripple[currentId];
	}

	@Override
	public int getPicWidth() {
		
		return getCurrentPic().getWidth();
	}

	@Override
	public int getPicHeight() {
		
		return getCurrentPic().getHeight();
	}

}
