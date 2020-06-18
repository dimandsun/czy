package com.czy.fish_game.model;

import com.czy.fish_game.base.graphics.Bitmap;
/**
 * 百分显示
 * @author Xiloerfan
 *
 */
public class HighPoint extends DrawableAdapter{
	private Bitmap[] imgs;
	private int currentPicId;
	public HighPoint(Bitmap[] imgs){
		this.imgs = imgs;
	}
	public int getActPicLength(){
		return imgs.length;
	}
	public void setCurrentPicId(int currentPicId){
		this.currentPicId = currentPicId;
	}
	@Override
	public Bitmap getCurrentPic() {
		
		return imgs[currentPicId];
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
