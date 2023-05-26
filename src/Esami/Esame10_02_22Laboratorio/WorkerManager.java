package Esami.Esame10_02_22Laboratorio;

public class WorkerManager {
	
	private static WorkerManager instance; 
	private Corridor corridor;
	
	private WorkerManager() {
		this.corridor = new CorridorImpl();
	}
	
	public static WorkerManager getInstance() {
		if (instance == null) {
			synchronized (WorkerManager.class) {
				if (instance == null) {
					instance = new WorkerManager();
				}
			}
		}
		return instance;
	}
	
	public Corridor getCorridor() {
		return corridor;
	}
}
