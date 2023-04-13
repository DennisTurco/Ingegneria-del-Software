package RIFACCIO;

public class Executors {
    public static ExecutorService newFiExecutorService(int count) {
        return new SimpleThreadPoolExecutorService(count);
    }

    private Executors() {
        // blank
    }
}