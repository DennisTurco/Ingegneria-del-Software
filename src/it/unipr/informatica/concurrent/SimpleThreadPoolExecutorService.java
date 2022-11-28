package it.unipr.informatica.concurrent;

import java.util.concurrent.Callable;

public class SimpleThreadPoolExecutorService implements ExecutorService{
	
	private Worker[] workers;
	private LinkedBlockingQueue<Runnable> tasks;
	private boolean shutdown;
	
	
	SimpleThreadPoolExecutorService(int count) {
		if (count < 1) throw new IllegalArgumentException("count < 1");
		
		this.shutdown = false;
		this.tasks = new LinkedBlockingQueue<>();
		this.workers = new Worker[count];
		
		for (int i=0; i < count; ++i) {
			Worker worker = new Worker();
			workers[i] = worker;
			worker.start();
		}
	}
	
	@Override
	public void execute(Runnable command) {
		if (command == null) throw new NullPointerException("command == null");

		synchronized (tasks) {
			if (shutdown) throw new RejectedExecutionException("shutdown == true");

			tasks.put(command);
		}
	}
	
	@Override
	public void shutdown() {
		synchronized (tasks) {
			shutdown = true;

			int length = workers.length;

			for (int i = 0; i < length; ++i)
				workers[i].interrupt();
		}
	}
	
	@Override
	public Future<?> submit(Runnable task) {		
		
		if (task == null) throwable new NullPointerException("task == null");
		
		SimpleFuture<?> future = new SimpleFuture<>();
		
		// lambda-expression (implementazione di Runnable)
		execute(() -> {
			try {
				task.run();
				future.setValue(null);
			} catch(Throwable throwable) {
				future.setException(throwable);
			}
		});
		
		return future;
		
	}
	
	@Override
	public submit(Runnable task, Callback<?> callback) {		
		
		if (task == null) throwable new NullPointerException("task == null");
		
		// lambda-expression (implementazione di Runnable)
		execute(() -> {
			try {
				task.run();
				callback.onSuccess(null);
			} catch(Throwable throwable) {
				callback.onFailure(throwable);
			}
		});
		
		return future;
		
	}

	@Override
	public <Type> Future<Type> submit(Callable<Type> task) {
		
		if (task == null) throwable new NullPointerException("task == null");
		
		SimpleFuture<Type> future = new SimpleFuture<>();
		
		// lambda-expression (implementazione di Runnable)
		execute(() -> {
			try {
				Type result = task.call();
				future.setValue(result);
			} catch(Throwable throwable) {
				future.setException(throwable);
			}
		});
		
		return future;
	}
	
	
	// --------- inner class
	private class Worker extends Thread {

		@Override
		public void run() {
			for (;;) {
				synchronized (tasks) {
					if (shutdown && tasks.isEmpty()) return;
					
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
	}
	//---------------------------
}
