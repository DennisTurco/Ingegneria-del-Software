package esercizi_ripasso;

public class ReentrantLock implements Lock{
	
	// quale thread è owner del lock 
	private Thread owner;
	
	// per contare numero di accessi a lock e unlock
	private int counter;
	
	// per fare la sincronizzazione
	private Object mutex;
	
	public ReentrantLock() {
		this.owner = null;
		this.mutex = new Object();
		
		// non deve mai scendere sotto lo 0
		this.counter = 0; 
	}
	
	@Override
	public void lock() {
		Thread currenThread = Thread.currentThread();
		
		synchronized (mutex) {
			
			// il counter non puo' essere < 0
			if (counter < 0) throw new IllegalMonitorStateException("counter < 0");
			
			// se owner non è null oppure non è il thread corrente allora ci blocchimo
			while (owner != null && owner != currenThread) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					throw new IllegalThreadStateException("interrupted");
				}
			}
			
			// ... altrimenti diciamo che owner è il thread corrente
			if (owner == null) owner = currenThread;
			
			// la lock è entrata quindi counter++
			counter++;
		}
	}

	@Override
	public void unlock() {
		
		synchronized (mutex) {
			
			// non posso procedere se il mio owner non è il currentThread, se entrassimo in attesa sarebbe sbagliato,
			// perchè se con la lock entriamo in attesa e qui uguale -> ciclo infinito ????
			if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()");
			
			// il counter non puo' essere < 0, ma in questo caso non può nemmeno essere 0 perchè significherebbe che non ho mai fatto lock e quindi di conseguenza non posso fare unlock
			if (counter <= 0) throw new IllegalMonitorStateException("counter <= 0");
			
			// la unlock è entrata quindi counter--
			counter--; 
			
			// se counter ora vale 0 significa che non ci sono lock
			if (counter == 0) {
				// nessuno è in possesso dell'owner
				owner = null;
				
				// all'interno della "lock()" un thread potrebbe essere entrato in wait nel caso in cui owner sia != null, 
				// siccome ora owner == null dobbiamo risvegliarlo
				mutex.notify();
			} 
		
		}
	}

	@Override
	public Condition newCondition() throws UnsupportedOperationException {
		return new InnerCondition();
	}
	
	
	// INNER CLASS
	private class InnerCondition implements Condition {
		
		private Object condition;
		
		// costruttore privato in quanto non è possibile accedere da fuori, ma solo tramite funzione della classe in cui è contenuta "newCondition()"
		private InnerCondition() {
			this.condition = new Object();
		}
		
		@Override
		public void signal() {
			
			// valutiamo se owner è il current Thread, se non lo dovesse essere -> lancio eccezione
			synchronized (mutex) {
				if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()");
			}
			
			// a questo punto eseguiamo la notify() sull'oggetto condition
			synchronized (condition) {
				condition.notify();
			}
		}

		@Override
		public void signalAll() {
			// valutiamo se owner è il current Thread, se non lo dovesse essere -> lancio eccezione
			synchronized (mutex) {
				if (owner != Thread.currentThread()) throw new IllegalMonitorStateException("owner != Thread.currentThread()");
			}
			
			// a questo punto eseguiamo la notifyAll() sull'oggetto condition
			synchronized (condition) {
				condition.notifyAll();
			}
		}

		@Override
		public void await() throws InterruptedException {
			
			// se unlock va a buon fine significa che eravamo gli owner altrimenti eccezione
			unlock();
			
			synchronized (condition) {
				condition.wait();
			}
			
			// se non siamo gli owner entriamo in wait
			lock();
		}
		
	}
	
}
