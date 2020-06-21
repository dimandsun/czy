import com.czy.util.model.StringMap;
import org.junit.Test;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

/**
 * @author chenzy
 * @description java10新特性
 * @since 2020/6/21
 */
/*
1、类型推断
2、copyOf Map/List/Set.copyOf(obj);得到不可做任何修改的集合
3、 ByteArrayOutputStream对象加toString(charset)方法，用于指定流中数据的编码
4、PrintWriter/PrintWriter/Formatter/Scanner 新增三个构造方法，需要额外的参数charset
5、reader.transferTo(writer); reader中的的数据放到writer流中 若文件非utf-8模式，可能会乱码
*/
public class Java10NewTest {


    @Test
    public void copyOfTest() {
        var map = new StringMap<String>("sb", "爱的");
        var copyMap = Map.copyOf(map);
        System.out.println(map);
        System.out.println(copyMap);
        copyMap.put("a", "as");//报错
    }

    @Test
    public void toStringTest() throws UnsupportedEncodingException {
        var charset = "gbk";
        var s = "黑恶和";
        var in = new ByteArrayInputStream(s.getBytes(charset));

        var out = new ByteArrayOutputStream();
        int i = 0;
        while ((i = in.read()) != -1) {
            out.write(i);
        }
        System.out.println(out.toString());

        System.out.println(out.toString(charset));
    }

    @Test
    public void printStreamTest() throws FileNotFoundException, UnsupportedEncodingException {
        var charset = "gbk";
        var printStream = new PrintStream("a.text", charset);
        printStream.println("爱德华就开始打翻了");
        printStream.flush();
        printStream.close();
        return;
    }

    //gbk模式乱码
    @Test
    public void transferTo() throws IOException {
        var charset = "gbk";
        var reader = new BufferedReader(new InputStreamReader(new FileInputStream("a.text")));
        var writer = new PrintWriter("b.text",charset);
        reader.transferTo(writer);
        writer.flush();
        writer.close();
        reader.close();
    }
    @Test public void scannerTest() throws FileNotFoundException {
        var charset = "gbk";
       var scanner= new Scanner(new FileInputStream("aa.text"),charset);
        scanner.useDelimiter(",");
        while (scanner.hasNext()){
            System.out.println(scanner.nextLine());
//            scanner.next();//默认以空格分隔 useDelimiter自定义分隔符
        }
        scanner.close();
    }

}
