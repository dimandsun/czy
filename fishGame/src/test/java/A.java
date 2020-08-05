import com.czy.util.io.FileUtilOld;
import com.czy.util.model.MyMap;
import org.junit.Test;

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
        List<MyMap> p = FileUtilOld.readConfigFileByXML("image/score/hundred.plist");
//        FileUtil.writeConfigFileByXML2YML("cannon/bulletandnet.plist","fishGame.yaml");
        printList(p);
        System.out.println(p);
    }
    public void printList(List<MyMap> list){
        for (MyMap map:list){
            printMap(map);
        }
    }
    public void printMap(MyMap map){
        Set<Map.Entry<String,Object>> entrySet=map.entrySet();
        for (Map.Entry<String,Object> entry: entrySet){
            Object value =entry.getValue();
            if (value instanceof List){
                printList((List<MyMap>) value);
            }else if (value instanceof MyMap){
                printMap((MyMap) value);
            }else {
                System.out.print(value.toString()+",");
            }
        }
    }
}
