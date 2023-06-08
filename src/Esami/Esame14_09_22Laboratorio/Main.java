package Esami.Esame14_09_22Laboratorio;

public class Main {
	private static final int N = 50;
	private static final int M = 20;
	
	public static void main(String[] args) {
		ResourceManagerImpl manager = ResourceManagerImpl.getInstance(N, M);
		
		for (int i=0; i<M; ++i) {
			manager.startAcquiring(i);
		}
		
		
		try {
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		manager.shutdown();
		
		System.out.println("Terminated");
	}
}
