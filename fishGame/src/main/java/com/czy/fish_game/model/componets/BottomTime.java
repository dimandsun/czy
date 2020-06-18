package com.czy.fish_game.model.componets;

import java.util.HashMap;

import com.czy.fish_game.base.graphics.Bitmap;
import com.czy.fish_game.base.graphics.Canvas;
import com.czy.fish_game.base.graphics.Paint;
import com.czy.fish_game.manager.ImageManager;
import com.czy.fish_game.model.GamingInfo;
import com.czy.fish_game.tools.LogTools;

/**
 * 金币显示组件
 * @author Xiloerfan
 *
 */
public class BottomTime extends Componet{
	private int[] num_index = new int[1];//所有数字的索引，这里第一个元素代表得分的最大位数，往后类推
	private Bitmap pic;
	private Bitmap[] num;
	private int numShowX,numShowY;//数字显示的X和Y坐标
	private int numPicWidth;	 //数字宽度，所有数字宽度是一样的
	public BottomTime(){
		try {
			initNum();
			pic = ImageManager.getImageMnagaer().getscaleImageByScreenFromAssets("image/componet/bottom_time.png");
			numPicWidth = num[0].getWidth();			
		} catch (Exception e) {
			LogTools.doLogForException(e);
		}
	
	}
	public void setPosition(int layoutX,int layoutY){
		this.setLayout_x(layoutX);
		this.setLayout_y(layoutY);
		numShowX = layoutX+pic.getWidth()/3;
		numShowY = layoutY+pic.getHeight()/4;
		this.getPicMatrix().setTranslate(this.getLayout_x(),this.getLayout_y());
	}
	/**
	 * 初始化显示的数字
	 */
	private void initNum(){
		HashMap<String,Bitmap> allNum = ImageManager.getImageMnagaer().getImagesMapByImageConfig(ImageManager.getImageMnagaer().createImageConfigByPlist("image/componet/num_gold"),ImageManager.getImageMnagaer().scaleNum);
		//效果图全名(num_0.png)
		StringBuffer numFullName = new StringBuffer();
		String numName = "num_";
		num = new Bitmap[10];
		for(int num = 0;num<10&&GamingInfo.getGamingInfo().isGaming();num++){
			numFullName.delete(0, numFullName.length());
			numFullName.append(numName+num+".png");
			this.num[num] =  allNum.get(numFullName.toString());
		}
	}
	@Override
	public void onDraw(Canvas canvas, Paint paint) {
		super.onDraw(canvas, paint);
		for(int i=0;i<num_index.length;i++){
			canvas.drawBitmap(num[num_index[i]], numShowX+(i*numPicWidth), numShowY, paint);
		}
	}
	/**
	 * 更新数字索引
	 */
	public void updateNumIndex(int time){
		String num = time+"";
		num_index = new int[num.length()];
		int index = 0;
		for(char n:num.toCharArray()){
			num_index[index] = n-48;
			index++;
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
