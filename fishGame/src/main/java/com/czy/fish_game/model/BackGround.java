package com.czy.fish_game.model;

import com.czy.fish_game.base.graphics.Bitmap;

public class BackGround extends DrawableAdapter{
	private Bitmap background;
	public void setCurrentPic(Bitmap background){
		this.background = background;
	}
	@Override
	public Bitmap getCurrentPic() {
		
		return background;
	}

	@Override
	public int getPicWidth() {
		
		return background.getWidth();
	}

	@Override
	public int getPicHeight() {
		
		return background.getHeight();
	}

}
