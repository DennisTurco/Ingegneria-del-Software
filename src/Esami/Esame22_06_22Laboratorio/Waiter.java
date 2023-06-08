package Esami.Esame22_06_22Laboratorio;

public class Waiter implements Runnable {
	private int counter;
	private Barrier barrier;
	private Thread thread;
	
	public Waiter(Barrier barrier) {
		this.counter = 1;
		this.barrier = barrier;
		this.thread = new Thread(this);
	}

	@Override
	public void run() {
		while (true) {
			try {
				barrier.await();
			} catch (InterruptedException e) {
				System.err.println(Thread.currentThread().getName() + " interrupted");
				return;
			}
			
			System.out.println("counter: " + counter);
			
			++counter;
		}
	}
	
	public void start() {
		thread.start();
	}
	
	public void shutdown() {
		thread.interrupt();
	}
}
