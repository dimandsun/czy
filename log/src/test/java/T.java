import com.czy.javaLog.LogFactory;
import com.czy.util.io.FileUtil;
import com.czy.util.text.StringUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

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

    @Test
    public void te() {
        System.out.println(StringUtil.subStr("%d{mmssSSS} [%thread] [%level]: %msg%n","%d\\{(.*)\\}"));
    }

    @Test
    public void name() throws IOException {
        System.out.println(FileUtil.getRoot());
    }
}
