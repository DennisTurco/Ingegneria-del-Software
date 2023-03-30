package concurrency.locks;

import concurrency.Condition;

public interface Lock {
    public void lock();

    public void unlock();

    public Condition newCondition();
}
