package RIFACCIO;

public interface Lock {
    public void lock();
    public void unlock();
    public Condition newCondition() throws UnsupportedOperationException;   
}