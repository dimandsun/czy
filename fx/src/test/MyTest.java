import com.czy.util.text.StringUtil;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenzy
 * 
 * @since 2020-05-11
 */
public class MyTest {

    @Test
    public void testSort(){
        var list = Arrays.asList(new Character[]{'e', 'a', 'b', 'c'});
        list=sortList(list);
        System.out.println(list);
    }
    /**
     * 对集合进行排序，集合的子对象需实现Comparable接口，
     * 注意：String没有实现Comparable
     * @param list
     * @param <T> 需实现Comparable接口
     * @return 排序结果
     */
    public static<T extends Comparable> List<T> sortList(List<T> list) {
        if (StringUtil.isBlank(list)){
            return list;
        }
        Class<T> tClass = (Class<T>) list.get(0).getClass();
        T[] result=  list.stream().sorted(T::compareTo)
                .toArray((int value) ->(T[]) Array.newInstance(tClass,list.size()));
        return Arrays.asList(result);
    }
}
