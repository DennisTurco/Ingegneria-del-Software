package concurrency.atomic;

import java.util.function.UnaryOperator;

public class AtomicReference<Type> {
    private Type value;
    private Object lock;

    public AtomicReference() {
        this(null);  // serve per chimare il costruttore sotto
    }

    public AtomicReference(Type value) {
        this.value = value;
        this.lock = new Object();
    }

    // costruisco la sezione critica e ritorno il valore
	// la get fa la deference
    public Type get() {
        synchronized (lock) {
            return value;
        }
    }

    // costruisco la sezione critica e setto il valore
    public void set(Type value) {
        synchronized (lock) {
            this.value = value;
        }
    }

    // Prende la sezione critica su lock, sovrascrive il valore di value e ritorna il valore vecchio prima
	// dell'aggiornamento.
    public Type getAndSet(Type value) {
        synchronized (lock) {
            Type ris = this.value;
            this.value = value;
            return ris;
        }
    }

    // Prima leggiamo il valore di value e lo ritorniamo quando è il momento.
    public Type getAndUpdate(UnaryOperator<Type> update) {
        synchronized (lock) {
            Type result = this.value;
            this.value = update.apply(value);
            return result;
        }

    }

    public Type updateAndGet(UnaryOperator<Type> update) {
        synchronized (lock) {
            Type result = update.apply(value);
            this.value = result;
            return result;
        }
    }
}