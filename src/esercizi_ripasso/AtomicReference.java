package esercizi_ripasso;

import java.util.function.UnaryOperator;

public class AtomicReference<T> {
	private T value;
	private Object lock;
	
	
	// costruttore vuoto
	public AtomicReference() {
		this(null);
	}
	
	public AtomicReference(T value) {
		this.value = value;
		this.lock = new Object();
	}
	
	// costruisco la sezione critica e ritorno il valore
	// la get fa la deference
	public T get() {
		synchronized (lock) {
			return value;
		}
	}
	
	// costruisco la sezione critica e setto il valore
	public void set(T value) {
		synchronized (lock) {
			this.value = value;
		}
	}
	
	// Prende la sezione critica su lock, sovrascrive il valore di value e ritorna il valore vecchio prima
	// dell'aggiornamento.
	public T getAndSet(T value) {
		synchronized (lock) {
			T result = this.value;
			this.value = value;
			return result;
		}
	}
	
	// Prima leggiamo il valore di value e lo ritorniamo quando è il momento.
	public T getAndUpdate(UnaryOperator<T> update) {
		synchronized (lock) {
			T result = value;
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
