package RIFACCIO;

public class Example02 {

    private void go() {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // creo i 5 consumer
        for (int i=0; i<5; ++i) {
            Consumer consumer = new Consumer(i+1, queue);
            new Thread(consumer).start();
        }

        // creo i 5 producer
        for (int i=0; i<5; ++i) {
            Producer producer = new Producer(i+1, queue);
            new Thread(producer).start();
        }
    }

    public static void main(String[] args) {
        new Example02().go();
    } 
}