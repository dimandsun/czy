package factory;

/**
 * @author chenzy
 * @date 2020-07-23
 */
public class Client {
    public static void main(String[] args) {
        Factory factory = new ConcreteFactory();
        Product p = factory.createProduct(ConcreteProductA.class);
        p.method();
    }
}
