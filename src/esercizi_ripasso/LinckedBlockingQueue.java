package esercizi_ripasso;

import java.util.LinkedList;

class LinckedBlockingQueue<Type> implements BlockingQueue<Type>{
	
	private LinkedList<Type> queue;
	
	public LinckedBlockingQueue() {
		this.queue = new LinkedList<>();
	}

	@Override
	public void put(Type type) throws InterruptedException, ClassCastException, NullPointerException, IllegalArgumentException {
		synchronized (queue) {
			queue.addLast(type);
			if(queue.size() == 1) queue.notifyAll(); // se la size era 0 allora potrei avere dei thread in wait (riga 27), quindi li risveglio. se la size è maggiore di 1 non ho thread in wait e quindi non ho necessità di risvegliare nessuno
		}
	}

	@Override
	public Type take() throws InterruptedException {
		synchronized (queue) {
			while(queue.size() == 0) queue.wait();
			Type value = queue.removeFirst();
			if(queue.size() > 0) queue.notifyAll(); // se per esempio size = 1 esistono più thread (thread 1 e 2) che intanto possono eseguire take e intanto per esempio thread 2 far tornare size = 0 ed in tal caso thread 1 non dovrà risvegliarlo
			return value;
		}
		
	}

	@Override
	public int remainingCapacity() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isEmpty() {
		synchronized (queue) {
			 return queue.isEmpty();
		}
		
	}

	@Override
	public void clear() {
		synchronized (queue) {
			queue.clear();
		}
	}
}


