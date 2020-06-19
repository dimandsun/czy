import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author chenzy
 * @since 2020-06-19
 */
/*
Consumer<T> 消费型接口
    void accept(T t);
Supplier<T> 供给型接口
    T get();
Function<T,R> 函数型接口
    R apply(T t)
Predicate<T>:断言型接口
    boolean test(T t)
*/
public class TestLambda {

    @Test
    public void a() {
//        happy(123d, d -> System.out.println("赚钱了" + d));
//        getList(5, () -> (int)(Math.random()*100)).forEach(integer -> System.out.println(integer));

        Consumer<String> consumer1 =  s-> System.out.println(s);
        Consumer<String> consumer2 =  System.out::println;
        consumer2.accept("你好");
        ((Consumer<String>)(System.out::println)).accept("ads");

        var employee=new Employee("chenzy");
        Supplier<String> supplier1 =employee::getName;


        //类名::实例方法 lambda表达式中 参数1是实例方法的调用者，第二个参数是方法参数时，可使用这种方式
        BiPredicate<String,String> e=String::equals;


        System.out.println(supplier1.get());

    }

    public void happy(Double d, Consumer<Double> consumer) {
        consumer.accept(d);
    }
    public <T> List<T> getList(int num, Supplier<T> supplier) {
        var result = new ArrayList<T>();
        for (int i = 0; i < num; i++) {
            result.add(supplier.get());
        }
        return result;
    }
}
