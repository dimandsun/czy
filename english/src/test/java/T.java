import com.czy.util.FileUtil;
import org.junit.Test;

/**
 * @author chenzy
 * @description
 * @since 2020-04-28
 */
public class T {
    @Test
    public void a(){
        String fieleConten =FileUtil.readFile(FileUtil.getFile("doc/sql.sql"));
        System.out.println(fieleConten);
    }
}
