package Example03;

import concurrency.ArrayBlockingQueue;
import concurrency.BlockingQueue;

public class Example03 {

    public void go() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        
        for (int i=0; i<5; i++) {
            Consumer consumer = new Consumer(i+1, queue);
            new Thread(consumer).start();
        }

        for (int i=0; i<5; i++) {
            Producer producer = new Producer(i+1, queue);
            new Thread(producer).start();
        }
    }

    public static void main(String[] args) {
        new Example03().go();
    }
}