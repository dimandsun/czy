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
}