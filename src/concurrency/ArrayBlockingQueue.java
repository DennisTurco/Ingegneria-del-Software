package concurrency;

import concurrency.locks.ReentrantLock;
import concurrency.locks.Lock;

//NOTA: nel dubbio se fare una signal() (notify()), o una signalAll() (notifyAll()), fare sempre una ...All() per essere sicuri

public class ArrayBlockingQueue<Type> implements BlockingQueue<Type> {

    //non potrebbe essere un tipo Type perchè se no fare: this.queue = new Type[size]; sarebbe errore, ma non è un problema perchè tanto non cambia nulla
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
                isNotFull.await(); //rimaniamo in attesa che le cose vadano a buon fine
            }

            queue[in] = elem;
            count++;
            in = (in + 1) % size;
            isNotEmpty.signal(); // non è necessario eseguire una notifyAll(), basta svegliare un solo Thread
        }
        finally { // il blocco finally viene sempre eseguito, sia se si verifica un eccezione, sia se non si verifica
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
        in = out = count = 0; //coda sicuramente svuotata

        //prima di fare la unlock sagnaliamo che la coda non è più piena (magari non la era anche prima)
        //va eseguita una notifyAll() perchè grazie ad una clear lo spazio si è liberato e tutti possono entrare
        isNotFull.notifyAll();
        
        lock.unlock(); 
    }
    
}
