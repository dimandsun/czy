import com.czy.util.ClassUtil;
import com.czy.util.model.StringMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public void abc(){
//        StringMap.class == Map.class;

        System.out.println(123);

    }

    @Test
    public void b() throws NoSuchMethodException {
        var method=Te.class.getMethod("a");
        var type =method.getAnnotatedReturnType().getType();
        type.equals(Void.TYPE);
        System.out.println(1);
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
    @Test
    public void test() {
        List<Integer> lastNums = new ArrayList<>();
        lastNums.add(1);
        for (int i = 0; i < 10; i ++) {
            showNums(lastNums);
            lastNums = calcNewNums(lastNums);
        }
    }
    /**
     * 打印输出队列
     * @duparam nums
     */
    private void showNums(List<Integer> nums) {
        for (Integer num : nums) {
            System.out.print(num + " ");
        }
        System.out.println("");
    }
    /**
     * 统计当前队列生成新的队列
     * @param lastNums
     * @return
     */
    private List<Integer> calcNewNums(List<Integer> lastNums) {
        List<Integer> newNums = new ArrayList<>();
        int lastNum = 0;
        int equalNums = 0;
        for (Integer num : lastNums) {
            if (equalNums == 0) { //第一个数
                lastNum = num.intValue();
                equalNums = 1;
            } else if (num.intValue() != lastNum) { //当前数不等于之前的数
//保存前面的统计
                newNums.add(equalNums);
                newNums.add(lastNum);
//更新当前的统计
                lastNum = num.intValue();
                equalNums = 1;
            } else { //当前数等于之前的数，计数 +1
                equalNums ++;
            }
        }
        if (equalNums != 0) { //保存最后一个统计
            newNums.add(equalNums);
            newNums.add(lastNum);
        }
        return newNums;
    }
}
