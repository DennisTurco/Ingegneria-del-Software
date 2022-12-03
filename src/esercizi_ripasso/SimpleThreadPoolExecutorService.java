package esercizi_ripasso;

import java.util.concurrent.ForkJoinWorkerThread;

import javax.sound.midi.VoiceStatus;

public class SimpleThreadPoolExecutorService implements ExecutorService{
	private Worker[] workers;
	private BlockingQueue<Runnable> tasks;
	private boolean shutdown;
	
	public SimpleThreadPoolExecutorService(int count) {
		if (count < 1) throw new IllegalArgumentException("count < 1");
		
		this.shutdown = false;
		this.tasks = new LinckedBlockingQueue<>();
		this.workers = new Worker[count];
		
		for (int i=0; i<count; ++i) {
			Worker worker = new Worker();
			worker[i] = worker;
			worker.start();
		}
	}
	
	
	@Override
	public void execute(Runnable command) {
		if (command == null) throw new NullPointerException("command == null");
		
		synchronized (tasks) {
			// shutdown non può essere true, in alternativa eccezione
			if(shutdown) throw new RejectedExecutionException("shutdown == true");
		
			try {
				tasks.put(command);
			} catch(InterruptedException interruptedException) {
				// blank
			}
		}
	}

	@Override
	public void shutdown() {
		synchronized (tasks) {
			shutdown = true;
			int length = workers.length;
			for(int i=0; i<length; ++i) {
				workers[i].shutdown();
			}
		}
	}
	
	
	// INNER CLASS
	private class Worker implements Runnable{
		
		private boolean shutdown;
		private Thread thread;
		
		private Worker() {
			this.shutdown = false;
			this.thread = new Thread(this);
		}
		
		@Override
		public void run() {
			// ciclo infinito
			for(;;) {
				synchronized (thread) {
					if (shutdown) return;
				}
				
				try {
					Runnable runnable = tasks.take();
					runnable.run();
				} catch(InterruptedException interruptedException) {
					return;
				} catch(Throwable throwable) {
					throwable.printStackTrace(); 
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

	
}
