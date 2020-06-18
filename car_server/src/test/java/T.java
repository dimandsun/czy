import com.czy.car_server.model.table.User;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;
import com.czy.util.sqltool.SQLUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author chenzy
 * @since 2020-06-09
 */
public class T {

    @Test
    public void creatBean() {
        SQLUtil.generateBeanFile("com.czy.car_server.model.table", "sql.sql", "chenzy");
    }

    @Test
    public void JsonTest() {
        var map = new StringMap<>("mobile", "123").add("original_ps", "d").add("originalPs", "ads");
        var user = JsonUtil.model2Model(map, User.class);
        System.out.println(user);
    }


    @Test
    public void server() throws IOException {

    }
    @Test
    public void client() throws IOException {

    }

    @Test
    public void aVoid(){
        var scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String s=scanner.next();
            System.out.println(s+"_______________________");
        }
    }

    public static void main(String[] args) {
        var scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String s=scanner.next();
            System.out.println(s+"_______________________");
        }
    }
}
