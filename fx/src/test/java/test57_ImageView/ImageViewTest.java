package test57_ImageView;

import com.czy.fx.test.FXUtil;
import com.czy.util.io.FileUtilOld;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static com.czy.util.text.StringUtil.println;

/**
 * @author chenzy
 *
 * @since 2020-05-23
 * 像素画
 */
public class ImageViewTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var imgView = new ImageView("qq.jpg");
        anchorPane.getChildren().addAll(imgView);

        /*在image中设置可以节省内存*/
        /*保持宽高比*/
        imgView.setPreserveRatio(true);
        /*更好的压缩质量*/
        imgView.setSmooth(true);
        imgView.setFitWidth(600);
        imgView.setFitHeight(600);


        /*真正宽高*/
        System.out.println(imgView.prefWidth(-1));
        System.out.println(imgView.prefHeight(-1));
        /*偏移量*/
        imgView.getContentBias();
        /*圆角图片*/
        var rectangle = new Rectangle(imgView.prefWidth(-1), imgView.prefHeight(-1));
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);
        imgView.setClip(rectangle);
        imgView.setX(100);
        rectangle.setX(100);

        /*图片像素点：rgb
         */
        var image = imgView.getImage();
        var pixelReader = image.getPixelReader();
        /*左上角第一个像素点*/
        int rgb = pixelReader.getArgb(0, 0);
        int alpha = rgb >> 24 & 0xff;
        int red = rgb >> 16 & 0xff;
        int green = rgb >> 8 & 0xff;
        int blue = rgb & 0xff;
        println("pixelReader:{},rgb:{}-{}-{}-{}", rgb, blue, green, red, alpha);
        /*像素格式*/
        pixelReader.getPixelFormat().getType();
        /**/
        var color = pixelReader.getColor(0, 0);
        println("color:{},rgb:{}-{}-{}-{}", color, color.getBlue() * 255, color.getGreen() * 255, color.getRed() * 255, color.getOpacity() * 255);
        System.out.println(color);

        /*批量获取像素点*/
        getPixels(pixelReader);
        /**/
        getPixels2(pixelReader);
        /*写图片*/
        var imageView = getImage();
        imageView = getImage2();
        /*保存图片*/
        var bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
        ImageIO.write(bufferedImage, "jpg", new File("d:/a.jpg"));
        /*截图*/
        var imageView1 = new ImageView(imageView.getImage());
        var snapshot = imageView1.snapshot(null, null);

        /**/
//        abc(new Image("girl.png"),400,400);
        abc(new Image("girl2.jpg"),600,400);
        /**/
        anchorPane.getChildren().add(imageView);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
    }

    private void abc(Image image,Integer width,Integer height) {
        var pixelReader = image.getPixelReader();
        String data = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                var color = pixelReader.getColor(j, i);
                Integer value = (int) (color.getRed()*255);
                char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o','+', '0', '<', '-', 'o', '。', '.', ' ',' ', ' ', ' '};
                data += chars[value / 10];
            }
            data += "\r\n";
        }
        FileUtilOld.write(new File("d:/a.text"),data);
    }


    private void getPixels2(PixelReader pixelReader) {
        var ints = new int[3 * 3];
        WritablePixelFormat<IntBuffer> format1 = PixelFormat.getIntArgbInstance();
        pixelReader.getPixels(0, 0, 3, 3, format1, ints, 0, 3);
        for (int in : ints) {
            int alpha1 = in >> 24 & 0xff;
            int red1 = in >> 16 & 0xff;
            int green1 = in >> 8 & 0xff;
            int blue1 = in & 0xff;
            println("批量2:rgb:{}-{}-{}-{}", blue1, green1, red1, alpha1);
        }
    }

    private void getPixels(PixelReader pixelReader) {
        WritablePixelFormat<ByteBuffer> format = PixelFormat.getByteBgraPreInstance();
        var bytes = new byte[3 * 3 * 4];
        pixelReader.getPixels(0, 0, 3, 3, format, bytes, 0, 3 * 4);
        for (int i = 0; i < bytes.length; i += 4) {
            int b = bytes[i] & 0xff;
            int g = bytes[i + 1] & 0xff;
            int r = bytes[i + 2] & 0xff;
            int a = bytes[i + 3] & 0xff;
            println("批量:rgb:{}-{}-{}-{}", b, g, r, a);
        }
    }

    private ImageView getImage2() {
        var writableImage1 = new WritableImage(new Image("qq.jpg").getPixelReader(), 50, 50);
        var pixelWriter1 = writableImage1.getPixelWriter();
        for (int i = 0; i < 50; i++) {
            pixelWriter1.setColor(i, i, Color.valueOf("black"));
        }
        return new ImageView(writableImage1);
    }

    private ImageView getImage() {
        var writableImage = new WritableImage(100, 100);
        var pixelWriter = writableImage.getPixelWriter();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                pixelWriter.setColor(i, j, Color.valueOf("red"));
            }
        }
        for (int i = 0; i < 100; i++) {
            pixelWriter.setColor(i, i, Color.valueOf("black"));
        }
        return new ImageView(writableImage);
    }
}
