package it.unipr.informatica.concurrent.atomic;

import java.util.function.UnaryOperator;

// ATOMIC REFERATION:
// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicReference.html

class AtomicReference<Type> {
	private Type value;
	private Object lock;
	
	public AtomicReference() {
		this(null);
	}
	
	public AtomicReference(Type value) {
		this.value = value;
		this.lock = new Object();
	}
	
	public Type get() {
		synchronized (lock) {
			return value;
		}
	}
	
	public void set(Type value) {
		synchronized (lock) {
			this.value = value;
		}
	}
	
	public Type getAndSet(Type value) {
		synchronized (lock) {
			Type result = this.value;
			this.value = value;
			return result;
		}
	}
	
	public Type getAndUpdate(UnaryOperator<Type> update) {
		synchronized (lock) {
			Type result = value;
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
