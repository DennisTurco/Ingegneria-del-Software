package Esami.Esame10_01_23.Esercizio1;

public class MyQueue extends BridgeQueue{

	protected MyQueue(Queue queue) {
		super(queue);
	}

	@Override
	public void push(Object value) {
		queue.push(value);
	}

	@Override
	public Object pop() {
		return queue.pop();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

}
