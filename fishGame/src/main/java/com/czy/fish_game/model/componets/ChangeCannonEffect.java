package com.czy.fish_game.model.componets;

import com.czy.fish_game.base.graphics.Bitmap;
import com.czy.fish_game.base.tools.Log;
import com.czy.fish_game.constants.Constant;
import com.czy.fish_game.model.DrawableAdapter;
import com.czy.fish_game.model.GamingInfo;

/**
 * 更换大炮时的变换效果
 * @author Xiloerfan
 *
 */
public class ChangeCannonEffect extends DrawableAdapter{
	private Bitmap[] effect;
	private int currentId;
	public ChangeCannonEffect(Bitmap[] effect){
		this.effect = effect;
	}
	/**
	 * 播放效果
	 */
	public void playEffect(){
		this.getPicMatrix().setTranslate(GamingInfo.getGamingInfo().getCannonLayoutX()-this.getPicWidth()/2, GamingInfo.getGamingInfo().getCannonLayoutY()-this.getPicHeight()/2);
		GamingInfo.getGamingInfo().getSurface().putDrawablePic(Constant.CHANGE_CANNON_EFFECT_LAYER, this);
		for(int i =0;i<effect.length;i++){
			currentId = i;
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				Log.e("ChangeCannonEffect", e.toString());
			}
		}
		GamingInfo.getGamingInfo().getSurface().removeDrawablePic(Constant.CHANGE_CANNON_EFFECT_LAYER, this);
	}
	
	@Override
	public Bitmap getCurrentPic() {
		
		return effect[currentId];
	}
	@Override
	public int getPicWidth() {
		
		return effect[currentId].getWidth();
	}
	@Override
	public int getPicHeight() {
		
		return effect[currentId].getHeight();
	}
	
}
