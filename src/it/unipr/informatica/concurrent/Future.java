package it.unipr.informatica.concurrent;

import java.util.concurrent.ExecutionException;

public interface Future<Type> {
	public Type get() throws InterruptedException, ExecutionException;
	public boolean isDone();
}