package esercizi_ripasso;

import it.unipr.informatica.concurrent.locks.ReentrantLock;

// costruiamo una seconda implementazione di BlockingQueue
public class ArrayBlockingQueue<Type> implements BlockingQueue<Type>{
	
	// in LinkedBlockingQueue avevamo: private Object queue;
	private Object[] queue; 
	
	// la size indica la dimensione mazzima, mentre la cout conta quanti elementi ci sono attualmente nell'array
	private int size, count;
	
	//indici che ci servono per capire dove inizia e dove finisce (su in facciamo la prossima put, mentre su out faremo la prossima take)
	private int in, out; 
	
	// isNotEmpty se la coda non è vuota e quindi possiamo fare take();
	// isNotFull se la coda non è piena e quindi possiamo fare put().
	private Condition isNotEmpty, isNotFull;
	
	private ReentrantLock lock;
	
	public ArrayBlockingQueue(int size) {
		if (size < 1) throw new IllegalArgumentException("size < 1");
		
		this.size = size;
		this.count = 0;
		this.in = 0;
		this.out = 0;
		this.queue = new Object[size];
		this.lock = new ReentrantLock();
		this.isNotEmpty = (Condition) lock.newCondition();
		this.isNotFull = (Condition) lock.newCondition();
	}
	
	@Override
	public void put(Type object) throws InterruptedException, ClassCastException, NullPointerException, IllegalArgumentException {
		if (object == null) throw new NullPointerException("object == null");
	
		try {
			// innanzitutto aquisiamo il lock
			lock.lock();
			
			// abbiamo raggiunto la grandezza massima quindi entriamo in attesa
			while (count == size) isNotFull.await(); 
			
			// aggiungo l'oggetto
			queue[in] = object;
			
			// incremento il count
			count++;
			
			// aggirno in
			in = (in + 1) % size;
			
			// a questo punto qualcosa è stato aggiunto e count è per forza > 0 quindi so che dentro c'è qualcosa
			isNotEmpty.signal();
			
		} finally {
			// va messo in un costrutto finally perchè la unlock() deve essere eseguita sia se non si presenta un eccezione, sia se si verifica
			lock.unlock();
		}
	}

	@Override
	public Type take() throws InterruptedException {
		
		try {
			// innanzitutto aquisiamo il lock
			lock.lock();
			
			// vado in attesa se l'array è vuoto
			while(count == 0) isNotEmpty.await();
			
			// ottengo l'oggetto
			@SuppressWarnings("unchecked")
			Type object = (Type) queue[out];
			
			// elimino la posizione
			queue[out] = null;
			
			// aggiorno count
			count--;
			
			// aggiorno out
			out = (out + 1) % size;
			
			// siamo sicuri che l'array non è pieno
			isNotFull.signal();	
			
			return object;
			
		} finally {
			// va messo in un costrutto finally perchè la unlock() deve essere eseguita sia se non si presenta un eccezione, sia se si verifica
			lock.unlock();
		}
		
		
	}

	@Override
	public int remainingCapacity() {
		// innanzitutto aquisiamo il lock
		lock.lock();
		
		// calcoliamo la capacità
		int result = size - count;
		
		// a questo punto rilasciamo
		lock.unlock();
		
		return result;
	}

	@Override
	public boolean isEmpty() {
		// innanzitutto aquisiamo il lock
		lock.lock();
		
		// ottengo il valore
		boolean result = (count == 0);
		
		// a questo punto rilasciamo
		lock.unlock();
	
		return result;
	}

	@Override
	public void clear() {
		// innanzitutto aquisiamo il lock
		lock.lock();
		
		// torna tutto a 0
		count = in = out = 0;
		
		// non devo fare queue = null perchè se no non lo potri più utilizzare, voglio solo pulirlo
		queue = new Object[size];
		
		// segnaliamo che la coda non è più piena
		isNotFull.signalAll();
		
		// a questo punto rilasciamo
		lock.unlock();
	}
}