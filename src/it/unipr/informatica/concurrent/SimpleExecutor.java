/*
 * SimpleExecutor
 * 
 * (c) 2021 Federico Bergenti. All Rights Reserved.
 */
package it.unipr.informatica.concurrent;

public final class SimpleExecutor implements Executor {
	private ThreadPool threadPool;
	
	public SimpleExecutor(int size) {
		if (size < 1)
			throw new IllegalArgumentException("size < 1");
		
		this.threadPool = new SimpleThreadPool(size);
	}

	@Override
	public void start() {
		threadPool.start();
	}

	@Override
	public void stop() {
		threadPool.stop();
	}
	
	@Override
	public void execute(Runnable task) {
		if(task == null)
			throw new IllegalArgumentException("task == null");
		
		threadPool.execute(task);
	}

	@Override
	public <T> void execute(Task<T> task, Callback<T> callback) {
		if(task == null)
			throw new IllegalArgumentException("task == null");

		if(callback == null)
			throw new IllegalArgumentException("callback == null");

		threadPool.execute(new CallbackTask<T>(task, callback));
	}

	private static class CallbackTask<T> implements Runnable {
		private Task<T> task;
		
		private Callback<T> callback;
		
		private CallbackTask(Task<T> task, Callback<T> callback) {
			this.task = task;
			
			this.callback = callback;
		}
		
		@Override
		public void run() {
			try {
				T result = task.run();
				
				callback.onSuccess(result);
			} catch(Throwable throwable) {
				callback.onFailure(throwable);
			}
		}
	}
	
	@Override
	public <T> Future<T> execute(Task<T> task) {
		if(task == null)
			throw new IllegalArgumentException("task == null");

		InnerFuture<T> result = new InnerFuture<>();
		
		threadPool.execute(new FutureTask<T>(task, result));
		
		return result;
	}

	private static class InnerFuture<T> implements Future<T> {
		private T value;
		
		private Throwable throwable;
		
		private Object lock;
		
		private InnerFuture() {
			this.value = null;
			
			this.throwable = null;
			
			this.lock = new Object();
		}
		
		@Override
		public T get() throws Throwable {
			synchronized(lock) {
				while (value == null && throwable == null)
					lock.wait();
				
				if (throwable != null)
					throw throwable;
				
				return value;
			}
		}
		
		private void set(T value) {
			synchronized(lock) {
				this.value = value;
				
				lock.notifyAll();
			}
		}

		private void set(Throwable throwable) {
			synchronized(lock) {
				this.throwable = throwable;
				
				lock.notifyAll();
			}
		}
	}

	private static class FutureTask<T> implements Runnable {
		private Task<T> task;
		
		private InnerFuture<T> future;
		
		private FutureTask(Task<T> task, InnerFuture<T> future) {
			this.task = task;
			
			this.future = future;
		}
		
		@Override
		public void run() {
			try {
				T result = task.run();
				
				future.set(result);
			} catch(Throwable throwable) {
				future.set(throwable);
			}
		}
	}
	
	@Override
	public <T> FuturePool<T> execute(Task<T>[] tasks) {
		if(tasks == null)
			throw new IllegalArgumentException("tasks == null");

		InnerFuturePool<T> result = new InnerFuturePool<>();

		for(int i = 0; i < tasks.length; ++i)			
			threadPool.execute(new FuturePoolTask<T>(tasks[i], result));
		
		return result;
	}
	
	private static class InnerFuturePool<T> implements FuturePool<T> {
		private LinkedBlockingQueue<Future<T>> futures;
		
		private InnerFuturePool() {
			this.futures = new LinkedBlockingQueue<>();
		}
		
		@Override
		public T get() throws Throwable {
			Future<T> future = futures.dequeue();

			return future.get();
		}
		
		private void add(Future<T> future) {
			futures.enqueue(future);
		}
	}
	
	private static class FuturePoolTask<T> implements Runnable {
		private Task<T> task;
		
		private InnerFuturePool<T> futures;
		
		private FuturePoolTask(Task<T> task, InnerFuturePool<T> futures) {
			this.task = task;
			
			this.futures = futures;
		}
		
		@Override
		public void run() {			
			InnerFuture<T> future = new InnerFuture<>();

			try {
				T result = task.run();
				
				futures.add(future);

				future.set(result);
			} catch(Throwable throwable) {
				futures.add(future);

				future.set(throwable);
			}
		}
	}
}
