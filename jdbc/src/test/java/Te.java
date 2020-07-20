import com.czy.util.model.StringMap;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class Te {

    @Test
    public void aVoid() {
        var person = new Person();
        person.addCat(new Cat(person));
        person.addCat(new Cat(person));
        person.addCat(new Cat(person));
    }

    @Test
    public void a() {
        /*方案1：硬编码*/
        System.out.println("1");
        System.out.println("1 1");
        System.out.println("2 1");
        System.out.println("1 2 1 1");
        System.out.println("1 1 1 2 2 1");
        System.out.println("3 1 2 2 1 1");
        /*方案二：
        1、找规律
            1.1：奇数行的末尾1个1，偶数行末尾两个1
            1.2：若n-1行起始没有1，则第n行的开始为1，若n-1行起始为1且只有一个1，则n行起始为n-1行
        2、代码实现
        */
    }

    public <T> List<T> permutate(List<T> nums, List<T> set, List<T> answers) {
        if (nums == null || nums.isEmpty()) {
            //去重
            answers = set.stream().distinct().collect(Collectors.toList());
        }
        nums.forEach(num -> {
            //返回去除当前元素的集合
            nums.stream().filter(new Predicate<T>() {
                @Override
                public boolean test(T t) {
                    return false;
                }
            });
            set.add(num);
        });

        for (int i = 0; i < nums.size(); i++) {
            T temp = (T) nums.remove(i);
            set.add(temp);
            permutate(nums, set, answers);
            if (!set.isEmpty()) {
                set.remove(set.size() - 1);
            }
        }

        return answers;
    }

    @Test
    public void list() {
        var map = new StringMap<>(3,"a","值1").add("b","值2").add("c","值3");
        {
            var temp=map.keySet().toString();
            System.out.println(temp.substring(1,temp.length()-1));
        }
        {
            var temp=map.values().toString();
            System.out.println(temp.substring(1,temp.length()-1));
        }
    }
}
