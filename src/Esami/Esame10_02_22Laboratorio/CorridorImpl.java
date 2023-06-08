package Esami.Esame10_02_22Laboratorio;

public class CorridorImpl implements Corridor {
	
	private static final int K = 5;
	private int count;
	
	public CorridorImpl() {
		this.count = 0;
	}
	
	@Override
	public void enter() {
		synchronized (this) {
			
			if (count >= K) {
				for (int i=0; i<K; ++i) {
					synchronized (this) {
						this.notify();
					}
				}
				count = count - K;
			}
			
			try {
				++count;
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}

	@Override
	public void exit() {
		
	}
	
}
