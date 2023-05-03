package Esame10_01_23.Esercizio1;

public abstract class Bridge {
	protected Queue queue;
	
	protected Bridge(Queue queue) {
		this.queue = queue;
	}
	
	public abstract void create();
}
