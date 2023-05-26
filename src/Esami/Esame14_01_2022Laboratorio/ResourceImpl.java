package Esami.Esame14_01_2022Laboratorio;

public class ResourceImpl implements Resource {
	
	private int id;
	private boolean free;
	private Object mutex;
	
	public ResourceImpl(int id) {
		this.id = id;
		this.free = true;
		this.mutex = new Object();
	}
	
	@Override
	public int getID() {
		return id;
	}

	@Override
	public int use() {
		synchronized (mutex) {
			if (free) throw new IllegalStateException("free == true");
			
			int result = (int) Math.random()*(id+99) - id;
			return result;
		}
	}

	@Override
	public void release() {
		synchronized (mutex) {
			free = true;
			System.out.println("risorsa " + id + " rilasciata");
			this.notifyAll();
		}
	}
	
	public boolean getFree() {
		return free;
	}
	
	public void setFree(boolean free) {
		this.free = free;
	}
}
