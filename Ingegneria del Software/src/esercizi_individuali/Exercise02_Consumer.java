package esercizi_individuali;

import it.unipr.informatica.concurrent.BlockingQueue;

public class Exercise02_Consumer implements Runnable{
	
	private BlockingQueue<String> queue;
	private int id;
	
	public Exercise02_Consumer(BlockingQueue<String> queue, int id) {
		if (id < 0) throw new IllegalArgumentException("id < 0");
		if (queue == null) throw new IllegalArgumentException("queue = null");
		
		this.queue = queue;
		this.id = id;
	}
	
	@Override
	public void run() {
		try {
			for (int i=0; i<5; i++) {
				System.out.println("C" + id + " receiving...");
				String message = queue.take();
				System.out.println("C" + id + " received: " + message);
				
				Thread.sleep(100 + (int) (50 * Math.random())); // random da 100 a 150ms
			}
		} catch(Throwable t) {}
	}

}