package RIFACCIO;

public class SimpleFuture<T> implements Future<T> {

    private boolean done;
    private T value;
    private Throwable exception;
    private Object mutex;

    public SimpleFuture() {
        this.done = false;
        this.value = null;
        this.exception = null;
        this.mutex = new Object();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        synchronized (mutex) {
            while (done == false) {
                mutex.wait();
            }

            if (value != null) return value;
            else throw new ExecutionException(exception); 
        }
    }

    @Override
    public boolean isDone() {
        synchronized (mutex) {
            return done;
        }
    }

    public void setException(Throwable exception) {
        if (exception == null) throw new NullPointerException("exception == null");

        synchronized (mutex) {
            if (done) throw new IllegalStateException("done == true");

            this.exception = exception;
            done = true;
            mutex.notifyAll();
        }
    }

    public void setValue(T value) {        
        synchronized (mutex) {
            if (done) throw new IllegalStateException("done == true");

            this.value = value;
            done = true;
            mutex.notifyAll();
        }
    }

}