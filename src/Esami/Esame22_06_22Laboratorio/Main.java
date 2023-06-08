package Esami.Esame22_06_22Laboratorio;

public class Main {
	private static final int N = 50;
	
	public static void main(String[] args) {
		Barrier barrier = new BarrierImpl();
		
		Waiter waiter = new Waiter(barrier);
		waiter.start();
		
		Notifier[] notifiers = new Notifier[N];
		for (int i=0; i<N; ++i) {
			
			Object object = new Object();
			
			barrier.add(object);
			Notifier notifier = new Notifier(object, barrier);
			notifier.start();
			notifiers[i] = notifier; 
		}
		
		
		// attesa
		try {
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// shutdown
		
		waiter.shutdown();
		
		for (Notifier notifier: notifiers) {
			notifier.shutdown();
		}
		
		System.out.println("Terminated");
		
	}
}
