package Esami.Esame10e12_01_23.Esercizio1;

public abstract class BridgeQueue {
	protected Queue queue;
	
	protected BridgeQueue(Queue queue) {
		this.queue = queue;
	}
	
	public abstract void push(Object value);
	public abstract Object pop();
	public abstract boolean isEmpty();
}