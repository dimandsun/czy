package test65_TableView.yiNanPing;

import com.czy.util.io.FileUtil;
import com.czy.util.json.JsonUtil;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import test65_TableView.yiNanPing.model.Employee;
import test65_TableView.yiNanPing.model.Faculty;
import test65_TableView.yiNanPing.model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class TableViewSample2 extends Application {
    private TableView<Person> table = new TableView<Person>();
    private TableView<Staff> table1 = new TableView<Staff>();
    private TableView<Employee> table2 = new TableView<Employee>();
    private TableView<Faculty> table3 = new TableView<Faculty>();
    private TableView<Student> table4 = new TableView<Student>();
    private TableView<Postgraduate> table5 = new TableView<Postgraduate>();

    private final ObservableList<Person> data = FXCollections.observableArrayList(new Person("Mark", "Chongqin", "123456", "123456@qq.com"));
    private final ObservableList<Staff> data1 = FXCollections.observableArrayList(new Staff("Programmer", "Third floor 108", "7000", "June", "Job", "Chongqin", "156789", "156789@qq.com"));
    private final ObservableList<Employee> data2 = FXCollections.observableArrayList(new Employee("Third floor 107", "5000", "June", "Amy", "Chongqin", "156889", "156889@qq.com"));
    private final ObservableList<Faculty> data3 = FXCollections.observableArrayList(new Faculty("7", "medium", "Third floor 106", "5000", "July", "Bob", "Chongqin", "146789", "146789@qq.com"));
    private final ObservableList<Student> data4 = FXCollections.observableArrayList(new Student("junior first", "Mark", "Chongqin", "123456", "123456@qq.com"));
    private final ObservableList<Postgraduate> data5 = FXCollections.observableArrayList(new Postgraduate("Software Engineering", "Mr.White", "senior first", "Mark", "Chongqin", "123456", "123456@qq.com"));
    File file = new File("Person_czy.text");

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Button person = new Button("Person");
        person.setLayoutX(100);
        person.setLayoutY(50);

        person.setOnAction(e -> {
            primaryStage.close();
            Scene scene = new Scene(new Group());
            primaryStage.setTitle("Person");
            primaryStage.setWidth(700);
            primaryStage.setHeight(600);
            try {
                ((Group) scene.getRoot()).getChildren().addAll(PersonTable());
            } catch (FileNotFoundException e1) {

                e1.printStackTrace();
            } catch (IOException e1) {

                e1.printStackTrace();
            }
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        Button employee = new Button("Employee");
        employee.setLayoutX(100);
        employee.setLayoutY(100);
        employee.setOnAction(e -> {
            primaryStage.close();
            Scene scene = new Scene(new Group());
            primaryStage.setTitle("Employee");
            primaryStage.setWidth(1150);
            primaryStage.setHeight(600);
            ((Group) scene.getRoot()).getChildren().addAll(employeeTable());
            primaryStage.setScene(scene);
            primaryStage.show();

        });
        Button faculty = new Button("Faculty");
        faculty.setLayoutX(100);
        faculty.setLayoutY(150);
        faculty.setOnAction(e -> {
            Scene scene = new Scene(new Group());
            primaryStage.setTitle("Faculty");
            primaryStage.setWidth(1430);
            primaryStage.setHeight(600);
            ((Group) scene.getRoot()).getChildren().addAll(facultyTable());
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        Button postgraduate = new Button("Postgraduate");
        postgraduate.setLayoutX(100);
        postgraduate.setLayoutY(200);
        postgraduate.setOnAction(e -> {

            primaryStage.close();
            Scene scene = new Scene(new Group());
            primaryStage.setTitle("Postgraduate");
            primaryStage.setWidth(1150);
            primaryStage.setHeight(600);
            ((Group) scene.getRoot()).getChildren().addAll(postgraduateTable());
            primaryStage.setScene(scene);
            primaryStage.show();

        });
        Button staff = new Button("Staff");
        staff.setLayoutX(100);
        staff.setLayoutY(250);
        staff.setOnAction(e -> {
            primaryStage.close();
            Scene scene = new Scene(new Group());
            primaryStage.setTitle("Staff");
            primaryStage.setWidth(1300);
            primaryStage.setHeight(600);
            ((Group) scene.getRoot()).getChildren().addAll(staffTable());
            primaryStage.setScene(scene);
            primaryStage.show();
        });


        Button student = new Button("Student");
        student.setLayoutX(100);
        student.setLayoutY(300);
        student.setOnAction(e -> {
            primaryStage.close();
            Scene scene = new Scene(new Group());
            primaryStage.setTitle("Student");
            primaryStage.setWidth(850);
            primaryStage.setHeight(600);
            ((Group) scene.getRoot()).getChildren().addAll(studentTable());
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        Text text = new Text("Personnel Information Management System");
        text.setLayoutX(10);
        text.setLayoutY(30);
        text.setStyle(STYLESHEET_CASPIAN);

        pane.getChildren().addAll(person, staff, employee, faculty, postgraduate, student, text);
        Scene scene = new Scene(pane);
        primaryStage.setWidth(300);
        primaryStage.setHeight(400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public VBox PersonTable() throws IOException {
        /*if (!file.exists()) {
            file.createNewFile();
        }*/
        /*czy添加，创建文件begin*/
        FileUtil.createFile(file);
        /*czy添加，创建文件end*/
        final HBox add = new HBox();

        final Label label = new Label("Management");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        TableColumn<Person, CheckBox> checklist = new TableColumn<Person, CheckBox>("delete");
        checklist.setCellValueFactory(cellDate -> cellDate.getValue().cb.getCheckBox());
        checklist.setMinWidth(50);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("Name"));
        nameCol.setMinWidth(100);
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
            }
        });

        TableColumn address = new TableColumn("Address");
        address.setCellValueFactory(new PropertyValueFactory<Person, String>("Address"));
        address.setMinWidth(100);
        address.setCellFactory(cellFactory);
        address.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setAddress(t.getNewValue());
            }
        });

        TableColumn telephoneNumber = new TableColumn("TelephoneNumber");
        telephoneNumber.setCellValueFactory(new PropertyValueFactory<Person, String>("TelephoneNumber"));
        telephoneNumber.setMinWidth(200);
        telephoneNumber.setCellFactory(cellFactory);
        telephoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setTelephoneNumber(t.getNewValue());
            }
        });

        TableColumn email = new TableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Person, String>("Email"));
        email.setMinWidth(150);
        email.setCellFactory(cellFactory);
        email.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
            }
        });

        table.setItems(data);
        table.getColumns().addAll(nameCol, address, telephoneNumber, email, checklist);

        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMaxWidth(nameCol.getPrefWidth());

        final TextField addAddress = new TextField();
        addAddress.setMaxWidth(address.getPrefWidth());
        addAddress.setPromptText("Address");

        final TextField addTelephoneNumber = new TextField();
        addTelephoneNumber.setMaxWidth(address.getPrefWidth());
        addTelephoneNumber.setPromptText("TelephoneNumber");

        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(email.getPrefWidth());
        addEmail.setPromptText("Email");

        final Button addButton = new Button("Add");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Person p = new Person(addName.getText(), addAddress.getText(), addTelephoneNumber.getText(),
                        addEmail.getText());
                data.add(p);
                /*czy添加把person对象转成字符串添加到文件末尾begin*/

                //person属性没有赋值成功，这里仅测试
                p.setName("czy");
                p.setEmail("1234");
                FileUtil.appendLine(file, JsonUtil.model2Str(p));
                /*czy添加把person对象转成字符串添加到文件末尾end*/
                /*try {

                    p.writeToFile(file);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/
                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
            }
        });

        final Button addButton1 = new Button("Delete");
        addButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).cb.isSelected()) {
                        data.remove(i);
                    }
                }

                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
            }
        });

        final Button addButton2 = new Button("Search");
        addButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                /*czy添加读取文件中的数据begin*/
                var personList = new ArrayList<Person>();
                FileUtil.readFileLine(file).forEach(personText-> personList.add(JsonUtil.str2Model(personText,Person.class)));
                personList.forEach(person -> System.out.println(person));
                /*czy添加读取文件中的数据end*/
                for (int i = 0; i < data.size(); i++) {
                    if ((data.get(i).getName() == addName.getText())
                            || (data.get(i).getName() == addAddress.getText())
                            || (data.get(i).getName() == addTelephoneNumber.getText())
                            || (data.get(i).getName() == addAddress.getText())) {

                    }
                }
            }
        });

        add.getChildren().addAll(addName, addAddress, addTelephoneNumber, addEmail, addButton, addButton1,
                addButton2);
        add.setSpacing(7);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, add);


        return vbox;
    }

    public VBox employeeTable() {

        final HBox add = new HBox();

        final Label label = new Label("Management");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        TableColumn<Employee, CheckBox> checklist = new TableColumn<Employee, CheckBox>("delete");
        checklist.setCellValueFactory(cellDate -> cellDate.getValue().cb.getCheckBox());
        checklist.setMinWidth(50);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        nameCol.setMinWidth(100);
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                ((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setName(t.getNewValue());
            }
        });

        TableColumn address = new TableColumn("Address");
        address.setCellValueFactory(new PropertyValueFactory<Person, String>("Address"));
        address.setMinWidth(100);
        address.setCellFactory(cellFactory);
        address.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                ((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setAddress(t.getNewValue());
            }
        });

        TableColumn telephoneNumber = new TableColumn("TelephoneNumber");
        telephoneNumber.setCellValueFactory(new PropertyValueFactory<Person, String>("TelephoneNumber"));
        telephoneNumber.setMinWidth(200);
        telephoneNumber.setCellFactory(cellFactory);
        telephoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                ((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setTelephoneNumber(t.getNewValue());
            }
        });

        TableColumn email = new TableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Employee, String>("Email"));
        email.setMinWidth(150);
        email.setCellFactory(cellFactory);
        email.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                ((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setEmail(t.getNewValue());
            }
        });
        TableColumn office = new TableColumn("Office");
        office.setCellValueFactory(new PropertyValueFactory<Employee, String>("Office"));
        office.setMinWidth(150);
        office.setCellFactory(cellFactory);
        office.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                ((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setOffice(t.getNewValue());
            }
        });
        TableColumn wages = new TableColumn("Wages");
        wages.setCellValueFactory(new PropertyValueFactory<Employee, String>("Wages"));
        wages.setMinWidth(150);
        wages.setCellFactory(cellFactory);
        wages.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                ((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setWages(t.getNewValue());
            }
        });

        TableColumn date = new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory<Employee, String>("Date"));
        date.setMinWidth(150);
        date.setCellFactory(cellFactory);
        date.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                ((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setDateOfAppointment(t.getNewValue());
            }
        });

        table2.setItems(data2);
        table2.getColumns().addAll(nameCol, address, telephoneNumber, email, office, wages, date, checklist);

        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMaxWidth(nameCol.getPrefWidth());

        final TextField addAddress = new TextField();
        addAddress.setMaxWidth(address.getPrefWidth());
        addAddress.setPromptText("Address");

        final TextField addTelephoneNumber = new TextField();
        addTelephoneNumber.setMaxWidth(address.getPrefWidth());
        addTelephoneNumber.setPromptText("TelephoneNumber");

        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(email.getPrefWidth());
        addEmail.setPromptText("Email");

        final TextField addOffice = new TextField();
        addOffice.setPromptText("Office");
        addOffice.setMaxWidth(nameCol.getPrefWidth());

        final TextField addWages = new TextField();
        addWages.setPromptText("Wages");
        addWages.setMaxWidth(nameCol.getPrefWidth());

        final TextField addDate = new TextField();
        addDate.setPromptText("Date");
        addDate.setMaxWidth(nameCol.getPrefWidth());

        final Button addButton = new Button("Add");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data2.add(new Employee(addOffice.getText(), addWages.getText(), addDate.getText(),
                        addName.getText(), addAddress.getText(), addTelephoneNumber.getText(), addEmail.getText()));
                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addOffice.clear();
                addWages.clear();
                addDate.clear();
            }
        });

        final Button addButton1 = new Button("Delete");
        addButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data2.size(); i++) {
                    if (data2.get(i).cb.isSelected()) {
                        data2.remove(i);
                    }
                }

                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addOffice.clear();
                addWages.clear();
                addDate.clear();
            }
        });

        final Button addButton2 = new Button("Search");
        addButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data.size(); i++) {
                    if ((data.get(i).getName() == addName.getText())
                            || (data.get(i).getName() == addAddress.getText())
                            || (data.get(i).getName() == addTelephoneNumber.getText())
                            || (data.get(i).getName() == addAddress.getText())) {

                    }
                }
            }
        });

        add.getChildren().addAll(addName, addAddress, addTelephoneNumber, addEmail, addOffice, addWages, addDate,
                addButton, addButton1, addButton2);
        add.setSpacing(7);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table2, add);


        return vbox;

    }

    public VBox facultyTable() {


        final HBox add = new HBox();

        final Label label = new Label("Management");
        label.setFont(new Font("Arial", 20));

        table3.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        TableColumn<Faculty, CheckBox> checklist = new TableColumn<Faculty, CheckBox>("delete");
        checklist.setCellValueFactory(cellDate -> cellDate.getValue().cb.getCheckBox());
        checklist.setMinWidth(50);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Faculty, String>("Name"));
        nameCol.setMinWidth(100);
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Faculty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Faculty, String> t) {
                ((Faculty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
            }
        });

        TableColumn address = new TableColumn("Address");
        address.setCellValueFactory(new PropertyValueFactory<Faculty, String>("Address"));
        address.setMinWidth(100);
        address.setCellFactory(cellFactory);
        address.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Faculty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Faculty, String> t) {
                ((Faculty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setAddress(t.getNewValue());
            }
        });

        TableColumn telephoneNumber = new TableColumn("TelephoneNumber");
        telephoneNumber.setCellValueFactory(new PropertyValueFactory<Faculty, String>("TelephoneNumber"));
        telephoneNumber.setMinWidth(200);
        telephoneNumber.setCellFactory(cellFactory);
        telephoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Faculty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Faculty, String> t) {
                ((Faculty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setTelephoneNumber(t.getNewValue());
            }
        });

        TableColumn email = new TableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Faculty, String>("Email"));
        email.setMinWidth(150);
        email.setCellFactory(cellFactory);
        email.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Faculty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Faculty, String> t) {
                ((Faculty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setEmail(t.getNewValue());
            }
        });
        TableColumn office = new TableColumn("Office");
        office.setCellValueFactory(new PropertyValueFactory<Faculty, String>("Office"));
        office.setMinWidth(150);
        office.setCellFactory(cellFactory);
        office.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Faculty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Faculty, String> t) {
                ((Faculty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setOffice(t.getNewValue());
            }
        });
        TableColumn wages = new TableColumn("Wages");
        wages.setCellValueFactory(new PropertyValueFactory<Faculty, String>("Wages"));
        wages.setMinWidth(150);
        wages.setCellFactory(cellFactory);
        wages.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Faculty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Faculty, String> t) {
                ((Faculty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setWages(t.getNewValue());
            }
        });

        TableColumn date = new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory<Faculty, String>("Date"));
        date.setMinWidth(150);
        date.setCellFactory(cellFactory);
        date.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Faculty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Faculty, String> t) {
                ((Faculty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setDateOfAppointment(t.getNewValue());
            }
        });

        TableColumn officeHours = new TableColumn("Office Hours");
        officeHours.setCellValueFactory(new PropertyValueFactory<Faculty, String>("Office Hours"));
        officeHours.setMinWidth(150);
        officeHours.setCellFactory(cellFactory);
        date.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Faculty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Faculty, String> t) {
                ((Faculty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setOfficeHours(t.getNewValue());
            }
        });

        TableColumn level = new TableColumn("Level");
        level.setCellValueFactory(new PropertyValueFactory<Faculty, String>("Level"));
        level.setMinWidth(150);
        level.setCellFactory(cellFactory);
        level.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Faculty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Faculty, String> t) {
                ((Faculty) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setLevel(t.getNewValue());
            }
        });
        table3.setItems(data3);
        table3.getColumns().addAll(nameCol, address, telephoneNumber, email, office, wages, date, officeHours,
                level, checklist);

        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMaxWidth(nameCol.getPrefWidth());

        final TextField addAddress = new TextField();
        addAddress.setMaxWidth(address.getPrefWidth());
        addAddress.setPromptText("Address");

        final TextField addTelephoneNumber = new TextField();
        addTelephoneNumber.setMaxWidth(address.getPrefWidth());
        addTelephoneNumber.setPromptText("TelephoneNumber");

        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(email.getPrefWidth());
        addEmail.setPromptText("Email");

        final TextField addOffice = new TextField();
        addOffice.setPromptText("Office");
        addOffice.setMaxWidth(nameCol.getPrefWidth());

        final TextField addWages = new TextField();
        addWages.setPromptText("Wages");
        addWages.setMaxWidth(nameCol.getPrefWidth());

        final TextField addDate = new TextField();
        addDate.setPromptText("Date");
        addWages.setMaxWidth(nameCol.getPrefWidth());

        final TextField addOfficeHours = new TextField();
        addOfficeHours.setPromptText("OfficeHours");
        addOfficeHours.setMaxWidth(nameCol.getPrefWidth());

        final TextField addLevel = new TextField();
        addLevel.setPromptText("Level");
        addLevel.setMaxWidth(nameCol.getPrefWidth());

        final Button addButton = new Button("Add");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
