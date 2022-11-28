package esercizi_ripasso;

public interface BlockingQueue<Type> {
	
	public void put(Type t) throws InterruptedException, ClassCastException, NullPointerException, IllegalArgumentException;
	
	public Type take() throws InterruptedException;
	
	public int remainingCapacity();
	
	public boolean isEmpty();
	
	public void clear();
}