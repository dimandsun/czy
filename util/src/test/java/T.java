import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzy
 * 
 * @since 2020-05-14
 */
public class T {

    @Test
    public void option() {

        Map<String, Map<String,Map<String,Object>>> map = new HashMap<>();
       /* map.put("a", 123);
        map.put("a22", 222);*/
/*        Optional.of(map).map(new Function<Map<String, Object>, Object>() {

            @Override
            public Object apply(Map<String, Object> stringObjectMap) {
                return null;
            }

            @Override
            public <V> Function<V, Object> compose(Function<? super V, ? extends Map<String, Object>> before) {
                return null;
            }

            @Override
            public <V> Function<Map<String, Object>, V> andThen(Function<? super Object, ? extends V> after) {
                return null;
            }
        });*/

    }
}
