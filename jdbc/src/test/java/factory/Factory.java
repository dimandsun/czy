package factory;

/**
 * @author chenzy
 * @date 2020-07-23
 */
public abstract class Factory {
    public abstract <T extends Product> T createProduct(Class<T> clz);
}