//				Employee p = new Employee(addOffice.getText(),addWages.getText(), addDate.getText(),addName.getText(), addAddress.getText(), addTelephoneNumber.getText(),
//						addEmail.getText());
                data3.add(new Faculty(addOfficeHours.getText(), addLevel.getText(), addOffice.getText(),
                        addWages.getText(), addDate.getText(), addName.getText(), addAddress.getText(),
                        addTelephoneNumber.getText(), addEmail.getText()));
//				try {
//					//output.writeObject(p);
//					System.out.println("success");
//					} catch (IOException e1) {
//
//					e1.printStackTrace();
//				}

                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addOffice.clear();
                addWages.clear();
                addDate.clear();
                addOfficeHours.clear();
                addLevel.clear();
            }
        });

        final Button addButton1 = new Button("Delete");
        addButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data3.size(); i++) {
                    if (data3.get(i).cb.isSelected()) {
                        data3.remove(i);
                    }
                }

                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addOffice.clear();
                addWages.clear();
                addDate.clear();
                addOfficeHours.clear();
                addLevel.clear();
            }
        });

        final Button addButton2 = new Button("Search");
        addButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data.size(); i++) {
                    if ((data.get(i).getName() == addName.getText())
                            || (data.get(i).getName() == addAddress.getText())
                            || (data.get(i).getName() == addTelephoneNumber.getText())
                            || (data.get(i).getName() == addAddress.getText())) {

                    }
                }
            }
        });

        add.getChildren().addAll(addName, addAddress, addTelephoneNumber, addEmail, addOffice, addWages, addDate,
                addOfficeHours, addLevel, addButton, addButton1, addButton2);
        add.setSpacing(7);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table3, add);

        return vbox;

    }

    public VBox postgraduateTable() {


        final HBox add = new HBox();

        final Label label = new Label("Management");
        label.setFont(new Font("Arial", 20));

        table5.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        TableColumn<Postgraduate, CheckBox> checklist = new TableColumn<Postgraduate, CheckBox>("delete");
        checklist.setCellValueFactory(cellDate -> cellDate.getValue().cb.getCheckBox());
        checklist.setMinWidth(50);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Postgraduate, String>("Name"));
        nameCol.setMinWidth(100);
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Postgraduate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Postgraduate, String> t) {
                ((Postgraduate) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
            }
        });

        TableColumn address = new TableColumn("Address");
        address.setCellValueFactory(new PropertyValueFactory<Postgraduate, String>("Address"));
        address.setMinWidth(100);
        address.setCellFactory(cellFactory);
        address.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Postgraduate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Postgraduate, String> t) {
                ((Postgraduate) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setAddress(t.getNewValue());
            }
        });

        TableColumn telephoneNumber = new TableColumn("TelephoneNumber");
        telephoneNumber.setCellValueFactory(new PropertyValueFactory<Postgraduate, String>("TelephoneNumber"));
        telephoneNumber.setMinWidth(200);
        telephoneNumber.setCellFactory(cellFactory);
        telephoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Postgraduate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Postgraduate, String> t) {
                ((Postgraduate) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setTelephoneNumber(t.getNewValue());
            }
        });

        TableColumn email = new TableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Postgraduate, String>("Email"));
        email.setMinWidth(150);
        email.setCellFactory(cellFactory);
        email.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Postgraduate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Postgraduate, String> t) {
                ((Postgraduate) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setEmail(t.getNewValue());
            }
        });

        TableColumn classStatus = new TableColumn("ClassStatus");
        classStatus.setCellValueFactory(new PropertyValueFactory<Postgraduate, String>("ClassStatus"));
        classStatus.setMinWidth(150);
        classStatus.setCellFactory(cellFactory);
        classStatus.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Postgraduate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Postgraduate, String> t) {
                ((Postgraduate) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setClassStatus(t.getNewValue());
            }
        });


        TableColumn direction = new TableColumn("Direction");
        direction.setCellValueFactory(new PropertyValueFactory<Postgraduate, String>("Direction"));
        direction.setMinWidth(150);
        direction.setCellFactory(cellFactory);
        direction.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Postgraduate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Postgraduate, String> t) {
                ((Postgraduate) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setDirection(t.getNewValue());
            }
        });

        TableColumn mentor = new TableColumn("Mentor");
        mentor.setCellValueFactory(new PropertyValueFactory<Postgraduate, String>("Mentor"));
        mentor.setMinWidth(150);
        mentor.setCellFactory(cellFactory);
        mentor.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Postgraduate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Postgraduate, String> t) {
                ((Postgraduate) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setMentor(t.getNewValue());
            }
        });


        table5.setItems(data5);
        table5.getColumns().addAll(nameCol, address, telephoneNumber, email, classStatus, direction, mentor, checklist);

        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMaxWidth(nameCol.getPrefWidth());

        final TextField addAddress = new TextField();
        addAddress.setMaxWidth(address.getPrefWidth());
        addAddress.setPromptText("Address");

        final TextField addTelephoneNumber = new TextField();
        addTelephoneNumber.setMaxWidth(telephoneNumber.getPrefWidth());
        addTelephoneNumber.setPromptText("TelephoneNumber");

        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(email.getPrefWidth());
        addEmail.setPromptText("Email");

        final TextField addClassStatus = new TextField();
        addClassStatus.setMaxWidth(classStatus.getPrefWidth());
        addClassStatus.setPromptText("Class Status");

        final TextField addDirection = new TextField();
        addDirection.setMaxWidth(direction.getPrefWidth());
        addDirection.setPromptText("Direction");

        final TextField addMentor = new TextField();
        addMentor.setMaxWidth(mentor.getPrefWidth());
        addMentor.setPromptText("Mentor");


        final Button addButton = new Button("Add");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
