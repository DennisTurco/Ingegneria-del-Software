package Esame10_01_23.Esercizio1;

public abstract class BridgeQueue {
	protected Queue queue;
	
	protected BridgeQueue(Queue queue) {
		this.queue = queue;
	}
	
	abstract public void push(Object value);
	abstract public Object pop();
	abstract public boolean isEmpty();
}