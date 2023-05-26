package Esami.Esame10_02_22Laboratorio;

public class WorkerImpl implements Worker {
	
	private WorkerManager manager;
	
	public WorkerImpl(WorkerManager manager) {
		this.manager = manager;
	}

	@Override
	public void execute() {
		
		manager.getCorridor().enter();
		
		System.out.println("Thread id: " + Thread.currentThread().getId());
		
		manager.getCorridor().exit();
	}

	@Override
	public Corridor getCorridor() {
		return manager.getCorridor();
	}

}
