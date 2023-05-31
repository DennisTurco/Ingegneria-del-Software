package Esami.Esame10e12_01_23.Laboratorio;

public class SimpleThreadPool implements ExecutorService {
	
	private BlockingQueue<Runnable> tasks;
	private Worker[] workers;
	private boolean shutdown;
	
	public SimpleThreadPool(int count) {
		if (count < 1) throw new IllegalArgumentException("count < 1");
		
		this.shutdown = false;
		this.tasks = new LinkedBlockingQueue<>();
		this.workers = new Worker[count];
		
		for (int i=0; i<count; ++i) {
			Worker worker = new Worker();
			worker.start();
			workers[i] = worker;
		}
	}
	
	@Override
	public void shutdown() {
		synchronized (this) {
			shutdown = true;
			
			for (int i=0; i<workers.length; ++i) {
				workers[i].shutdown();
			}
		}
	}

	@Override
	public void execute(Runnable command) {
		synchronized (this) {
			if (shutdown) throw new IllegalStateException("shutdown == true");
			
			tasks.push(command);
		}
	}
	
	// INNER CLASS
	private class Worker implements Runnable {
		
		private Thread thread;
		private boolean shutdown;
		
		private Worker() {
			this.shutdown = false;
			this.thread = new Thread(this);
		}
		
		@Override
		public void run() {
			while(true) {
				if (shutdown) return;
				
				synchronized (this) {
					try {
						System.out.println("Threard: " + Thread.currentThread().getName() + " starting command");
						
						Runnable command = tasks.pop();
						command.run();
						
					} catch (Throwable exception) {
						return;
					}
					
				}
			}
		}
		
		private void start() {
			thread.start();
		}
		
		private void shutdown() {
			shutdown = true;
			thread.interrupt();
		}
	}
	
}
