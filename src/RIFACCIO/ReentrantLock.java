package RIFACCIO;

public class ReentrantLock implements Lock {

    private Object mutex; // mi serve per la syncronized
    private Thread owner; // mi serve per capire chi e' owner del lock
    private int counter; // conto le lock

    public ReentrantLock() {
        this.mutex = new Object();
        this.owner = null;
        this.counter = 0;
    }

    @Override
    public void lock() {

        Thread currentThread = new Thread();

        synchronized (mutex) {
            if (counter < 0) throw new IllegalArgumentException("counter < 0");

            while (owner != null && owner != currentThread) {
                try {
                    mutex.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
            if (counter <= 0) throw new IllegalArgumentException("counter <= 0");
            if (owner == null) throw new IllegalArgumentException("owner == null");

            counter--;

            if (counter == 0) {
                owner = null;
                mutex.notify();
            }
        }
    }

    @Override
    public Condition newCondition() throws UnsupportedOperationException{
        return new InnerCondition();
    }

    // INNER CONDITION
    private class InnerCondition implements Condition{
        
        private Object condition;

        private InnerCondition() {
            this.condition = new Object();
        }

        @Override
        public void await() throws InterruptedException{
            unlock();

            synchronized (condition) {
                condition.wait();
            }

            wait();
        }

        @Override
        public void signal() {
            lock();

            synchronized (mutex) {
                if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()");
            }

            synchronized (condition) {
                condition.notify();
            }

            unlock();
        }

        @Override
        public void signalAll() {
            lock();

            synchronized (mutex) {
                if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()");
            }

            synchronized (condition) {
                condition.notifyAll();
            }

            unlock();
        }

    }
    
}
