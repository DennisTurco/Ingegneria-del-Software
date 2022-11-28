package esercizi_individuali;

import it.unipr.informatica.concurrent.BlockingQueue;
import it.unipr.informatica.concurrent.LinkedBlockingQueue;

public class Exercise02 {
	private void go() {
		BlockingQueue<String> queue = new LinkedBlockingQueue<>();
		
		// creo 5 consumer
		for (int i=1; i<=5; ++i) {
			Exercise02_Consumer consumer = new Exercise02_Consumer(queue, i);
			new Thread(consumer).start();
		}
				
		// creo 5 producer
		for (int i=1; i<=5; ++i) {
			Exercise02_Producer producer = new Exercise02_Producer(queue, i);
			new Thread(producer).start();
		}
		
		
	}
	
	public static final void main(String[] argv) {
		new Exercise02().go();
	}
}