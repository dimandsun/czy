package com.czy.question;

import com.czy.core.ProjectContainer;
import com.czy.core.model.ProjectInfo;
import com.czy.question.controller.QuestionController;
import com.czy.question.model.table.Question;
import com.czy.util.io.FileUtilOld;

/**
 * @author chenzy
 * @since 2020/7/24
 */
public class Main {
    public static void main(String[] args) {
        ProjectInfo.init(FileUtilOld.getResourceFile("question_bank","application.yml"));
        var projectContainer= ProjectContainer.getInstance();
        projectContainer.initProject();
        var questionController=(QuestionController)projectContainer.getBeanMap().get("questionController").getBean();
        var question=new Question();
        question.setName("adsfasd");
        var resultVO=questionController.insert(question);
        System.out.println(resultVO);
    }
}
