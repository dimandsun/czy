import com.czy.util.text.StringUtil;
import com.czy.util.io.FileUtil;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

/**
 * @author chenzy
 * @since 2020-06-22
 */
public class T {
    @Test
    public void getText() {
        var file = new File("C:\\Users\\Samsung\\Desktop\\北师大网络教育心理测量学离线作业及答案 - 百度文库.html");
        file = new File("C:\\Users\\Samsung\\Desktop\\a.txt");
        var htmlStr = FileUtil.readFile(Optional.ofNullable(file));
        var s = StringUtil.filterHtml(Optional.of(htmlStr));
        System.out.println(s);
    }
}
