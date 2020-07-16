import org.junit.Test;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class Te {

    @Test
    public void aVoid(){
        var person = new Person();
        person.addCat(new Cat(person));
        person.addCat(new Cat(person));
        person.addCat(new Cat(person));
    }
}
