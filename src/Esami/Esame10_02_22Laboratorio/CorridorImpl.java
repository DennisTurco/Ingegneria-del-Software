package Esami.Esame10_02_22Laboratorio;

public class CorridorImpl implements Corridor {
	
	private int counter;
	
	public CorridorImpl() {
		this.counter = 0;
	}
	
	
	private void unlock() {
		// sblocco tutte le chiamate
		if (counter >= 5) {
			for (int i=0; i<counter; ++i) {
				this.notify();
			}
			counter = 0;
		}
	}
	
	@Override
	public void enter() {
		
		synchronized (this) {
			
			unlock();			
						
			try {
				counter++;
				this.wait();
			} catch (InterruptedException e) {
				counter--;
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void exit() {
		synchronized (this) {
			this.notify();
		}
	}

}
