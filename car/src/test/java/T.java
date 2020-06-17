import com.czy.car.model.table.User;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;
import com.czy.util.sqltool.SQLUtil;
import org.junit.Test;

/**
 * @author chenzy
 * @since 2020-06-09
 */
public class T {

    @Test
    public void creatBean(){
        SQLUtil.generateBeanFile("com.czy.car.model.table","sql.sql","chenzy");
    }

    @Test
    public void JsonTest(){
        var map=new StringMap<>("mobile", "123").add("original_ps", "d").add("originalPs", "ads");
        var user=JsonUtil.model2Model(map, User.class);
        System.out.println(user);
    }
}
