import com.czy.core.enums.ActiveEnum;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;

/**
 * @author chenzy
 * 
 * @since 2020/6/21
 */

/*
jvm两种模式：编译模式、解释模式

 */
public class java12NewTest {
    @Test public void switchTest(){
        var a= ActiveEnum.dev;
        switch (a){
            case dev-> System.out.println("ad");
            case pro,test-> System.out.println("asdasd");
            default -> System.out.println(123);
        }
    }
    @Test public void stringTest(){
        var optionalS="ads撒旦".describeConstable();
        System.out.println(optionalS);

        var s="ads";
        var transform= "asfd".transform(str ->123);
        System.out.println(transform);
        "".strip();//可以去除特殊的空格
        System.out.println("asdf".indent(2));//indent 每行前加空格
    }

    @Test public void FilesTest() throws IOException {

        {
            var writer=new FileWriter("a.text");
            writer.write("a");
            writer.write("b");
            writer.write("c");
            writer.close();
        }
        {
            var writer=new FileWriter("b.text");
            writer.write("a");
            writer.write("1");
            writer.write("c");
            writer.close();
        }
        //查找并返回内容中第一个不匹配字节的位置
        var mismatch=Files.mismatch(Path.of("a.text"),Path.of("b.text"));
        System.out.println(mismatch);
    }
    //数字格式化
    @Test public void numberTest(){
        var format=NumberFormat.getCompactNumberInstance(Locale.US,NumberFormat.Style.SHORT);
        System.out.println(format.format(1_0));
        System.out.println(format.format(1_9220));
        System.out.println(format.format(1_000_000));
        System.out.println(format.format(1L<<20));
        System.out.println(format.format(1L<<30));
        System.out.println(format.format(1L<<40));
        System.out.println(format.format(1L<<50));

    }

}
