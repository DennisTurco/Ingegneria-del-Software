package RIFACCIO;

public class Producer implements Runnable{
    
    private int id;
    private BlockingQueue<String> queue;
    
    public Producer(int id, BlockingQueue<String> queue) {
        if (id < 0) throw new IllegalArgumentException("id < 0");
        if (queue == null) throw new IllegalArgumentException("queue == null");

        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() { 
            try {
                for (int i=0; i<5; ++i) {
                    String message = "message" + id;
                    System.out.println("Producer: " + id + " sending " + message);
                    queue.put(message);
                    System.out.println("Producer: " + id + " sent " + message);
                    Thread.sleep(100 + (int) (50 * Math.random()));
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }

}
