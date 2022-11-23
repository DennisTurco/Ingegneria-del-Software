package esercizi_individuali;

import it.unipr.informatica.concurrent.BlockingQueue;

public class Exercise02_Producer implements Runnable {
	
	private int id;
	private BlockingQueue<String> queue;
	

	public Exercise02_Producer(BlockingQueue<String> queue, int id) {
		if (id < 0) throw new IllegalArgumentException("id < 0");
		if (queue == null) throw new IllegalArgumentException("queue = null");
		
		this.queue = queue;
		this.id = id;
	}
	
	@Override
	public void run() {
		try {
			for (int i=1; i<=5; ++i) {
				String message = id + "/" + i;
				
				System.out.println("P" + id + " sending: " + message);
				queue.put(message);
				System.out.println("P" + id + " sent: " + message);
				
				Thread.sleep(100 + (int) (50 * Math.random())); // random da 100 a 150ms
			}
		} catch(Throwable throwable) {}
		
	}

}