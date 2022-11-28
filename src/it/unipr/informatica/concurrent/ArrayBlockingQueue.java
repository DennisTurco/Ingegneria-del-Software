package it.unipr.informatica.concurrent;

import it.unipr.informatica.concurrent.locks.Condition;
import it.unipr.informatica.concurrent.locks.Lock;
import it.unipr.informatica.concurrent.locks.ReentrantLock;

//NOTA: nel dubbio se fare una signal() (notify()), o una signalAll() (notifyAll()), fare sempre una ...All() per essere sicuri

public final class ArrayBlockingQueue<Type> implements BlockingQueue<Type> {
	
	protected Object[] queue; //non potrebbe essere un tipo Type perchè se no fare: this.queue = new Type[size]; sarebbe errore, ma non è un problema perchè tanto non cambia nulla
	
	protected int in, out;
	
	protected int count, size; 
	
	private Lock lock;
	
	private Condition isNotEmpty, isNotFull;
	
	public ArrayBlockingQueue(int size) {
		if(size < 1) throw new IllegalArgumentException("size < 1");
		
		this.size = size;
		this.queue = new Object[size];
		this.in = 0;
		this.out = 0;
		this.count = 0;
		this.lock = new ReentrantLock();
		this.isNotEmpty = lock.newCondition();
		this.isNotFull = lock.newCondition();
	}
	
	@Override
	public void put(Type object) throws InterruptedException {
		if (object == null) throw new NullPointerException("object == null");
		
		try {
			lock.lock();
			
			while(count == size) isNotFull.await(); //rimaniamo in attesa che le cose vadano a buon fine
			
			queue[in] = object;
			
			++count;
			
			in = (in + 1) % size;
			
			isNotEmpty.signal(); // non è necessario eseguire una notifyAll(), basta svegliare un solo Thread
			
		} finally { // il blocco finally viene sempre eseguito, sia se si verifica un eccezione, sia se non si verifica
			lock.unlock();
		}
	}

	@Override
	public Type take() throws InterruptedException {
		try {
			lock.lock();
			
			while(count == 0) isNotEmpty.await();
			
			@SuppressWarnings("unchecked")
			Type result = (Type) queue[out];
			
			queue[out] = null;
			
			--count;
			
			out = (out + 1) % size;
			
			isNotFull.signal(); // non è necessario eseguire una notifyAll(), basta svegliare un solo Thread
			
			return result;
			
		} finally {
			lock.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		//ottengo il lock e il numero di celle libere che è il valore che ritorniamo
		
		// non mi serve un costrutto try e catch perchè se la lock lancia un eccezione non viene lockato nulla quindi nessun problema
		lock.lock();
		
		int result = size - count;
		
		lock.unlock();
		
		return result;
	}

	@Override
	public boolean isEmpty() {
		
		lock.lock();
		
		boolean result = (count == 0);
		
		lock.unlock();
		
		return result;
	}

	@Override
	public void clear() {
			
		lock.lock();
		in = out = count = 0;  //coda sicuramente svuotata
		queue = new Object[size];
		
		//prima di fare la unlock sagnaliamo che la coda non è più piena (magari non la era anche prima)
		isNotFull.signalAll(); //va eseguita una notifyAll() perchè grazie ad una clear lo spazio si è liberato e tutti possono entrare
		lock.unlock();
	}
	

}