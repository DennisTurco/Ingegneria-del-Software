package it.unipr.informatica.concurrent;

import java.util.LinkedList;

// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html

public class LinkedBlockingQueue<T> implements BlockingQueue<T> {
	
	private LinkedList<T> queue;
	
	public LinkedBlockingQueue() {
		this.queue = new LinkedList<>();
	}
	
	@Override
	public T take() throws InterruptedException {

		synchronized (queue) {
				
			while(queue.size() == 0) queue.wait();
			
			T objecT = queue.removeFirst();
			
			if (queue.size() > 0) queue.notify(); //per essere certi avremmo potuto mettere notifyAll()
			
			return objecT;
		}
	}
	
	@Override
	public void put(T object) throws InterruptedException {
		synchronized (queue) {
			queue.addLast(object);
			
			if (queue.size() == 1) queue.notify();
		}
	}

	@Override
	public void clear() {
		synchronized (queue) {
			queue.clear(); // svuota tutta la coda in mutua esclusione
			
			// queue = null; // tolgo riferimenti alla queue
			// questo non lo facciamo perche' la clear pulisce semplicemente la coda
		}
		
	}
	
	@Override
	public int remainingCapacity() {
		return Integer.MAX_VALUE; // ritorno il valore massimo ammissibile
	}

	@Override
	public boolean isEmpty() {
		synchronized (queue) {
			return queue.isEmpty();
		}
	}

}
