package Esame10_01_23.Esercizio1;

public class Example {
	
	private void go() {
		
		//BridgeQueue queue = new Array(new ArrayQueue(5));
		BridgeQueue queue = new MyQueue(new LinkedQueue());
		
		System.out.println(queue.getClass().getName());
		
		queue.push(1);
		queue.push(2);
		queue.push(3);
		queue.push(4);
		queue.push(5);
		
		// stampa
		for (int i=0; i<5; ++i) {
			System.out.println(queue.pop() + " ");
		}
	}
	
	public static void main(String[] args) {
		new Example().go();
	}
}