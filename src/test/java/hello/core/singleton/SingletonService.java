package hello.core.singleton;

public class SingletonService {
    private static final SingletonService instance = new SingletonService();

    private SingletonService() {}

    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("로직");
    }
}
