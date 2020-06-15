package com.czy.fx.test.test76_WebView;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-15
 */
public class WebViewTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("前进");
        var btn2 = new Button("后退");
        var webView = new WebView();
        var engine = webView.getEngine();
        engine.load("https://www.baidu.com");
        //html文本
//        engine.loadContent();
        //字体缩放
//        webView.setFontScale(0.9);
//        webView.setZoom(0.9);
        //浏览记录
        var history = engine.getHistory();
        history.getEntries().forEach(entry -> {
entry.getTitle();
entry.getUrl();
entry.getLastVisitedDate();
        });

        btn.setOnAction(event -> {
            //浏览记录中，当前页面的索引
            history.getCurrentIndex();
            //最大存储页面数量
            history.getMaxSize();
            history.go(1);
        });
        //
        btn2.setOnAction(event -> {
            history.go(-1);
        });

        anchorPane.getChildren().addAll(btn, btn2, webView);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setLeftAnchor(btn2, btn.getWidth() + 10);
        AnchorPane.setTopAnchor(webView, btn.getHeight() + 10);
        webView.prefHeightProperty().bind(anchorPane.heightProperty().subtract(btn.getHeight() + 10));
        webView.prefWidthProperty().bind(anchorPane.widthProperty());
    }
}
