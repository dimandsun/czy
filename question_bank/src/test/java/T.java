import com.czy.core.ProjectContainer;
import com.czy.core.annotation.db.Table;
import com.czy.core.util.TableUtil;
import com.czy.jdbc.pool.DataSourceFactory;
import com.czy.question.dao.IQuestionDao;
import com.czy.question.model.table.Question;
import com.czy.util.sqltool.SQLUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

/**
 * @author chenzy
 * @date 2020-06-22
 */
public class T {

    @Test public void creatBean() {
        SQLUtil.generateBeanFile("com.czy.question.server.model.table", "sql.sql", "chenzy");
    }
    @Test public void byteTest(){
        byte b = 111;
        int i = b;
    }

    @Test public void testJDBC(){
//        DataSourceFactory.reloadSetting("jdbc","jdbc.yml");
        var projectContainer=ProjectContainer.getInstance();
        projectContainer.initProject();
        IQuestionDao questionDao= (IQuestionDao) projectContainer.getBeanMap().get("questionDao").getBean();
        var question=new Question();
        question.setName("adsfasd");
        var tableName=TableUtil.getTableName(question.getClass());
        var id=questionDao.insert(tableName,question);
        System.out.println(id);
    }
}
