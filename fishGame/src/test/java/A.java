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

    public void printList(List<?> list){
        list.forEach(o -> {
            if (o instanceof StringMap map){
                printMap(map);
            }
        });
    }
    public void printMap(StringMap<?> map){
        map.values().forEach(value -> {
            if (value instanceof List<?> list){
                    printList(list);
            }else if (value instanceof StringMap<?> temp){
                printMap(temp);
            }else {
                System.out.print(value.toString()+",");
            }
        });
    }
}
