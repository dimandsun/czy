import com.czy.util.json.JsonUtil;

import java.util.Arrays;

/**
 * @author chenzy
 * @since 2020-06-19
 */
public class Employee {
    private String name;

    @Override
    public String toString() {
        return JsonUtil.model2Str(this);
    }

    @Override
    public int hashCode() {
        int result =0;
        for (var b:name.getBytes()){
            result+=b;
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee){
            var employee=(Employee)obj;
            return name.equals(employee.name);

        }
        return name.equals(obj);
    }

    public Employee(String name) {
        this.name=name;
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
