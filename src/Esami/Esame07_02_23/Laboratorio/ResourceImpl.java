package Esami.Esame07_02_23.Laboratorio;

public class ResourceImpl implements Resource {
	
	private final int ID;
	private final ReentrantLock lock;
	
	public ResourceImpl(int ID) {
		if (ID < 0) throw new IllegalArgumentException("ID < 0");
			
		this.ID = ID;
		this.lock = new ReentrantLock();
	}
	
	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void acquire() throws InterruptedException {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " acquired resource: " + ID);
	}

	@Override
	public void release() {
		lock.unlock();
		System.out.println(Thread.currentThread().getName() + " released resource: " + ID);
	}
	
	@Override
	public int use() {
		// numero casuale tra 0 e 9
		if (!lock.isCurrentThreadOwner()) throw new IllegalStateException("!lock.isCurrentThreadOwner()");
			
		return (int) (Math.random()*10);
		
	}

}
