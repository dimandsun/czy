package com.czy.fx.test.test65_TableView.yiNanPing;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class TableViewSample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(400);
        stage.setHeight(500);

        final Label label = new Label("Student IDs");
        label.setFont(new Font("Arial", 20));

        TableView tableView = new TableView<>();
        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);

        ObservableList<TableColumn> tableViewColumns = generateTableViewColumns();
        tableView.getColumns().setAll(tableViewColumns);

        ObservableList<TableColumn> tcs = tableView.getColumns();
        for (int i = 0; i < tcs.size(); i++) {
            TableColumn tc = tcs.get(i);
            if (i == 0) {
                Callback<TableColumn.CellDataFeatures<ArrayList, Color>, ObservableValue<Color>> cellValueFactory = buildCallbackColor(i);
                tc.setCellValueFactory(cellValueFactory);
            } else {
                Callback<TableColumn.CellDataFeatures<ArrayList, String>, ObservableValue<String>> cellValueFactory = buildCallbackString(i);
                tc.setCellValueFactory(cellValueFactory);
            }

        }

        ObservableList<ArrayList> tableViewRows = generateTableViewRows();
        tableView.getItems().setAll(tableViewRows);

        for (int i = 0; i < tcs.size(); i++) {
            TableColumn dataColumn = tcs.get(i);
            if (i == 0) {
                Callback<TableColumn<ArrayList, Color>, TableCell<ArrayList, Color>> cellFactoryPane = buildCallbackPane();
                dataColumn.setCellFactory(cellFactoryPane);
            } else {
                Callback<TableColumn<ArrayList, String>, TableCell<ArrayList, String>> cellFactoryTextFieldTableCell = buildCallbackTextFieldTableCell();
                dataColumn.setCellFactory(cellFactoryTextFieldTableCell);
            }

        }

        final VBox vbox = new VBox();

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableView);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);

        stage.show();
    }

    private ObservableList<TableColumn> generateTableViewColumns() {
        ObservableList<TableColumn> tableViewColumns = FXCollections.observableArrayList();
        TableColumn firstDataColumn = new TableColumn<>("Activity");
        TableColumn secondDataColumn = new TableColumn<>("Class A");
        TableColumn thirdDataColumn = new TableColumn<>("Class B");

        firstDataColumn.setMinWidth(80);
        secondDataColumn.setMinWidth(130);
        thirdDataColumn.setMinWidth(130);

        tableViewColumns.add(firstDataColumn);
        tableViewColumns.add(secondDataColumn);
        tableViewColumns.add(thirdDataColumn);

        return tableViewColumns;
    }

    private ObservableList<ArrayList> generateTableViewRows() {
        int max = 6;
        ObservableList<ArrayList> tableViewRows = FXCollections.observableArrayList();
        for (int i = 1; i < max; i++) {
            ArrayList dataRow = new ArrayList<>();

            Color value1 = Color.GREEN;
            String value2 = "A" + i;
            String value3 = "B" + i;

            dataRow.add(value1);
            dataRow.add(value2);
            dataRow.add(value3);

            tableViewRows.add(dataRow);
        }
        return tableViewRows;
    }

    private Callback<TableColumn.CellDataFeatures<ArrayList, Color>, ObservableValue<Color>> buildCallbackColor(int index) {
        Callback<TableColumn.CellDataFeatures<ArrayList, Color>, ObservableValue<Color>> cellValueFactory = new Callback<TableColumn.CellDataFeatures<ArrayList, Color>, ObservableValue<Color>>() {
            @Override
            public ObservableValue<Color> call(TableColumn.CellDataFeatures<ArrayList, Color> param) {
                return new SimpleObjectProperty(param.getValue().get(index));
            }
        };
        return cellValueFactory;
    }

    private Callback<TableColumn.CellDataFeatures<ArrayList, String>, ObservableValue<String>> buildCallbackString(int index) {
        Callback<TableColumn.CellDataFeatures<ArrayList, String>, ObservableValue<String>> cellValueFactory = new Callback<TableColumn.CellDataFeatures<ArrayList, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ArrayList, String> param) {
                return new SimpleStringProperty((String) param.getValue().get(index));
            }
        };
        return cellValueFactory;
    }

    private Callback<TableColumn<ArrayList, Color>, TableCell<ArrayList, Color>> buildCallbackPane() {
        Callback<TableColumn<ArrayList, Color>, TableCell<ArrayList, Color>> cellFactory = new Callback<TableColumn<ArrayList, Color>, TableCell<ArrayList, Color>>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                double cellWidth = tableColumn.getMinWidth();
                double cellHeight = 35;

                TableCell tableCell = new TableCell<Object, Color>() {
                    @Override
                    protected void updateItem(Color item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Pane p = new Pane();
                            p.setPrefSize(cellWidth, cellHeight);
                            Canvas canvasRectLayerColor = new Canvas();
                            p.getChildren().add(canvasRectLayerColor);
                            canvasRectLayerColor.setWidth(20);
                            canvasRectLayerColor.setHeight(20);
                            GraphicsContext gc = canvasRectLayerColor.getGraphicsContext2D();
                            gc.setFill(item);
                            gc.fillRect(0, 0, canvasRectLayerColor.getWidth(), canvasRectLayerColor.getHeight());
                            setGraphic(p);
                        }
                    }
                };

                return tableCell;
            }
        };

        return cellFactory;
    }

    private Callback<TableColumn<ArrayList, String>, TableCell<ArrayList, String>> buildCallbackTextFieldTableCell() {
        Callback<TableColumn<ArrayList, String>, TableCell<ArrayList, String>> cellFactory = new Callback<TableColumn<ArrayList, String>, TableCell<ArrayList, String>>() {
            @Override
            public TableCell call(TableColumn tc) {
                TextFieldTableCell tftc = new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        return t.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });

                return tftc;
            }
        };
        return cellFactory;
    }

}
