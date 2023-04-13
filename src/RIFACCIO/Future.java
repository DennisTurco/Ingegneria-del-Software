package RIFACCIO;

public interface Future<T> {
    public T get() throws InterruptedException, ExecutionException;
    public boolean isDone();
}