//				Person p = new Person(addName.getText(), addAddress.getText(), addTelephoneNumber.getText(),
//						addEmail.getText());
                data5.add(new Postgraduate(addDirection.getText(), addMentor.getText(), addClassStatus.getText(), addName.getText(), addAddress.getText(),
                        addTelephoneNumber.getText(), addEmail.getText()));
//				try {
//					//output.writeObject(p);
//					System.out.println("success");
//					} catch (IOException e1) {
//
//					e1.printStackTrace();
//				}
                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addClassStatus.clear();
                addDirection.clear();
                addMentor.clear();
            }
        });

        final Button addButton1 = new Button("Delete");
        addButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data5.size(); i++) {
                    if (data5.get(i).cb.isSelected()) {
                        data5.remove(i);
                    }
                }

                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addClassStatus.clear();
                addDirection.clear();
                addMentor.clear();
            }
        });

        final Button addButton2 = new Button("Search");
        addButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data.size(); i++) {
                    if ((data.get(i).getName() == addName.getText())
                            || (data.get(i).getName() == addAddress.getText())
                            || (data.get(i).getName() == addTelephoneNumber.getText())
                            || (data.get(i).getName() == addAddress.getText())) {

                    }
                }
            }
        });

        add.getChildren().addAll(addName, addAddress, addTelephoneNumber, addEmail, addClassStatus, addDirection, addMentor, addButton,
                addButton1, addButton2);
        add.setSpacing(7);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table5, add);

        return vbox;

    }

    public VBox staffTable() {


        final HBox add = new HBox();

        final Label label = new Label("Management");
        label.setFont(new Font("Arial", 20));

        table1.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        TableColumn<Staff, CheckBox> checklist = new TableColumn<Staff, CheckBox>("delete");
        checklist.setCellValueFactory(cellDate -> cellDate.getValue().cb.getCheckBox());
        checklist.setMinWidth(50);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Staff, String>("Name"));
        nameCol.setMinWidth(100);
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Staff, String> t) {
                ((Staff) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setName(t.getNewValue());
            }
        });

        TableColumn address = new TableColumn("Address");
        address.setCellValueFactory(new PropertyValueFactory<Staff, String>("Address"));
        address.setMinWidth(100);
        address.setCellFactory(cellFactory);
        address.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Staff, String> t) {
                ((Staff) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setAddress(t.getNewValue());
            }
        });

        TableColumn telephoneNumber = new TableColumn("TelephoneNumber");
        telephoneNumber.setCellValueFactory(new PropertyValueFactory<Staff, String>("TelephoneNumber"));
        telephoneNumber.setMinWidth(200);
        telephoneNumber.setCellFactory(cellFactory);
        telephoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Staff, String> t) {
                ((Staff) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setTelephoneNumber(t.getNewValue());
            }
        });

        TableColumn email = new TableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Staff, String>("Email"));
        email.setMinWidth(150);
        email.setCellFactory(cellFactory);
        email.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Staff, String> t) {
                ((Staff) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setEmail(t.getNewValue());
            }
        });
        TableColumn office = new TableColumn("Office");
        office.setCellValueFactory(new PropertyValueFactory<Staff, String>("Office"));
        office.setMinWidth(150);
        office.setCellFactory(cellFactory);
        office.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Staff, String> t) {
                ((Staff) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setOffice(t.getNewValue());
            }
        });
        TableColumn wages = new TableColumn("Wages");
        wages.setCellValueFactory(new PropertyValueFactory<Staff, String>("Wages"));
        wages.setMinWidth(150);
        wages.setCellFactory(cellFactory);
        wages.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Staff, String> t) {
                ((Staff) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setWages(t.getNewValue());
            }
        });

        TableColumn date = new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory<Staff, String>("Date"));
        date.setMinWidth(150);
        date.setCellFactory(cellFactory);
        date.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Staff, String> t) {
                ((Staff) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setDateOfAppointment(t.getNewValue());
            }
        });

        TableColumn jobTitle = new TableColumn("JobTitle");
        jobTitle.setCellValueFactory(new PropertyValueFactory<Staff, String>("JobTitle"));
        jobTitle.setMinWidth(150);
        jobTitle.setCellFactory(cellFactory);
        jobTitle.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Staff, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Staff, String> t) {
                ((Staff) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setJobTitle(t.getNewValue());
            }
        });


        table1.setItems(data1);
        table1.getColumns().addAll(nameCol, address, telephoneNumber, email, office, wages, date, jobTitle, checklist);

        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMaxWidth(nameCol.getPrefWidth());

        final TextField addAddress = new TextField();
        addAddress.setMaxWidth(address.getPrefWidth());
        addAddress.setPromptText("Address");

        final TextField addTelephoneNumber = new TextField();
        addTelephoneNumber.setMaxWidth(address.getPrefWidth());
        addTelephoneNumber.setPromptText("TelephoneNumber");

        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(email.getPrefWidth());
        addEmail.setPromptText("Email");

        final TextField addOffice = new TextField();
        addOffice.setPromptText("Office");
        addOffice.setMaxWidth(nameCol.getPrefWidth());

        final TextField addWages = new TextField();
        addWages.setPromptText("Wages");
        addWages.setMaxWidth(nameCol.getPrefWidth());

        final TextField addDate = new TextField();
        addDate.setPromptText("Date");
        addWages.setMaxWidth(nameCol.getPrefWidth());

        final TextField addjobTitle = new TextField();
        addjobTitle.setPromptText("JobTitle");
        addjobTitle.setMaxWidth(nameCol.getPrefWidth());

        final Button addButton = new Button("Add");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
