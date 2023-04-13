package RIFACCIO;

public class ArrayBlockingQueue<T> implements BlockingQueue<T> {

    private Object[] queue;
    private int size;
    private int count;
    private Lock lock;
    private Condition isNotEmpty, isNotFull;

    public ArrayBlockingQueue(int size) {
        if (size < 1) throw new IllegalArgumentException("size < 1");

        this.size = size;
        this.count = 0;
        this.queue = new Object[size];
        this.lock = new ReentrantLock();
        this.isNotEmpty = lock.newCondition();
        this.isNotFull = lock.newCondition();
    }

    @Override
    public void put(T elem) throws InterruptedException {
       if (elem == null) throw new IllegalArgumentException("elem == null");

       try {
            lock.lock();

            while (count == size) {
                isNotFull.await();
            }

            queue[count+1] = elem;
            ++count;
            isNotFull.signal();

       } finally {
            lock.unlock();
       }
    }

    @Override
    public T take() throws InterruptedException {
        try {
            lock.lock();
            
            while (count == 0) {
                isNotEmpty.await();
            }
            
            @SuppressWarnings("unchecked")
            T res = (T) queue[count];

            --count;

            isNotFull.signal();

            return res;

        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        boolean ris = count == 0;
        lock.unlock();
        return ris;
    }

    @Override
    public int remainingCapacity() {
        lock.lock();
        int res = size - count;
        lock.unlock();
        return res;
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