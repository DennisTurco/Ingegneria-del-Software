package concurrency;
import java.util.LinkedList;

public class LinkedBlockingQueue<Type> implements BlockingQueue<Type>{
    private LinkedList<Type> queue;
    
    public LinkedBlockingQueue() {
        this.queue = new LinkedList<>();
    }

    @Override
    public void put(Type elem) throws InterruptedException {
        synchronized (queue) {
            queue.addLast(elem);

            if (queue.size() == 1) {
                queue.notify();
            }
        }
    }

    @Override
    public Type take() throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == 0) {
                queue.wait();
            }

            Type object = queue.removeFirst();

            if (queue.size() > 0) {
                queue.notify(); //per essere certi avremmo potuto mettere notifyAll()
            }

            return object;
        }
    }

    @Override
    public int remainingCapacity() {
        synchronized (queue) {
            //return Integer.MAX_VALUE-queue.size(); // ??
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (queue) {
            return queue.size() == 0;
        }
    }

    @Override
    public void clear() {
        synchronized (queue) { 
            // svuota tutta la coda in mutua esclusione

			// queue = null; // tolgo riferimenti alla queue
			// questo non lo facciamo perche' la clear pulisce semplicemente la coda
            
            queue.clear();
        }
    }

}