package Esami.Esame10e12_01_23.Laboratorio;

import java.util.LinkedList;

public class LinkedBlockingQueue<T> implements BlockingQueue<T> {
	
	private LinkedList<T> queue;
	
	public LinkedBlockingQueue() {
		this.queue = new LinkedList<>();
	}
	
	@Override
	public void push(T elem) {
		synchronized (queue) {
			queue.addLast(elem);
			
			if (queue.size() == 1) queue.notify();
		}
	}

	@Override
	public T pop() {
		synchronized (queue) {
			while (queue.size() == 0) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					return null;
				}
			}
			
			T result = queue.removeFirst();
			
			if (queue.size() > 0) queue.notify();
			
			return result;
		}
	}

}
