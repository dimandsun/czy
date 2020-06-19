import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

中间操作:filter/map/mapToXX/flatMap/sort/
    filter：接收lambda，排除某些元素
    limit：截断流，使元素不超过给定数量
    skip(n):跳过元素，返回一个扔掉了n个元素的流，若流中元素不足n个，则返回一个空流。与limit(n)互补
    distinct-去重，通过流所生成的元素的hashCode()和equals去重
*/
public class TestStream {
    private Employee[] employees = new Employee[]{new Employee("123"), new Employee("张三"), new Employee("张三"), new Employee("马六"),};

    @Test
    public void distinctTest(){
        Arrays.stream(employees).distinct().forEach(System.out::println);
    }
    @Test
    public void skipTest(){
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
            System.out.println("中间操作："+employee.getName());
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
