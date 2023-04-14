package concurrency.locks;

public interface Lock {
    public void lock();
    public void unlock();
    public Condition newCondition();
}