//				Employee p = new Employee(addOffice.getText(),addWages.getText(), addDate.getText(),addName.getText(), addAddress.getText(), addTelephoneNumber.getText(),
//						addEmail.getText());
                data1.add(new Staff(addjobTitle.getText(), addOffice.getText(), addWages.getText(), addDate.getText(),
                        addName.getText(), addAddress.getText(), addTelephoneNumber.getText(), addEmail.getText()));
//				try {
//					//output.writeObject(p);
//					System.out.println("success");
//					} catch (IOException e1) {
//
//					e1.printStackTrace();
//				}

                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addOffice.clear();
                addWages.clear();
                addDate.clear();
                addjobTitle.clear();
            }
        });

        final Button addButton1 = new Button("Delete");
        addButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data1.size(); i++) {
                    if (data1.get(i).cb.isSelected()) {
                        data1.remove(i);
                    }
                }

                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addOffice.clear();
                addWages.clear();
                addDate.clear();
                addjobTitle.clear();
            }
        });

        final Button addButton2 = new Button("Search");
        addButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data.size(); i++) {
                    if ((data.get(i).getName() == addName.getText())
                            || (data.get(i).getName() == addAddress.getText())
                            || (data.get(i).getName() == addTelephoneNumber.getText())
                            || (data.get(i).getName() == addAddress.getText())) {

                    }
                }
            }
        });

        add.getChildren().addAll(addName, addAddress, addTelephoneNumber, addEmail, addOffice, addWages, addDate,
                addjobTitle, addButton, addButton1, addButton2);
        add.setSpacing(7);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table1, add);

        return vbox;

    }

    public VBox studentTable() {


        final HBox add = new HBox();

        final Label label = new Label("Management");
        label.setFont(new Font("Arial", 20));

        table4.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        TableColumn<Student, CheckBox> checklist = new TableColumn<Student, CheckBox>("delete");
        checklist.setCellValueFactory(cellDate -> cellDate.getValue().cb.getCheckBox());
        checklist.setMinWidth(50);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
        nameCol.setMinWidth(100);
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> t) {
                ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
            }
        });

        TableColumn address = new TableColumn("Address");
        address.setCellValueFactory(new PropertyValueFactory<Student, String>("Address"));
        address.setMinWidth(100);
        address.setCellFactory(cellFactory);
        address.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> t) {
                ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setAddress(t.getNewValue());
            }
        });

        TableColumn telephoneNumber = new TableColumn("TelephoneNumber");
        telephoneNumber.setCellValueFactory(new PropertyValueFactory<Student, String>("TelephoneNumber"));
        telephoneNumber.setMinWidth(200);
        telephoneNumber.setCellFactory(cellFactory);
        telephoneNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> t) {
                ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setTelephoneNumber(t.getNewValue());
            }
        });

        TableColumn email = new TableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Student, String>("Email"));
        email.setMinWidth(150);
        email.setCellFactory(cellFactory);
        email.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> t) {
                ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setEmail(t.getNewValue());
            }
        });

        TableColumn classStatus = new TableColumn("ClassStatus");
        classStatus.setCellValueFactory(new PropertyValueFactory<Student, String>("ClassStatus"));
        classStatus.setMinWidth(150);
        classStatus.setCellFactory(cellFactory);
        classStatus.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> t) {
                ((Student) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setClassStatus(t.getNewValue());
            }
        });

        table4.setItems(data4);
        table4.getColumns().addAll(nameCol, address, telephoneNumber, email, classStatus, checklist);

        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMaxWidth(nameCol.getPrefWidth());

        final TextField addAddress = new TextField();
        addAddress.setMaxWidth(address.getPrefWidth());
        addAddress.setPromptText("Address");

        final TextField addTelephoneNumber = new TextField();
        addTelephoneNumber.setMaxWidth(address.getPrefWidth());
        addTelephoneNumber.setPromptText("TelephoneNumber");

        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(email.getPrefWidth());
        addEmail.setPromptText("Email");

        final TextField addClassStatus = new TextField();
        addClassStatus.setMaxWidth(email.getPrefWidth());
        addClassStatus.setPromptText("Class Status");

        final Button addButton = new Button("Add");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data4.add(new Student(addClassStatus.getText(), addName.getText(), addAddress.getText(),
                        addTelephoneNumber.getText(), addEmail.getText()));
                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addClassStatus.clear();
            }
        });

        final Button addButton1 = new Button("Delete");
        addButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                for (int i = 0; i < data4.size(); i++) {
                    if (data4.get(i).cb.isSelected()) {
                        data4.remove(i);
                    }
                }

                addName.clear();
                addAddress.clear();
                addTelephoneNumber.clear();
                addEmail.clear();
                addClassStatus.clear();
            }
        });

        final Button addButton2 = new Button("Search");


        add.getChildren().addAll(addName, addAddress, addTelephoneNumber, addEmail, addClassStatus, addButton,
                addButton1, addButton2);
        add.setSpacing(7);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table4, add);


        return vbox;

    }




    public class Postgraduate extends Student implements Serializable {
        private static final long serialVersionUID = 1L;

        private final SimpleStringProperty direction;

        private final SimpleStringProperty mentor;
        private Checkbox cb = new Checkbox();

        public Postgraduate(String direction, String mentor, String classStatus, String name, String address,
                            String telephoneNumber, String email) {
            super(classStatus, name, address, telephoneNumber, email);
            this.direction = new SimpleStringProperty(direction);
            this.mentor = new SimpleStringProperty(mentor);
        }

        public String getDirection() {
            return direction.get();
        }

        public void setDirection(String direction) {
            this.direction.set(direction);
        }

        public String getMentor() {
            return mentor.get();
        }

        public void setMentor(String mentor) {
            this.mentor.set(mentor);
        }

        public String toString() {
            return "Postgraduate " + this.getName();
        }
    }

    public class Staff extends Employee implements Serializable {
        private static final long serialVersionUID = 1L;
        private final SimpleStringProperty jobTitle;
        private Checkbox cb = new Checkbox();

        public Staff(String jobTitle, String office, String wages, String dateOfAppointment, String name,
                     String address, String telephoneNumber, String email) {
            super(office, wages, dateOfAppointment, name, address, telephoneNumber, email);
            this.jobTitle = new SimpleStringProperty(jobTitle);
        }

        public String getJobTitle() {
            return jobTitle.get();
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle.set(jobTitle);
        }

        public String toString() {
            return "Staff " + this.getName();
        }

    }

    public class Student extends Person implements Serializable {
        private static final long serialVersionUID = 1L;
        private final SimpleStringProperty classStatus;
        private static final String FRRESHMAN = "frreshman";
        private static final String SOPHOMORE = "spohomore";
        private static final String JUNIOR = "junior";
        private static final String SENIOR = "senior";
        private Checkbox cb = new Checkbox();

        public Student(String classStatus, String name, String address, String telephoneNumber, String email) {
            super(name, address, telephoneNumber, email);
            this.classStatus = new SimpleStringProperty(classStatus);
        }

        public String getClassStatus() {
            return classStatus.get();
        }

        public void setClassStatus(String classStatus) {
            this.classStatus.set(classStatus);
        }

        public String toString() {
            return "Student " + this.getName();

        }

    }
}