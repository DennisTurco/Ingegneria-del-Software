/*
 * BlockingQueue<T>
 * 
 * */

package it.unipr.informatica.concurrent;

public interface BlockingQueue<T> {
	
	public void put(T e) throws InterruptedException;
	
	public T take() throws InterruptedException;
	
	public int remainingCapacity();
	
	public boolean isEmpty();
	
	public void clear();
}
