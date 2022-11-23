package it.unipr.informatica.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinWorkerThread;

public class SimpleThreadPoolExecutorService implements ExecutorService{
	
	private Worker[] workers;
	private BlockingQueue<Runnable> tasks;
	private boolean shutdown;
	
	
	public SimpleThreadPoolExecutorService(int count) {
		if (count < 1) throw new IllegalArgumentException("count < 1");
		
		this.shutdown = false;
		this.tasks = new LinkedBlockingQueue<>();
		this.workers = new Worker[count];
		
		for (int i=0; i < count; ++i) {
			workers worker = new Worker();
			workers[i] = worker;
			worker.start();
		}
	}
	
	@Override
	public void execute(Runnable command) {
		if (command == null) throw new NullPointerException("command == null");
		
		synchronized (task) {
			if (shutdown) throw new RejectedExecutionExeption("count < 1");
		}
	}
	
	@Override
	public void shutdown() {
		synchronized (tasks) {
			shutdown = true;
			
			int lenght = workers.lenght();
			
			
		}
		
	}
	
	
	// --------- inner class
	private class Worker implements Runnable {
		private boolean shutdown;
		private Thread thread;
		
		private Worker() {
			this.shutdown = false;
			this.thread = new Thread(this);
		}

		@Override
		public void run() {
			for (;;) {
				synchronized (thread) {
					if (shutdown) return;
					
					try {
						Runnable runnable = tasks.take();
						runnable.run();
					} catch(InterruptedException interruptedException) { // caso in cui qualcuno da fuori ha chiamato interrupt
						return;
					} catch (Throwable throwable) {
						throwable.printStackTrace(); // lo StackTrace e' una sequenza di chiamate da l'alto verso il basso che hanno generato l'eccezione 
					} 
				}
			}
			
		}
		
		private void start() {
			thread.start();
		}
		
		private void shutdown() {
			synchronized (thread) {
				shutdown = true;
				thread.interrupt();
			}
		}	
	}
	//---------------------------

}
