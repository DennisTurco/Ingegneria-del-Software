package concurrency;

import concurrency.locks.ReentrantLock;
import concurrency.locks.Lock;

public class ArrayBlockingQueue<Type> implements BlockingQueue<Type> {

    private Object[] queue;
    private int size;
    private int count;
    private Lock lock;
    private int in = 0;
    private int out = 0;
    private Condition isNotEmpty, isNotFull;

    public ArrayBlockingQueue(int size) {
        if (size < 1) throw new IllegalArgumentException("size < 1");
        
        this.size = size;
        this.queue = new Object[size];
        this.count = 0;
        this.in = 0;
        this.out = 0;
        this.lock = new ReentrantLock();
    }

    @Override
    public void put(Type elem) throws InterruptedException {
        if (queue == null) throw new NullPointerException("queue == null");
        
        try {
            lock.lock();

            while (count == size){
                isNotFull.await();
            }

            queue[in] = elem;
            count++;
            in = (in + 1) % size;
            isNotEmpty.signal();
        }
        finally {
            lock.unlock();
        }

    }

    @Override
    public Type take() throws InterruptedException {
        if (queue == null) throw new NullPointerException("queue == null");
        
        try {
            lock.lock();
            
            while (count == 0) {
                isNotEmpty.await();
            }

            @SuppressWarnings("unchecked")
            Type object = (Type) queue[out];
            count--;
            out = (out + 1) % size;

            isNotFull.signal();

            return object;
        }
        finally {
            lock.unlock();
        }
        
    }

    @Override
    public int remainingCapacity() {
        lock.lock();
        int remaining = size - count;
        lock.unlock(); 
        return remaining;
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        boolean result = (count == 0);
        lock.unlock();    
        return result;
    }

    @Override
    public void clear() {
        lock.lock();
        queue = new Object[size];
        in = out = count = 0;
        isNotFull.notifyAll();
        lock.unlock(); 
    }
    
}
