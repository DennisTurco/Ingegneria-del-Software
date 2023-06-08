package Esami.Esame10_02_22Laboratorio;

public class WorkerImpl implements Worker {
	
	private Corridor corridor;
	
	public WorkerImpl(Corridor corridor) {
		this.corridor = corridor;
	}
	
	@Override
	public void execute() {
		corridor.enter();
		System.out.println("In corridor " + Thread.currentThread().getId());
		corridor.exit();
	}

	@Override
	public Corridor getCorridor() {
		return corridor;
	}

}
