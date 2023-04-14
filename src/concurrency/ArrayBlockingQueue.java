package concurrency;

import concurrency.locks.ReentrantLock;
import concurrency.locks.Condition;
import concurrency.locks.Lock;

//NOTA: nel dubbio se fare una signal() (notify()), o una signalAll() (notifyAll()), fare sempre una ...All() per essere sicuri

public class ArrayBlockingQueue<T> implements BlockingQueue<T> {

    //non potrebbe essere un tipo T perchè se no fare: this.queue = new T[size]; sarebbe errore, ma non è un problema perchè tanto non cambia nulla
    private Object[] queue;
    private int size, count;
    private Lock lock;
    private Condition isNotEmpty, isNotFull;

    public ArrayBlockingQueue(int size) {
        if (size < 1) throw new IllegalArgumentException("size < 1");
        
        this.size = size;
        this.queue = new Object[size];
        this.count = 0;
        this.lock = new ReentrantLock();
        this.isNotEmpty = lock.newCondition();
		this.isNotFull = lock.newCondition();
    }

    @Override
    public void put(T elem) throws InterruptedException {
        if (elem == null) throw new NullPointerException("queue == null");
        
        try {
            lock.lock();

            while (count == size){
                isNotFull.await(); //rimaniamo in attesa che le cose vadano a buon fine
            }

            queue[count] = elem;
            ++count;
            isNotEmpty.signal(); // non è necessario eseguire una notifyAll(), basta svegliare un solo Thread
        }
        finally { // il blocco finally viene sempre eseguito, sia se si verifica un eccezione, sia se non si verifica
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
            T object = (T) queue[count-1];
            queue[count-1] = null;
            --count;
            isNotFull.signal(); // non è necessario eseguire una notifyAll(), basta svegliare un solo Thread

            return object;
        }
        finally {
            lock.unlock();
        }
        
    }

    @Override
    public int remainingCapacity() {

        //ottengo il lock e il numero di celle libere che è il valore che ritorniamo

		// non mi serve un costrutto try e catch perchè se la lock lancia un eccezione non viene lockato nulla quindi nessun problema

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
        count = 0;

        //prima di fare la unlock sagnaliamo che la coda non è più piena (magari non la era anche prima)
        //va eseguita una notifyAll() perchè grazie ad una clear lo spazio si è liberato e tutti possono entrare
        isNotFull.notifyAll();
        
        lock.unlock(); 
    }
    
}
