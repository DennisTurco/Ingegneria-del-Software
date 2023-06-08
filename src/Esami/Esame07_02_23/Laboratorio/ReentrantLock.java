package Esami.Esame07_02_23.Laboratorio;

public class ReentrantLock implements Lock {
	
	private final Object mutex;
	private int count;
	private Thread owner;
	
	public ReentrantLock() {
		this.mutex = new Object();
		this.count = 0;
		this.owner = null;
	}
	
	@Override
	public void lock() throws InterruptedException {
		Thread currentThread = Thread.currentThread();
		synchronized (mutex) {
			while (owner != null && owner != currentThread) {
				mutex.wait();
			}
			
			if (owner == null) {
				owner = currentThread;
			}
			
			++count;
		}
	}

	@Override
	public void unlock() {
		synchronized (mutex) {
			if (owner != Thread.currentThread()) throw new IllegalStateException("owner != Thread.currentThread()");
		
			--count;
			
			if (count == 0) {
				owner = null;
				mutex.notify();
			}
		}
	}
	
	public boolean isCurrentThreadOwner() {
		synchronized (this) {
			return owner == Thread.currentThread();
		}
	}
}