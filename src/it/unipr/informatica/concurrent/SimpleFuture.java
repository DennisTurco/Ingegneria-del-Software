package it.unipr.informatica.concurrent;

import java.util.concurrent.ExecutionException;

public class SimpleFuture<Type> implements Future<Type> {

	private Object mutex;
	private Type value;
	private Throwable exception;
	private boolean done;
	
	SimpleFuture() {
		this.mutex = new Object();
		this.done = false;
		this.value = null;
		this.exception = null;
	}
	
	@Override
	public Type get() throws InterruptedException, ExecutionException {
		synchronized (exception) { //sezione critica, verifichiamo se c'e' l'eccezione o c'e' il valore
			if(!done) mutex.wait(); // si puo' anche mettere while
			if(exception != null) throw new ExecutionException(exception); // se exception non e' nullo vuol dire che l'eccezione e' stata lanciata e quindi la rilanciamo
			// se invece la exception e' nulla va tutto bene e ritorniamo il valore
			return value;
		}
	}

	@Override
	public boolean isDone() {
		synchronized (mutex) {
			return done;
		}
	}
	
	// il set del valore o dell'eccezione non sono comprese dall'interfaccia, e quindi verranno realizzati qui internamente
	// ad ogni modo avranno una visibilita' protected
	void setValue(Type object) {
		synchronized (mutex) {
			if (done) throw new IllegalStateException("done == true"); // bug, non puo' essere gia' stato messo a true
			value = object;
			done = true;
			mutex.notifyAll(); // si svegliano tutti
		}
	}
	
	void setException(Throwable throwable) {
		if (throwable == null) throw new 
	}

}
