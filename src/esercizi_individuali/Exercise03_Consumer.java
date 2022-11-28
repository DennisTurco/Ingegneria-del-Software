package esercizi_individuali;

import it.unipr.informatica.concurrent.ArrayBlockingQueue;

public class Exercise03_Consumer implements Runnable{
	
	private ArrayBlockingQueue<String> queue;
	private int id;
	
	Exercise03_Consumer(ArrayBlockingQueue<String> queue, int id) {
		if (id < 0) throw new IllegalArgumentException("id < 0");
		if (queue == null) throw new IllegalArgumentException("queue == null");
		
		this.id = id;
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			for (int i=0; i<5; ++i) {
				System.out.println("C" + id + " receiving..."); 
				String message = queue.take();
				System.out.println("C" + id + " received " + message); 
				Thread.sleep(40 + (int) (50 * Math.random()));
			}
			
		} catch (InterruptedException interrupted) { 
			System.err.println("C" + id + " interrupted"); 
		} 
	}

}
