import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;

/**
 * @author chenzy
 * @since 2020/6/21
 */
public class java14NewTest {


    /*
    开启空指针异常提示-XX:+ShowCodeDetailsInExceptionMessages
    关闭空指针异常提示：-XX:-ShowCodeDetailsInExceptionMessages
    */
    @Test public void nullTest(){
        StringMap<StringMap<StringMap>> abc = new StringMap<>();
    }
    //只能声明静态属性。不能为abstract。不能显式继承类
    @Test public void recordTest(){
        record A(@JsonProperty("name_a") String name, Integer age){
            private static int a=0;

            public static int getA() {
                return a;
            }

            public static void setA(int a) {
                A.a = a;
            }
        }
        var a=new A("a",12);
        System.out.println(JsonUtil.model2Str(a));
    }

    @Test public void instanceofTest(){
        {
            Object a="";
            if (a instanceof String s){
                System.out.println(s);
            }
        }
        {
            Object a="";
            a=null;
            if (a instanceof String s){
                System.out.println(s);
            }
        }
        {
            Object a="";
            if (a instanceof String s){
                System.out.println(s);
            }else {
                System.out.println(s);
            }
        }
        {
            Boolean b=false;
            Object a="";
            if (a instanceof String s){
                if (s.equals("")){
                    b=true;
                }
            }
            b= a instanceof String s&&s.equals("");

        }
    }
    String s="asd";
}
