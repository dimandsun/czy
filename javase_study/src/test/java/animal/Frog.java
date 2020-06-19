package animal;

/**
 * @author chenzy
 * @since 2020-06-19
 * 青蛙
 */
public class Frog extends Ovipara {
    public Frog() {
    }
    public float age;

    @Override
    public String toString() {
        return "Frog{}";
    }

    @Override
    public void move() {
        super.move();
    }

    public static void main(String[] args) {
        Frog frog = new Frog();
        Fish fish = new Fish();
        Bird bird = new Bird();
        Cat cat = new Cat();
        Dog dog = new Dog();
        Eatable[] eatables = new Eatable[]{frog, fish, bird, cat, dog};
    }
}
