package RIFACCIO;

import java.util.function.UnaryOperator;

public class AtomicReference<T> {

    private Object mutex;
    private T value;

    public AtomicReference() {
        this(null);
    }

    public AtomicReference(T value) {
        if (value == null) throw new IllegalArgumentException("value == null");

        this.value = value;
    }

    public T get() {
        synchronized (mutex) {
            return value;
        }
    }

    public void set(T value) {
        if (value == null) throw new IllegalArgumentException("value == null");

        synchronized (mutex) {
            this.value = value;
        }
    }

    public T getAndSet(T value) {
        if (value == null) throw new IllegalArgumentException("value == null");

        synchronized (mutex) {
            T ris = this.value;
            this.value = value;
            return ris;
        }
    }

    public T getAndUpdate(UnaryOperator<T> update) { 
        synchronized (mutex) {
            T ris = value;
            value = update.apply(value);
            return ris;
        }
    }


    public T updateAndGet(UnaryOperator<T> update) { 
        synchronized (mutex) {
            T ris = update.apply(value);
            value = ris;
            return ris;
        }
    }

}