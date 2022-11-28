package it.unipr.informatica.concurrent;

import java.util.concurrent.Callable;

public interface ExecutorService extends Executor {
	public void shutdown();
	public Future<?> submit(Runnable task); // permette di mettersi in attesa che il Runnable sia terminato
	public <Type> Future<Type> FormSubmitEvent(Callable<Type> task);
}
