package Esame10_01_23.Esercizio1;

import java.util.NoSuchElementException;

public class MyQueue extends QueueBridge {
	
	Queue type;
	
	protected MyQueue(Queue queue) {
		super(queue);
		this.type = queue;
	}
	
	@Override
	boolean isEmpty() {
		return type.isEmpty();
	}

	@Override
	void push(Object o) {
		type.push(o);
		
	}

	@Override
	Object pop() throws NoSuchElementException {
		return type.pop();
	}
	
	

}
