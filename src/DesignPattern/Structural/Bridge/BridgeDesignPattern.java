package DesignPattern.Structural.Bridge;

/*
Goal:
	A Bridge Pattern says that just 
	"decouple the functional abstraction from the implementation so that the two can vary independently".
*/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// ---------------------------- Interface
interface Queue {
	public void push(Object elem);
	public Object take();
}


//---------------------------- class 1
class LinkedQueue implements Queue {
	
	private List<Object> queue;
	
	public LinkedQueue() {
		this.queue = new LinkedList<>();
	}
	
	@Override
	public void push(Object elem) {
		if (elem == null) throw new NullPointerException("elem == null");
		
		synchronized (queue) {
			queue.add(elem);
			queue.notify();
		}
	}

	@Override
	public Object take() {
		synchronized (queue) {
			while(queue.size() == 0) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				
			Object elem = queue.remove(0);
			
			return elem;
		}
	}
	
}


//---------------------------- class 2
class ArrayQueue implements Queue {
	
	private List<Object> queue;
	
	public ArrayQueue() {
		this.queue = new ArrayList<>(); 
	}

	@Override
	public void push(Object elem) {
		if (elem == null) throw new NullPointerException("elem == null");
		
		synchronized (queue) {
			queue.add(elem);
			queue.notify();
		}
	}

	@Override
	public Object take() {
		synchronized (queue) {
			while (queue.size() == 0) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			Object elem = queue.remove(0);
			return elem;
		}
	}	
}


//---------------------------- Abstraction
abstract class BridgeQueue {
	protected Queue queue;
	
	protected BridgeQueue(Queue queue) {
		this.queue = queue;
	}
	
	public abstract void push(Object elem);
	public abstract Object pop();
}


//---------------------------- abstraction extender
class myQueue extends BridgeQueue {

	protected myQueue(Queue queue) {
		super(queue);
	}

	@Override
	public void push(Object elem) {
		queue.push(elem);
	}

	@Override
	public Object pop() {
		return queue.take();
	}
	
	
	
}


//---------------------------- main
public class BridgeDesignPattern {
	public static void main(String[] args) {
		myQueue queue = new myQueue(new ArrayQueue());
		
		for (int i = 0; i<10; ++i) {
			new Thread(() -> {
				System.out.println("Thread Consumer: " + Thread.currentThread().getName() + " " + queue.pop());
			}).start();
			
		}
		
		for (int i = 0; i<10; ++i) {
			int id = i;
			new Thread(() -> {
				System.out.println("Thread Producer: " + Thread.currentThread().getName() + " " + id);
				queue.push(id);
			}).start();;
			
		}
				
	}
}
