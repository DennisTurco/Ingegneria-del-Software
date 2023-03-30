package RIFACCIO;

public interface BlockingQueue<T> {
    public void put(T elem) throws InterruptedException;
    public T take() throws InterruptedException;
    public boolean isEmpty();
    public int remainingCapacity();
    public void clear();
}