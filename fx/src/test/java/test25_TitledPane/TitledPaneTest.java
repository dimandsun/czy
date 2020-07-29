package test25_TitledPane;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author chenzy
 * 
 * @since 2020/5/10
 */
public class TitledPaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var ap = new AnchorPane();
        var tp = new TitledPane("TitledPane", new Button("1233"));
        tp.setAnimated(false);//禁用默认的展开缩放动画
        tp.setCollapsible(false);//禁用折叠
        tp.setExpanded(false);//缩，配合setCollapsible(false)使用可以隐藏子控件

        /*管理TitledPan，每次只能展开一个TitledPan*/
        var ad = new Accordion();
        ad.getPanes().addAll();
        var tp2 = new TitledPane();
        tp2.setExpanded(true);//展开
        var box=new HBox();
        box.getChildren().addAll(FXUtil.getButtonList("1","2"));
        tp2.setText("TitledPane");
        tp2.setContent(box);
        tp2.setGraphic(new ImageView("qq.jpg"));
        ap.getChildren().addAll(tp, tp2);
        AnchorPane.setBottomAnchor(tp2, 20d);

        /*折叠展开监听*/
        tp2.expandedProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        }));
        /*箭头方向*/
        tp2.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
