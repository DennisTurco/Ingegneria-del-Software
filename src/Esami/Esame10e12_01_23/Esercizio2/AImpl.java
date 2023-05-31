package Esami.Esame10e12_01_23.Esercizio2;


public class AImpl implements A {
	
	private Object mutex;
	
	public AImpl() {
		this.mutex = new Object();
	}

	@Override
	public void m(int min, int max, Callback<Integer> callback) {
		
		if (min < 0) throw new IllegalArgumentException("min < 0");
		if (max < 0) throw new IllegalArgumentException("max < 0");
		if (max < min) throw new IllegalArgumentException("max < min");
		if (callback == null) throw new NullPointerException("callback == null");
		
		new Thread(() -> {
			synchronized (mutex) {
				try {
					int random = (int) (Math.random()*max + min);
					
					System.out.printf("min = %d, max = %d, Random = %d", min, max, random);
					
					Thread.sleep(random);
					
					System.out.println("\nTerminated");
					
					callback.onSuccess(random);
				} catch (InterruptedException e) {
					callback.onFailure(e);
				}
			}
			
		}).start();
		
	}

}
