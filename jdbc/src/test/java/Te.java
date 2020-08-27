import com.czy.jdbc.pool.MyConnection;
import com.czy.util.model.StringMap;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class Te {
    @Test
    public void str() {
        System.out.println("systemTime".split("\\."));
    }

    @Test
    public void aVoid() {
        var person = new Person();
        person.addCat(new Cat(person));
        person.addCat(new Cat(person));
        person.addCat(new Cat(person));
    }

    @Test
    public void abc() {
//        StringMap.class == Map.class;
        List<MyConnection> pool = Collections.synchronizedList(new LinkedList<MyConnection>());
        System.out.println(123);
        try{

        }catch (Exception e){

        }finally {

        }

    }

    @Test
    public void ab1c() {
        var s1 = "abcd";//1362
        var s2 = new String("ab") + new String("cd");//1374
        var s3=s2.intern();//1362
        System.out.println(s2 == s3);
    }

    @Test
    public void ab2c() {
        var s2 = new String("ab") + new String("cd");//1372
        var s3=s2.intern();//1372
        System.out.println(s2 == s3);
        var s1 = "abcd";//1372
        System.out.println(123);
    }
    @Test
    public void ab3c() {
        System.out.println("abcd" == (new String("ab") + new String("cd")));
        System.out.println(123);
    }
    @Test
    public void b() throws NoSuchMethodException {
        var method = Te.class.getMethod("a");
        var type = method.getAnnotatedReturnType().getType();
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
        var map = new StringMap<>(3, "a", "值1").add("b", "值2").add("c", "值3");
        {
            var temp = map.keySet().toString();
            System.out.println(temp.substring(1, temp.length() - 1));
        }
        {
            var temp = map.values().toString();
            System.out.println(temp.substring(1, temp.length() - 1));
        }
    }

    @Test
    public void test() {
        List<Integer> lastNums = new ArrayList<>();
        lastNums.add(1);
        for (int i = 0; i < 10; i++) {
            showNums(lastNums);
            lastNums = calcNewNums(lastNums);
        }
    }

    /**
     * 打印输出队列
     *
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
     *
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
                equalNums++;
            }
        }
        if (equalNums != 0) { //保存最后一个统计
            newNums.add(equalNums);
            newNums.add(lastNum);
        }
        return newNums;
    }

    @Test public void abcc() {
        var str = "A2B3c2";
        if (str == null || str.length() % 2 != 0) {
            System.out.println("字符串长度不是偶数！");
            return;
        }
        var chars = str.toCharArray();
        String result = "";
        for (int i = 0; i < chars.length - 1; i += 2) {
            var content = chars[i];/*char需先转成String，再转数字，*/
            var size = Integer.valueOf(String.valueOf(chars[i + 1]));
            if (!Character.isLowerCase(content) && !Character.isUpperCase(content)) {
                System.out.println("第" + (i + 1) + "个字符不是大小写字母");
                return;
            }
            if (size < 0 || size > 9) {
                System.out.println("第" + (i + 2) + "个字符不是数字");
                return;
            }
            if (size == 0) {
                System.out.println("第" + (i + 1) + "个字符重复次数不能为0");
                return;
            }/*对content复制size次*/
            result += String.valueOf(content).repeat(size);
        }
        System.out.println("结果：" + result);
    }
}
