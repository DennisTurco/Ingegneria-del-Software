package Esami.Esame10_02_22Laboratorio;

import java.util.ArrayList;
import java.util.List;

public class WorkerManager {
	private static WorkerManager instance;
	private List<Worker> workers;
	
	private WorkerManager(Corridor corridor, int workerCount) {
		this.workers = new ArrayList<>();
		
		for (int i=0; i<workerCount; ++i) {
			workers.add(new WorkerImpl(corridor));
		}
	}
	
	public static WorkerManager getInstance(Corridor corridor, int workerCount) {
		if (instance == null) {
			synchronized (WorkerManager.class) {
				if (instance == null) {
					instance = new WorkerManager(corridor, workerCount);
				}
			}
		}
		return instance;
	}
	
	public List<Worker> getWorkers() {
		return workers;
	}
}
