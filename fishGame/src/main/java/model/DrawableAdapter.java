package model;

import base.graphics.Canvas;
import base.graphics.Matrix;
import base.graphics.Paint;
import basecomponet.JMatrix;
import model.interfaces.Drawable;

public abstract class DrawableAdapter implements Drawable{
	private Matrix matrix = new JMatrix();
	
	public Matrix getPicMatrix() {
		// TODO Auto-generated method stub
		return matrix;
	}

	public void onDraw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(this.getCurrentPic(),
				this.getPicMatrix(), paint);		
	}

	
}
