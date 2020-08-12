import com.czy.util.io.FileUtil;
import com.czy.util.model.MyMap;
import com.czy.util.model.StringMap;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenzy
 * 
 * @since 2020-05-08
 */

public class A {

    @Test
    public void a() {
        var p = FileUtil.readXML(FileUtil.getResourceFile("image/score/hundred.plist")).get();
        printList(p);
        System.out.println(p);
    }
    public void printList(List<StringMap> list){
        for (StringMap map:list){
            printMap(map);
        }
    }
    public void printMap(StringMap map){
        Set<Map.Entry<String,Object>> entrySet=map.entrySet();
        for (Map.Entry<String,Object> entry: entrySet){
            Object value =entry.getValue();
            if (value instanceof List){
                printList((List<StringMap>) value);
            }else if (value instanceof StringMap){
                printMap((StringMap) value);
            }else {
                System.out.print(value.toString()+",");
            }
        }
    }
}
