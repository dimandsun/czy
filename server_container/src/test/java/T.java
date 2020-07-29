import org.junit.Test;

import java.util.IdentityHashMap;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class T {
    @Test public void testMap() {
        {
            var map = new IdentityHashMap<String,Object>();
            map.put("1","擦");
            map.put("2","擦");
            map.put("1","擦");
            map.forEach((key,value)-> System.out.println(key+":"+value));
        }

        {
            var map = new IdentityHashMap<String,Object>();
            map.put(new String("1"),"擦3");
            map.put(new String("1"),"擦1");
            map.put(new String("1"),"擦2");
            map.get("1");
            map.forEach((key,value)-> System.out.println(key+":"+value));
        }
    }
}
