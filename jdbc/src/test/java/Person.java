import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class Person {
    private List<Cat> catList=new ArrayList<>();

   public void addCat(Cat cat){
       catList.add(cat);
   }
   public void removeCat(){
       if (catList.isEmpty()){
           return;
       }
       catList.remove(catList.size()-1);
   }
}
