package Example02;
import concurrency.BlockingQueue;
import concurrency.LinkedBlockingQueue;

public class Exercise02 {
    
    public void go() {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // creo 5 consumer
        for (int i=0; i<5; i++) {
            Consumer consumer = new Consumer(i+1, queue);
            new Thread(consumer).start();
        }

        // creo 5 producer
        for (int i=0; i<5; i++) {
            Producer producer = new Producer(i+1, queue);
            new Thread(producer).start();
        }
    }
    
    public static void main(String[] args) {
        new Exercise02().go();
    }
}