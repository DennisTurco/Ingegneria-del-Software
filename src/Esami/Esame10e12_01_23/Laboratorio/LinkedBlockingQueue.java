package Esami.Esame10e12_01_23.Laboratorio;

import java.util.LinkedList;

public class LinkedBlockingQueue<T> implements BlockingQueue<T> {
	
	private LinkedList<T> queue;
	
	public LinkedBlockingQueue() {
		this.queue = new LinkedList<>();
	}
	
	@Override
	public T pop() throws InterruptedException {
		synchronized (queue) {
			while (queue.size() == 0) queue.wait();
			
			T result = queue.removeFirst();
			
			if (queue.size() > 0) queue.notify();
			
			return result;
		}
	}

	@Override
	public void push(T elem) {
		synchronized (queue) {
			queue.addLast(elem);
			
			if (queue.size() == 1) queue.notify();
		}
	}

}
