package esercizi_individuali;

import it.unipr.informatica.concurrent.ArrayBlockingQueue;

public class Exercise03_Producer implements Runnable{
	
	private ArrayBlockingQueue<String> queue;
	private int id;
	
	public Exercise03_Producer(ArrayBlockingQueue<String> queue, int id) {
		if (id < 0) throw new IllegalArgumentException("id < 0");
		if (queue == null) throw new IllegalArgumentException("queue == null");
		
		this.queue = queue;
		this.id = id;
	}
	
	@Override
	public void run() {
		try {
			for (int i = 0; i < 5; ++i) {
				String message = "msg/" + i;

				System.out.println("P" + id + " send " + message);

				queue.put(message);

				System.out.println("P" + id + " sent " + message);

				Thread.sleep(100 + (int) (50 * Math.random()));
			}
		} catch (InterruptedException interrupted) {
			System.err.println("P" + id + " interrupted");
		}
	}
	
}
