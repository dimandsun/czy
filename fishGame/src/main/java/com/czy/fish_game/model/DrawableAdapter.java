package com.czy.fish_game.model;

import com.czy.fish_game.base.graphics.Canvas;
import com.czy.fish_game.base.graphics.Matrix;
import com.czy.fish_game.base.graphics.Paint;
import com.czy.fish_game.basecomponet.JMatrix;
import com.czy.fish_game.model.interfaces.Drawable;

public abstract class DrawableAdapter implements Drawable{
	private Matrix matrix = new JMatrix();
	
	public Matrix getPicMatrix() {
		
		return matrix;
	}

	public void onDraw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(this.getCurrentPic(),
				this.getPicMatrix(), paint);		
	}

	
}
