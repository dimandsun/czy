import com.czy.util.sqltool.SQLUtil;
import org.junit.Test;

/**
 * @author chenzy
 * @date 2020-07-09
 */
public class T {

    @Test
    public void ge(){
        SQLUtil.generateBeanFile("com.czy.task.model.table", "sql.sql", "task");
    }
}
