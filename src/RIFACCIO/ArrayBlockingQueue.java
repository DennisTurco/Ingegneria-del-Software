package RIFACCIO;

public class ArrayBlockingQueue<T> implements BlockingQueue<T> {

    private Object[] queue;
    private int count;
    private int size;
    private Lock lock;
    private Condition isNotEmpty;
    private Condition isNotFull;

    public ArrayBlockingQueue(int size) {
        if (size < 1) throw new IllegalArgumentException("size < 1");

        this.size = size;
        this.count = 0;
        this.lock = new ReentrantLock();
        this.queue = new Object[size];
    }

    @Override
    public void put(T elem) throws InterruptedException {
        if (queue == null) throw new NullPointerException("queue == null");

        try {
            lock.lock();

            while (count == size) isNotFull.await();

            queue[count] = elem;
            count++;
            isNotEmpty.signal();

        } finally {
            lock.unlock();
        }
    }

    @Override
    public T take() throws InterruptedException {
        if (queue == null) throw new NullPointerException("queue == null");

        try {
            lock.lock();

            while (count == 0) isNotEmpty.await();

            @SuppressWarnings("unchecked")
            T object = (T) queue[count-1];
            count--;
            isNotFull.signal();

            return object;

        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        boolean result = (count == 0);
        lock.unlock();
        return result;
    }

    @Override
    public int remainingCapacity() {
        lock.lock();
        int result = size - count;
        lock.unlock();
        return result;
    }

    @Override
    public void clear() {
        lock.lock();
        count = 0;
        queue = new Object[size];
        isNotFull.signalAll();
        lock.unlock();
    }
    
}