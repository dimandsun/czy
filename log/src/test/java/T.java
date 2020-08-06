import com.czy.log00.LogFactory;
import org.junit.Test;

/**
 * @author chenzy
 * @date 2020-07-24
 */
public class T {

    @Test public void testLog(){
        var log=LogFactory.getLog("test");
        log.info("adasdf");
        log.info("去你没得");
        log.info("浅谈JAVA中的日志文件 - CSDN博客");

    }
}
