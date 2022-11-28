package it.unipr.informatica.concurrent;

public interface Executor {
	public void execute(Runnable command);
}
