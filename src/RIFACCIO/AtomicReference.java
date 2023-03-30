package RIFACCIO;

import java.util.function.UnaryOperator;

public class AtomicReference<T> {
    private T value;
    private Object lock;

    public AtomicReference() {
        this(null);  // serve per chimare il costruttore sotto
    }

    public AtomicReference(T value) {
        this.value = value;
        this.lock = new Object();
    }

    public T get() {
        synchronized (lock) {
            return value;
        }
    }

    public void set(T value) {
        synchronized (lock) {
            this.value = value;
        }
    }

    public T getAndSet(T value) {
        synchronized (lock) {
            T ris = this.value;
            this.value = value;
            return ris;
        }
    }

    public T getAndUpdate(UnaryOperator<T> update) {
        synchronized (lock) {
            T result = this.value;
            this.value = update.apply(value);
            return result;
        }

    }

    public T updateAndGet(UnaryOperator<T> update) {
        synchronized (lock) {
            T result = update.apply(value);
            this.value = result;
            return result;
        }
    }
}