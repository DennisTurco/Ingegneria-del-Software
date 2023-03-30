package Example02;
import concurrency.BlockingQueue;

public class Producer implements Runnable{
    private BlockingQueue<String> queue;
    private int id;

    public Producer(int id, BlockingQueue<String> queue) {    
        if (queue == null) throw new IllegalArgumentException("queue == null");
        if (id < 0) throw new IllegalArgumentException("id < 0");

        this.queue = queue;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            for (int i=0; i<5; i++) {
                String message = id + "/" + 1;
                System.out.println("Producer" + id + " sending" + message);
                queue.put(message);
                System.out.println("Producer" + id + " sent" + message);
                Thread.sleep(100 + (int) (50 * Math.random()));
            }

        } catch(InterruptedException interrupted) {
            System.err.println("Producer: " + id + " interrupted!");
            interrupted.printStackTrace();
        }
    }
}
