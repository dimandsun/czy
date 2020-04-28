import com.czy.util.FileUtil;
import org.junit.Test;

/**
 * @author chenzy
 * @description
 * @since 2020-04-28
 */
public class Tes {

    @Test
    public static void a(){
        FileUtil.readFile(FileUtil.getFile("/doc/sql.sql"));
    }
}
