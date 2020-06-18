package com.czy.fish_game.model.componets;

import com.czy.fish_game.base.graphics.Bitmap;
import com.czy.fish_game.base.tools.Log;
import com.czy.fish_game.manager.ImageManager;
import com.czy.fish_game.model.GamingInfo;
/**
 * 大炮底座
 * @author Xiloerfan
 *
 */
public class Bottom extends Componet{
	private Bitmap pic;
	public Bottom(){
		try{
		pic = ImageManager.getImageMnagaer().getscaleImageByScreenFromAssets("image/componet/bottom.png");
		this.setLayout_x(GamingInfo.getGamingInfo().getScreenWidth()/2-getPicWidth()/2);
		this.setLayout_y(GamingInfo.getGamingInfo().getScreenHeight()-getPicHeight());
		this.getPicMatrix().setTranslate(this.getLayout_x(),this.getLayout_y());
		}catch(Exception e){
			Log.e("Bottom", e.toString());
		}
	}

	public Bitmap getCurrentPic() {
		
		return pic;
	}

	public int getPicWidth() {
		
		return pic.getWidth();
	}

	public int getPicHeight() {
		
		return pic.getHeight();
	}

}
