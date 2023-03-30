package concurrency;
public interface BlockingQueue<Type> {
    public void put(Type elem) throws InterruptedException;
    public Type take() throws InterruptedException;
    public int remainingCapacity();
    public boolean isEmpty();
    public void clear();
}