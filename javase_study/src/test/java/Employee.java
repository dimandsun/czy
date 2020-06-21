import com.czy.util.json.JsonUtil;

import java.util.Arrays;

/**
 * @author chenzy
 * @since 2020-06-19
 */
public class Employee {
    private String name;

    private Integer age;

    @Override
    public String toString() {
        return JsonUtil.model2Str(this);
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (var b : name.getBytes()) {
            result += b;
        }
        result += age;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            var employee = (Employee) obj;
            boolean isEqual = true;
            if (!name.equals(employee.name)) {
                return false;
            }

            if (employee.age==null)return age==null;
            else return employee.age.equals(age);
        }
        return name.equals(obj);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Employee(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
