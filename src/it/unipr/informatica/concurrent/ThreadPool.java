/*
 * ThreadPool
 * 
 * (c) 2021 Federico Bergenti. All Rights Reserved.
 */
package it.unipr.informatica.concurrent;

public interface ThreadPool {
	public void start();

	public void stop();

	public void execute(Runnable runnable);
}
