package base.graphics;

/*
 * 画板
 */
public interface Canvas {
	public void drawBitmap(Bitmap bitmap, base.graphics.Matrix matrix, Paint paint);

	public void drawBitmap(Bitmap bitmap, float x, float y, Paint paint);
}
