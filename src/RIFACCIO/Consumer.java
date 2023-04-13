package RIFACCIO;

public class Consumer implements Runnable{

    private int id;
    private BlockingQueue<String> queue;

    public Consumer(int id, BlockingQueue<String> queue) {
        if (id < 0) throw new IllegalArgumentException("id < 0");
        if (queue == null) throw new IllegalArgumentException("queue == null");

        this.id = id;
        this.queue = queue;
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
