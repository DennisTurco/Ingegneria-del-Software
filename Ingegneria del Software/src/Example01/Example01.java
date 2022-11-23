package Example01;

class Example01 {
	
	private Object mutex = new Object(); // accessibile sia dal Waiter sia dal Notifier perche' sono inner class
	
	private boolean waitInProgress = false;
	
	public void go() { // questa funzione potrebbe avere senso metterla come private
		
		// l'obbiettivo e' quello di scrivere un codice che sia piu' generico possibile
		// ma non posso scrivere: Object ob = new Notifier(); perche' Object non ha start
		// ma scrivere: Notifier notifier = new Notifier(); e' troppo poco generico
		// di Notifier ci serve solo start()
		// Thread thread = new Notifier();
		// ora pero' abbiamo tolto la classe Notifier e l'abbiamo aggiunta nel main come classe interna
		
		
		// una classe interna anoima e' una classe innerclass
		/*Runnable runnable = new Runnable() {
			@Override
			public void run() {
				doNotify();
			}
		};
		
		Thread notifier = new Thread(runnable);
		
		runnable = new Runnable() {
			@Override
			public void run() {
				doWait();
			}
		};
		
		Thread waiter = new Thread(runnable);*/
		
		// Usare al posto della cosa sopra delle lambda expression
		/*Thread notifier = new Thread(() -> { //sembra che si sta chiamando solo una funzione ma in realta' stiamo creando un oggetto
			doNotify();
		});
		
		Thread waiter = new Thread(() -> {
			doWait();
		});*/
		
		
		waitInProgress = false;
		
		// ancora meglio usare:
		Thread notifier = new Thread(this::doNotify); // lambda expression senza argomenti e l' unica cosa che fa e' chiamare doNotify su this
		Thread waiter = new Thread(this::doWait); //costruisco un Thread e il corpo del Thread e' doWait
		
		notifier.start();	
		waiter.start();	
	}
	
	public static void main(String[] args) {
		new Example01().go(); // questa operazione serve per evitare di scrivere del codice dentro al main
	}
	
	// CLASSE EMBEDDED
	// aggiungendo la classe Notifier all'interno della classe Example01 ci permette di renderla privata (è privata ma di Example 01)
	// inoltre condivide lo stato di Example01 (anche privato), ora inizia e muore con la classe Example01
	/*private class Notifier extends Thread() {
		@Override
		public void run() {
			System.out.println("Started");
			
			try {
				Thread.sleep(2000);
			} catch (Throwable throwable) {
				System.err.println("Error --> " + throwable);
			}
			
			System.out.print("Terminated");
		}
	}*/
	
	private void doWait() {
		System.out.println("Waiter Started");
		
		/*synchronized (mutex) {
			notifyAll();
		}*/
		// non viene gestita la interrupt exception
		
		/*synchronized (mutex) {
			try {
				mutex.wait();
			} catch(Throwable throwable) {
				System.err.println("Error --> " + throwable);
			}
		}*/
		// se il waiter non è ancora in wait la notify non sveglia nessuno .... 11.45
		// se ci mettiamo 5 secondi di sleep e' bassa la probabilità che la wait esagua la wait prima della notify
		// non si possono risolvere problemi di sincronizzazione con un attesa temporizzata
		
		// soluzione permettere alla notify che venga eseguita la wait, usiamo un booleano
		synchronized (mutex) {
			waitInProgress = true;
			
			mutex.notifyAll();
			
			try {
				mutex.wait(); // arrivati qui siamo sicuri che ...12.13
			} catch(Throwable throwable) {
				System.err.println("Error --> " + throwable);
			}
			
			waitInProgress = false;
		}
		
		System.out.println("Waiter Terminated");
	}
	
	private void doNotify() {
		System.out.println("Notified Started");
		
		/*try {
			Thread.sleep(2000);
		} catch (Throwable throwable) {
			System.err.println("Error --> " + throwable);
		}*/
		
		//mutex.notifyAll();
		// cosi' non va bene perche' in questo caso non siamo nella regione critica dell'oggetto mutex
		// significa che manca il blocco Syncronize
		
		//synchronized (mutex) {
		//	mutex.notifyAll();
		//}
		// cosi' otteniamo lo stesso effetto perche' 
		
		synchronized (mutex) {
				try {
					while (!waitInProgress)  //meglio mettere while anziche' if 
						mutex.wait();
					
					Thread.sleep(5000); // il waiter rimmarra' in wait per almeno 5 secondi
					
					mutex.notifyAll();
				} catch (Throwable throwable) {
					System.err.println("Error --> " + throwable);
				}			
		}
		
		System.out.print("Notified Terminated");
	}
	
	
}
	
	