package factory;

/**
 * @author chenzy
 * @date 2020-07-23
 */
public class ConcreteFactory extends Factory {
    @Override
    public <T extends Product> T createProduct(Class<T> clz) {
        try {
            return clz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
