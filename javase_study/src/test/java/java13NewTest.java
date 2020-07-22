import com.czy.core.enums.ActiveEnum;
import org.junit.Test;

/**
 * @author chenzy
 * @since 2020/6/21
 */
public class java13NewTest {
    //yield结束switch结构
    @Test
    public void switchTest() {
        {
            var a = ActiveEnum.Dev;
            var s = switch (a) {
                case Dev -> "b";
                case Pro, Test -> "a";
                default -> "123";
            };
            System.out.println(s);
        }
        {
            var a = ActiveEnum.Dev;
            var s = switch (a) {
                case Dev -> "b";
                case Pro, Test -> "a";
                default -> {
                    String abc="";
                    yield 12;
                }
            };
            System.out.println(s);
        }

        {
            var a = ActiveEnum.Dev;
            var s = switch (a) {
                case Dev:yield "123";
                case Pro, Test :yield "a";
                default:yield 12;
            };
            System.out.println(s);
        }

    }

    /*文本块：*/
    @Test public void textTest(){
        {
            String html = """
       <html>
         <body>
           <p>Hello, world</p>
         </body>
       </html>
       """;
            System.out.println(html);
        }
        {
            String html = """
                    """;
            System.out.println(html.length());
        }

        {
            //三个双引号所在行的空格不会作为内容
            //文本块每行开始的空格，要根据结束行的三个引号前有几个空格，它有几个空格，就会去除几个空格
            //html和html1不相等
            String html = """
                    阿萨法
""";
            System.out.println(html.length());
            String html1 = """
                    阿萨法
            """;
            System.out.println(html1.length());
        }
        {
            String html = """
String html = \"""
                                           阿萨法
                       \""";
                    """;
            System.out.println(html);
        }

        {
            int i=0;
            String html = """
asd"""+0+"""
                    """;
            System.out.println(html);
        }
        {
            int i=0;
            String html = """
asd$i
                    """.replace("$i",i+"");
            System.out.println(html);
        }
        {
            int i=0;
            int a=2;
            String html = String.format("""
asd%s%s
                    """,i,a);
            System.out.println(html);
        }
        {
            int i=0;
            int a=2;
            String html = String.format("""
asd%s%s
                    """,i,a);
            System.out.println(html);
        }
    }

    @Test public void a(){

    }
}
