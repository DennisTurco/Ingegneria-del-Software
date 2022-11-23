package esercizi_ripasso;

public interface Lock {
	public void lock();
	public void unlock();
	public Condition newCondition();
}
