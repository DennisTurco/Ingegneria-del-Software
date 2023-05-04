package Esame10_01_23.Esercizio1;

import java.util.NoSuchElementException;

public abstract class QueueBridge {
	protected Queue queue;
	
	protected QueueBridge(Queue queue) {
		this.queue = queue;
	}
	
	abstract boolean isEmpty();
	abstract void push(Object o);
	abstract Object pop() throws NoSuchElementException;
}
