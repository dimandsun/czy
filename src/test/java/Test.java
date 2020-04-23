import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenzy
 * @description
 * @since 2020-04-10
 */
public class Test {

    @org.junit.Test
    public void tes() {
        try {
            Method method = Test.class.getMethod("abc", Integer.class);
            Test t = new Test();
            Integer i = 0;
            while (i<1000) {
                Lock lock = new ReentrantLock();
                Integer finalI1 = i;
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        lock.lock();
                        super.run();
                        try{
                            sleep(5000);
                            System.out.println("爱德华副科级啦-----------i=" + finalI1);
                            System.out.println();
                        }catch (Exception e){
                            e.printStackTrace();
                            lock.unlock();
                        }

//                            method.invoke(t, finalI);
                    }
                };
                i++;
                thread.start();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public void abc(Integer i) {
        System.out.println("--------------abc方法执行了,i=" + i);
    }

    @org.junit.Test
    public void by(){
        String s="E855424D653185C242A32C1B7C04ACE6";
        System.out.println(s.getBytes().length);
    }

    @org.junit.Test
    public void tesa(){
    }
    @org.junit.Test
    public void reName(){
    }

    @org.junit.Test
    public void testJson(){
    }

}
