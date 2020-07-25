package com.czy.question;

import com.czy.core.ProjectContainer;
import com.czy.core.model.ProjectInfo;
import com.czy.core.util.TableUtil;
import com.czy.question.dao.IQuestionDao;
import com.czy.question.model.table.Question;
import com.czy.util.io.FileUtil;
import com.czy.util.text.StringUtil;

/**
 * @author chenzy
 * @since 2020/7/24
 */
public class Main {
    public static void main(String[] args) {
        ProjectInfo.init(FileUtil.getResourceFile("question_bank","application.yml"));
        var projectContainer= ProjectContainer.getInstance();
        projectContainer.initProject();
        IQuestionDao questionDao= (IQuestionDao) projectContainer.getBeanMap().get("questionDao").getBean();
        var question=new Question();
        question.setName("adsfasd");
        var tableName= TableUtil.getTableName(question.getClass());
        var id=questionDao.insert(tableName,question);
        System.out.println(id);
    }
}
