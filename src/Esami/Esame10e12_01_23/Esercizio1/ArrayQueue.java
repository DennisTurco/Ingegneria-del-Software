package Esami.Esame10e12_01_23.Esercizio1;

import java.util.NoSuchElementException;

public class ArrayQueue implements Queue {
	
	private Object[] queue;
	private int counter;
	private int size;
	
	public ArrayQueue(int size) {
		if (size < 1) throw new IllegalArgumentException("size < 1");
		
		this.counter = 0;
		this.size = size;
		this.queue = new Object[size];
	}
	
	@Override
	public boolean isEmpty() {
		return (counter == 0);
	}

	@Override
	public void push(Object o) {
		if (o == null) throw new NullPointerException("o == null");
		if (counter == size) throw new IllegalStateException("counter == size");
		
		queue[counter] = o;
		counter++;
	}

	@Override
	public Object pop() throws NoSuchElementException {
		
		if (counter == 0) throw new NoSuchElementException("counter == 0");
			
		Object result = queue[counter-1];
		counter--;
		return result;
	}

}