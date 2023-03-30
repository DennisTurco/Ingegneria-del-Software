package RIFACCIO;

public class Example03 {
    
    private void go() {

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        // creo 5 consumatori
        for (int i=0; i<5; i++) {
            Producer producer = new Producer(i, queue);
            new Thread(producer).start();
        }

        // creo 5 produttori
        for (int i=0; i<5; i++) {
            Consumer consumer = new Consumer(i, queue);
            new Thread(consumer).start();
        }
    }

    public static void main() {
        new Example03().go();
    }
}