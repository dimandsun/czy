import com.czy.util.sqltool.SQLUtil;
import org.junit.Test;

/**
 * @author chenzy
 * @since 2020-06-09
 */
public class T {

    @Test
    public void creatBean(){
        SQLUtil.generateBeanFile("com.czy.car.model","sql.sql","chenzy");
    }
}
