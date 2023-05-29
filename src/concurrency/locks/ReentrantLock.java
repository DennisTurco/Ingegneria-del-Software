package concurrency.locks;

public class ReentrantLock implements Lock{

    // owner del lock
    private Thread owner;

    // contiamo gli accessi alla lock ed unlock
    // lo stesso thread potrebbe fare piu' lock alla stessa risorsa per aumentare i livelli di lock.
    // lo stesso thread per esempio puo' loccare 3 volte, poi pero' devo unloccare 3 volte per liberare la risorsa.
    private int counter;

    // per fare la sincronizzazione
    private Object mutex;

    public ReentrantLock() {
        this.owner = null;
        this.counter = 0;
        this.mutex = new Object();
    }

    @Override
    public void lock() {

        Thread currentThread = Thread.currentThread();

        synchronized (mutex) {
            if (counter < 0) throw new IllegalMonitorStateException("counter < 0");

            while(owner != null && owner != currentThread) {
                try {
                    mutex.wait();
                } catch (InterruptedException InterruptedException) {
                    throw new IllegalThreadStateException("interrupted");
                }
            }

            if (owner == null) {
                owner = currentThread;
            }

            counter++;
        }
    }

    @Override
    public void unlock() {
        synchronized (mutex) {
            if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()"); 
            if (counter <= 0) throw new IllegalMonitorStateException("counter <= 0");
        
            counter--;
            
            if (counter == 0) {
                owner = null;
                mutex.notify();
            }
        }
    }

    @Override
    public Condition newCondition() throws UnsupportedOperationException {
        return new InnerCondition();
    }
    
    // INNER CLASS
    private class InnerCondition implements Condition {
        
        private Object condition;

        private InnerCondition() {
            this.condition = new Object();
        }

        @Override
        public void await() throws InterruptedException {
            unlock();

            synchronized (condition) {
                condition.wait();
            }

            lock();
        }

        @Override
        public void signal() {
            synchronized (mutex) {
                if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()");
            }   
            
            synchronized (condition) {
                condition.notify();
            }
        }

        @Override
        public void signalAll() {
            synchronized (mutex) {
                if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()");
            }   
            
            synchronized (condition) {
                condition.notifyAll();
            }
            
        }
    }
}
