package reference;

import java.lang.ref.WeakReference;

/**
 * @author chenzy
 * @date 2020-06-24
 * 弱引用：
 */
public class MyWeakReference {
    public static void main(String[] args) {
        //m指向WeakReference对象，WeakReference对象若引用指向M对象
        var m = new WeakReference<>(new M());
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());
    }
}
