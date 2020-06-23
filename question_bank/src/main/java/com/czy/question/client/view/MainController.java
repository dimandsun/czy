package com.czy.question.client.view;

import com.czy.question.server.model.enums.QuestionTypeEnum;
import com.czy.question.server.model.table.Question;
import com.czy.question.server.model.table.Subject;
import com.czy.util.ObjectUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;

/**
 * @author chenzy
 * @since 2020/6/23
 * 若Application类作为控制器，JavaFX将创建应用程序类的新实例，并将新实例用作控制器。即会存在两个Application实例，两个实例中属性内容不同，会出现问题
 *
 */
public class MainController {
    @FXML private AnchorPane root;
    @FXML private MenuBar menuBar;
    @FXML private BorderPane borderPane;
    @FXML private TreeView<Subject> leftRoot;
    @FXML private TreeItem<Subject> subjectRoot;
    @FXML private ListView<Question> questionListView;
    @FXML private Button testButton;
    @FXML private void initialize() {
        var question=new Question();
        question.setQuestionType(QuestionTypeEnum.E2C);
        question.setContent("hello,my name is chenzy");
        question.setAnswerContent("你好，我的名字叫chenzy");
        var questionList = FXCollections.observableArrayList(question);
        var subjectList= ObjectUtil.createList(Subject.class,"语文","数学","发展心理学");
        subjectList.forEach(subject -> subjectRoot.getChildren().add(new TreeItem<>(subject)));
        leftRoot.setCellFactory(TextFieldTreeCell.forTreeView(new StringConverter<>() {
            @Override
            public String toString(Subject object) {
                if (object==null){//根节点为空
                    return "题库";
                }
                return object.getName();
            }
            @Override
            public Subject fromString(String string) {
                System.out.println("不会执行fromString");
                return null;
            }
        }));
        //TODO 组件随窗口拉伸而拉伸未实现
//ASCII ISO88591 GB2312 GBK UTF-8
        //渲染题目
        var questionBorder=new Border(new BorderStroke(Paint.valueOf("#FF0000"), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2)));
        var anserBorder=new Border(new BorderStroke(Paint.valueOf("#FF0000"), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2)));
        question.getContent();
        question.getAnswerContent();
        questionListView.setItems(questionList);
        questionListView.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
            @Override
            public ListCell<Question> call(ListView<Question> param) {
                var listCell =new ListCell<Question>(){
                    @Override
                    protected void updateItem(Question item, boolean empty) {
                        super.updateItem(item, empty);
//                        FXUtil.setColor(this, "#ff82ab");
                        if (!empty){
                            var box=new VBox(5);
                            var contentLabel = new Label("Q： "+item.getContent());
                            var answerLabel=new Label("A：  "+item.getAnswerContent());
                            contentLabel.setBorder(questionBorder);
                            answerLabel.setBorder(anserBorder);
                            box.getChildren().addAll(contentLabel,answerLabel);
                            this.setGraphic(box);
                        }
                    }
                };
                return listCell;
            }
        });
        subjectRoot.setExpanded(true);
        leftRoot.setOnMouseClicked(event -> {
            if (MouseButton.SECONDARY.equals(event.getButton())){
                //右键
            }

        });
    }

}
