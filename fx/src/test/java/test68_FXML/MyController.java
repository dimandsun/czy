package test68_FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author chenzy
 * @since 2020-06-10
 */
public class MyController {
    @FXML
    private Button buttonId;
    @FXML
    private Label a;

    public MyController() {
    }
    @FXML
    private void  initialize(){
        System.out.println("aasfasd");

    }

    public void action(ActionEvent event) {
        System.out.println("MyController:action");

    }
}
