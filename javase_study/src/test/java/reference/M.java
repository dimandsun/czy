package reference;

/**
 * @author chenzy
 * @date 2020-06-24
 */
public class M {
    /**
     * 垃圾回收时调用
     * 重写此方法，在这里做耗时的资源回收操作，等于延长对象的生命周期，对象产生快，回收慢。可能会导致内存泄漏
     *
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
    }
}
