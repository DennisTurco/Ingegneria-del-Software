package Esami.Esame07_02_23.Laboratorio;

public class ResourceImpl implements Resource {
	
	private final int ID;
	private final SimpleLock lock;
	
	public ResourceImpl(int ID) {
		this.ID = ID;
		this.lock = new SimpleLock();		
	}
	
	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void acquire() throws InterruptedException {
		lock.lock();
	}

	@Override
	public void release() {
		lock.unlock();
	}

	@Override
	public int use() {
		if (!lock.isCurrentThreadOwner()) throw new IllegalStateException("!lock.isCurrentThreadOwner()");
		
		return (int) Math.random()*10;
	}
}