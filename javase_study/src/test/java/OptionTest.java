import org.junit.Test;

import java.util.Optional;

/**
 * @author chenzy
 * @description
 * @since 2020/6/20
 */
/*
Optional.of(T t); 创建实例
Optional.empty(); 创建空实例
Optional.ofNullable(T t); 若不为空创建实例，否则创建空实例
isPresent() 判断是否包含值
orElse(T t) 如果包含值，返回。否则返回t
orElseGet(Supplier s) 如果包含值，返回，否则返回s获取值
map(Function f) 如果有值对其处理，并返回处理后的Optional,否则返回Optional.empty()
flatMap(Function f) 与map类似，要求返回值必须是Optional
*/
public class OptionTest {

    @Test
    public void optionTest(){
        {
            //报空指针异常
//            Optional<Employee> employeeOptional=Optional.of(null);
//            System.out.println(employeeOptional.get());
        }
        {
            //get后报NoSuchElementException
            Optional<Employee> employeeOptional= Optional.empty();
//            System.out.println(employeeOptional.get());
        }
        {
            Optional.ofNullable(null);

        }
        {
            if (Optional.ofNullable(null).isPresent()){

            }

        }



    }
}
