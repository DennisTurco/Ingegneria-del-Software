package RIFACCIO;


public class ReentrantLock implements Lock {

    private Thread owner;
    private int counter;
    private Object mutex;

    public ReentrantLock() {
        this.owner = new Thread();
        this.counter = 0;
        this.mutex = new Object();
    }

    @Override
    public void lock() {
        Thread currenThread = new Thread();

        synchronized (mutex) {
            if (counter < 0) throw new IllegalArgumentException("counter < 0");

            while (owner != null && owner != currenThread) {
                try {
                    mutex.wait();
                } catch (InterruptedException interruptedException) {
                    throw new IllegalMonitorStateException("interrupted");
                }
            }

            if (owner == null) owner = currenThread;

            counter++;

        }
    }

    @Override
    public void unlock() {
        synchronized (mutex) {

            if (owner != Thread.currentThread()) throw new IllegalStateException("owner != Thread.currentThread()");

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


    // INNER CONDITION
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
            synchronized(mutex) {
                if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()");
            }

            synchronized (condition) {
                condition.notify();
            }
        }

        @Override
        public void signalAll() {
            synchronized(mutex) {
                if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()");
            }

            synchronized (condition) {
                condition.notifyAll();
            }
        }

    }

    
}
