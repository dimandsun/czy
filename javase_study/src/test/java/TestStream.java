import com.czy.util.StringUtil;
import org.junit.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author chenzy
 * @since 2020-06-19
 */
/*
集合是数据
流是计算
stream  自身不存储元素
        不改变源对象
        返回一个持有结果的stream
        操作是延迟的，需要结果时才执行

集合->stream->中间操作-》新流

中间操作:filter/map/mapToXX/flatMap/sort/ 等等
    筛选与切片:
        filter：接收lambda，排除某些元素
        limit：截断流，使元素不超过给定数量
        skip(n):跳过元素，返回一个扔掉了n个元素的流，若流中元素不足n个，则返回一个空流。与limit(n)互补
        distinct-去重，通过流所生成的元素的hashCode()和equals去重
    映射：
        map 接收lambda。接收一个函数作为参数，该函数会应用到每个元素上，并把元素映射成新元素，返回新流
        flatMap 接收一个函数作为参数，该函数会应用到每个元素上，并把元素映射成新流，然后把这些新流合并返回一个流
    排序
        自然排序
    查找与匹配:
        allMatch 匹配所有元素        返回boolean
        anyMatch 匹配至少一个元素    返回boolean
        noneMatch 匹配0个元素        返回boolean
        findFirst 查找第一个元素     返回Optional<元素>
        findAny 查找任意元素         返回Optional<元素>
        count 总个数                 返回int
        max                         返回Optional<元素>
        min                         返回Optional<元素>
    归约
        reduce:
    收集

并行流与顺序流
    parallel、sequential切换并行流和顺序流
*/
public class TestStream {
    private Employee[] employees = new Employee[]{new Employee("123", 12), new Employee("张三", 2), new Employee("张三", 26), new Employee("马六", 30),};
    private String[] strings = new String[]{"1", "5", "3", "不撒旦法", "bac", "abc", "hah", "呵呵", "啊", "去", "爱的色放", "阿斯蒂芬", "在不出现", "阿飞", "规范"};

    @Test
    public void parallelTest(){
        //累加.parallel()切换并行流
       var result = LongStream.rangeClosed(0,10000000L)
                .parallel()
                .reduce(0,Long::sum);
        System.out.println(result);
    }


    @Test
    public void collectTest() {
        List<Employee> r = Arrays.stream(employees).collect(Collectors.toList());
        Arrays.stream(employees).collect(Collectors.toSet());
        var a = Arrays.stream(employees).collect(Collectors.toCollection(HashSet::new));
        System.out.println(a);
        //总个数
        System.out.println(Arrays.stream(employees).collect(Collectors.counting()));
        //平均年龄
        System.out.println(Arrays.stream(employees).collect(Collectors.averagingInt(Employee::getAge)));
        //总年龄
        System.out.println(Arrays.stream(employees).collect(Collectors.summingInt(Employee::getAge)));
        //年龄最大
        System.out.println(Arrays.stream(employees).max(Comparator.comparing(Employee::getAge)));
        System.out.println(Arrays.stream(employees).collect(Collectors.maxBy(Comparator.comparing(Employee::getAge))));
        //返回 个数、总计、最小值、平均值、最大值
        System.out.println(Arrays.stream(employees).collect(Collectors.summarizingDouble(Employee::getAge)));
        //拼接字符串
        System.out.println(Arrays.stream(employees).map(Employee::getName).collect(Collectors.joining(",","begin","end")));
        //分组
        System.out.println(Arrays.stream(employees).collect(Collectors.groupingBy(Employee::getName)));
        //多级分组
        System.out.println(Arrays.stream(employees).collect(Collectors.groupingBy(Employee::getName, Collectors.groupingBy(employee -> {
            if (employee.getAge() < 6) {
                return "婴儿";
            } else if (employee.getAge() < 12) {
                return "儿童";
            } else if (employee.getAge() < 18) {
                return "少年";
            } else if (employee.getAge() < 30) {
                return "青年";
            } else {
                return "老人";
            }
        }))));
        //分区
         System.out.println(Arrays.stream(employees).collect(Collectors.partitioningBy(employee -> employee.getAge()>20)));

    }

    @Test
    public void reduceTest() {
        /*identity作为接口方法的参数1，从流中取出一个元素作为参数2——————》接口方法的返回值作为接口方法的参数1，再从流出取出一个元素作为参数2.....
         以此计算得到接口参数最后返回的元素
        */
        var employee = new Employee("as1");
        var result = Arrays.stream(employees).reduce(employee, (employee1, employee2) -> {
            employee.setName(employee1.getName() + employee2.getName());
            return employee1;
        });
        System.out.println(result);
    }

    @Test
    public void matchFindTest() {
        System.out.println(Arrays.stream(employees).findAny());
    }

    @Test
    public void sortTest() {
        Arrays.stream(strings).sorted().forEach(System.out::println);
        Arrays.stream(strings).sorted(String::compareTo).forEach(System.out::println);
    }

    @Test
    public void flatMapTest() {
        //列表里面的字符串转换成字符输出
        Arrays.stream(employees).map(employee -> Arrays.stream(StringUtil.getArray(employee.getName()))).forEach(stream -> stream.forEach(System.out::println));
//        等价于
        Arrays.stream(employees).flatMap(employee -> Arrays.stream(StringUtil.getArray(employee.getName()))).forEach(System.out::println);
    }

    @Test
    public void mapTest() {
        Arrays.stream(employees).map(Employee::getName).forEach(System.out::println);
    }

    @Test
    public void distinctTest() {
        Arrays.stream(employees).distinct().forEach(System.out::println);
    }

    @Test
    public void skipTest() {
        Arrays.stream(employees).skip(2).forEach(System.out::println);
    }

    @Test
    public void limitTest() {
        Arrays.stream(employees).limit(2).forEach(System.out::println);
    }

    @Test
    public void filterTest() {
        //中间操作 filter
        var filterStream = Arrays.stream(employees).filter(employee -> {
            System.out.println("中间操作：" + employee.getName());
            return !employee.getName().equals("abc");
        });
        //终止操作
        filterStream.forEach(System.out::println);


    }


    @Test
    public void createStream() {
//        创建流 Collections集合的stream() paralleStream()
//               Arrays.stream()
//               Stream.of();
//         Stream.iterate

        //Collections集合的stream() paralleStream()
        var list = new ArrayList<String>();
        var stream = list.stream();

        //Arrays方法创建，返回数组
        var employees = new Employee[2];
        var employeeStream = Arrays.stream(employees);

        //Stream的静态方法创建
        Stream.of("a", "v");

        //创建无线流
        var iStream = Stream.iterate(0, x -> x + 2);
        iStream.limit(10).forEach(System.out::println);

        //生成
        Stream.generate(Math::random).limit(5).forEach(System.out::println);


//        中间操作:filter/map/mapToXX/flatMap/sort/


//        终止操作
    }
}
