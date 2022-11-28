package it.unipr.informatica.concurrent.locks;

// i metodi final sono metodi che non possono essere modificati dalle override quindi dalle sottoclassi
// funziona in opposto del c++: in c++ di base i metodi non vanno in override, mentre in java di base si

// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Condition.html

public interface Condition {
	public void await() throws InterruptedException; // in realtà si dovrebbe chiamare wait
	
	public void signal();
	
	public void signalAll();
}
