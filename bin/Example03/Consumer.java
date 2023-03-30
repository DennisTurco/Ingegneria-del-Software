package Example03;
import concurrency.BlockingQueue;

public class Consumer implements Runnable{
    private BlockingQueue<String> queue;
    private int id;

    public Consumer(int id, BlockingQueue<String> queue) {    
        if (queue == null) throw new IllegalArgumentException("queue == null");
        if (id < 0) throw new IllegalArgumentException("id < 0");

        this.queue = queue;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; ++i) {
				String message = queue.take();
				System.out.println("Consumer" + id + " received " + message);
				Thread.sleep(40 + (int) (100 * Math.random()));
			}

        } catch(InterruptedException interrupted) {
            System.err.println("Consumer: " + id + " interrupted!");
            interrupted.printStackTrace();
        }
    }
}