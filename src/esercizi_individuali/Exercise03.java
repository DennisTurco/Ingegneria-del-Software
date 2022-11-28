package esercizi_individuali;

import it.unipr.informatica.concurrent.ArrayBlockingQueue;

public class Exercise03 {
	
	private void go() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
		
		// creo i 5 consumatori
		for(int i=1; i<=5; ++i) {
			Exercise03_Consumer consumer = new Exercise03_Consumer(queue, i);
			new Thread(consumer).start();
			
		}
		
		// creo i 5 produttori
		for(int i=1; i<=5; ++i) {
			Exercise03_Producer producer = new Exercise03_Producer(queue, i);
			new Thread(producer).start();
		}
	}
	
	public static final void main(String[] args) {
		new Exercise03().go();
	}
}
