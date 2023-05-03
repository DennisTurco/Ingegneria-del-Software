package Esame10_01_23.Esercizio1;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedQueue implements Queue {
	
	private List<Object> queue;
	
	public LinkedQueue() {
		this.queue = new LinkedList<>();
	}

	@Override
	public boolean isEmpty() {
		return queue.size() == 0;
	}

	@Override
	public void push(Object o) {
		if (o == null) throw new NullPointerException("o == null");
		
		queue.add(o);
	}

	@Override
	public Object pop() throws NoSuchElementException {
		if (queue.size() == 0) throw new NoSuchElementException("queue.size() == 0");
		
		Object result = queue.remove(queue.size()-1);
		return result;
	}

}
