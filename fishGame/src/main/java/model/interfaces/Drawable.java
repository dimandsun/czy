package model.interfaces;
import base.graphics.Bitmap;
import base.graphics.Canvas;
import base.graphics.Matrix;
import base.graphics.Paint;

public interface Drawable {
	public Matrix getPicMatrix();//获取图片旋转的矩阵表示
	public Bitmap getCurrentPic();//获取当前动作图片的资源
	public int getPicWidth();//返回图片的宽度
	public int getPicHeight();//返回图片的高度
	public void onDraw(Canvas canvas, Paint paint);//绘制的回调方法
}
