package com.czy.fx.test.test69_GraspData;

import com.czy.user.model.User;
import com.czy.util.StringUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.util.List;

import static com.czy.fx.test.test69_GraspData.GraspData.getGraspDataList;

/**
 * @author chenzy
 * @since 2020-06-11
 */
public class GraspDataController {
    @FXML
    private TableView<BulletChat> tableView;
    @FXML
    private TextField roomId;
    @FXML
    private TableColumn<BulletChat, String> nameColumn;
    @FXML
    private TableColumn<BulletChat, String> contentColumn;
    @FXML
    private TableColumn<BulletChat, String> timeColumn;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeline"));
    }

    private ScheduledService<ObservableList<BulletChat>> scheduledService;

    private ScheduledService<ObservableList<BulletChat>> getService(String roomId) {
        if (scheduledService == null) {
            scheduledService = new ScheduledService<>() {
                @Override
                protected Task<ObservableList<BulletChat>> createTask() {
                    return new Task<>() {
                        @Override
                        protected ObservableList<BulletChat> call() {
                            return FXCollections.observableArrayList(getGraspDataList(roomId));
                        }
                    };
                }
            };
        }
        return scheduledService;
    }

    public void getList() {
        String roomIdText = roomId.getText();
        if (StringUtil.isBlank(roomIdText)) {
            return;
        }
        var scheduledService = getService(roomIdText);
        scheduledService.setDelay(Duration.seconds(0));
        scheduledService.setDelay(Duration.seconds(1));
        scheduledService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            tableView.setItems(newValue);
            tableView.scrollTo(newValue.size() - 1);
        });
        scheduledService.start();
    }

    public void outConnection() {
        var scheduledService = getService(roomId.getText());
        scheduledService.cancel();
    }

}
