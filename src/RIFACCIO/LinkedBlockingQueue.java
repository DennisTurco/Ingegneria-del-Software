package RIFACCIO;

import java.util.LinkedList;

public class LinkedBlockingQueue<T> implements BlockingQueue<T> {

    private LinkedList<T> queue;

    public LinkedBlockingQueue() {
        this.queue = new LinkedList<>();
    }

    @Override
    public void put(T elem) throws InterruptedException {
        synchronized (queue) {
            queue.addLast(elem);

            if (queue.size() == 1) queue.notify();

        }
    }

    @Override
    public T take() throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == 0) queue.wait();

            T object = queue.removeFirst();

            if (queue.size() > 0) queue.notify();
            
            return object;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (queue) {
            return queue.isEmpty();
        }
    }

    @Override
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void clear() {
        synchronized (queue) {
            queue.clear();
        }
    }

}