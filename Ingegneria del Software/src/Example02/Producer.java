package Example02;

import it.unipr.informatica.concurrent.BlockingQueue;

class Producer implements Runnable {
	
	private int id;
	private BlockingQueue<String> queue;
	
	Producer(int id, BlockingQueue<String> queue) {
		//controllo errori
		if (id < 0) throw new IllegalArgumentException("id < 0");
		if (queue == null) throw new IllegalArgumentException("queue = null");
		
		// passaggio parametri
		this.id = id;
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			for (int i=0; i<5; ++i) {
				String message = id + "/" + i; 
				System.out.println("P" + id + " sending " + message); 
				
				queue.put(message);
				
				System.out.println("P" + id + " sent " + message); 
				//attesa tra 100ms a 150ms 
				Thread.sleep(100 + (int) (50 * Math.random())); 
			}
			
		} catch (InterruptedException interrupted) {
			System.err.println("Producer " + id + " interrupted"); 
		}
	}

}
