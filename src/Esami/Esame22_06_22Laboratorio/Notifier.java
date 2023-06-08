package Esami.Esame22_06_22Laboratorio;

public class Notifier implements Runnable {
	
	private Object object;
	private Thread thread;
	private Barrier barrier;
	
	public Notifier(Object object, Barrier barrier) {
		this.object = object;
		this.thread = new Thread(this);
		this.barrier = barrier;
	}

	@Override
	public void run() {
		while (true) {
			
			long value = (int) (Math.random()*100);
			
			try {
				Thread.sleep(value);
			} catch (InterruptedException e) {
				System.err.println(Thread.currentThread().getName() + " interrupted");
				return;
			}
			
			synchronized (object) {
				object.notify();
			}
			
			synchronized (barrier) {
				barrier.notify();
			}
		}
	}
	
	public void start() {
		thread.start();
	}
	
	public void shutdown() {
		thread.interrupt();
	}
	
}
