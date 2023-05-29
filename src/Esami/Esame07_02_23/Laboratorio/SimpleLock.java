package Esami.Esame07_02_23.Laboratorio;

public class SimpleLock implements Lock {
	
	private Thread owner;
	private int counter;
	
	public SimpleLock() {
		this.owner = null;
		this.counter = 0;
	}
	
	@Override
	public void lock() throws InterruptedException {
		synchronized (this) {
			if (counter < 0) throw new IllegalStateException("count < 0");
			
			Thread currentThread = Thread.currentThread();
			
			while (owner != null && owner != currentThread) {
				this.wait();
			}
			
			if (owner == null) {
				owner = currentThread;
			}
			
			counter++;
		}
	}
	
	@Override
	public void unlock() {
		synchronized (this) {
			if (counter <= 0) throw new IllegalStateException("count <= 0");
			if (Thread.currentThread() != owner) throw new IllegalMonitorStateException("Thread.currentThread() != owner");
			
			counter--;	
			
			if (counter == 0) {
				owner = null;
				this.notify();
			}	
		}
	}
	
	public boolean isCurrentThreadOwner() {
		synchronized (this) {
			return owner == Thread.currentThread();
		}
	}

}
