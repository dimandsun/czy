package animal;

import com.czy.util.json.JsonUtil;

import java.util.Date;

/**
 * @author chenzy
 * @since 2020-06-19
 */
public abstract class Animal implements Eatable,Moveable{

    private String name;
    public Date birthDay;

    public Animal() {
    }

    public Animal(String name, Date birthDay) {
        this.name = name;
        this.birthDay = birthDay;
    }

    @Override
    public void eating() {

    }
    @Override
    public void move() {

    }

    @Override
    public String toString() {
        return JsonUtil.model2Str(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}
