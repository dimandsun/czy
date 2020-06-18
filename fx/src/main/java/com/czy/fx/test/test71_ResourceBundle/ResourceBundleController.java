package com.czy.fx.test.test71_ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author chenzy
 * @since 2020-06-12
 */
public class ResourceBundleController {
    @FXML private Button btn;
    @FXML private Button btn2;
    @FXML private Button btn3;

    public Button getBtn() {
        return btn;
    }

    public Button getBtn2() {
        return btn2;
    }

    public Button getBtn3() {
        return btn3;
    }

    @FXML
    private void initialize() {
    }

}
