package test7_screen;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020/4/27
 *  屏幕信息
 */
public class ScreenTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Screen screen= Screen.getPrimary();
        var rectangle2D= screen.getBounds();//获取整个屏幕的宽高信息
        var rectangle2D1=screen.getVisualBounds();//获取可视化的屏幕的宽高

        var dpi=screen.getDpi();
        System.out.println(12);
    }
}
