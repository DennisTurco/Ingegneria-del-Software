package it.unipr.informatica.concurrent;

import it.unipr.informatica.concurrent.atomic.AtomicReference;

public final class SimpleThreadPool implements ThreadPool {
	private Thread[] threads;

	private LinkedBlockingQueue<Runnable> runnables;

	private AtomicReference<Boolean> started;

	public SimpleThreadPool(int size) {
		if (size < 1)
			throw new IllegalArgumentException("size < 1");

		this.started = new AtomicReference<Boolean>(false);

		this.runnables = new LinkedBlockingQueue<>();

		this.threads = new InnerThread[size];
	}

	@Override
	public void start() {
		if (started.get())
			throw new IllegalStateException("started.get()");

		started.set(true);

		for (int i = 0; i < threads.length; ++i) {
			InnerThread thread = new InnerThread();

			threads[i] = thread;

			thread.start();
		}
	}

	@Override
	public void stop() {
		if (!started.get())
			throw new IllegalStateException("!started.get()");

		started.set(false);
		
		for (int i = 0; i < threads.length; ++i) {
			threads[i].interrupt();

			threads[i] = null;
		}
	}

	@Override
	public void execute(Runnable runnable) {
		if (!started.get())
			throw new IllegalStateException("!started.get()");
		
		runnables.enqueue(runnable);
	}

	private class InnerThread extends Thread {
		@Override
		public void run() {
			for (;;) {
				if (isInterrupted() || started.get() == false)
					return;

				try {
					Runnable task = runnables.dequeue();

					task.run();
				} catch (InterruptedException interruptedException) {
					return;
				} catch (Throwable throwable) {
					throwable.printStackTrace();
				}
			}
		}
	}
}
