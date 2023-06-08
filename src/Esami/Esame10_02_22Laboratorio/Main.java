package Esami.Esame10_02_22Laboratorio;

import java.util.ArrayList;

public class Main {
	private static final int K = 50;
	
	public static void main(String[] args) {
		Corridor corridor = new CorridorImpl();
		
		WorkerManager manager = WorkerManager.getInstance(corridor, K);
		ArrayList<Worker> workers = (ArrayList<Worker>) manager.getWorkers();
		
		for (int i=0; i<workers.size(); ++i) {
			Worker worker = workers.get(i);
			
			new Thread(() -> {
				worker.execute();
				System.out.println("Done " + Thread.currentThread().getId());
			}).start();
		}
		
	}
}
