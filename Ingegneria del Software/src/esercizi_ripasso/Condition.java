package esercizi_ripasso;

public interface Condition {
	public void signal();
	public void signalAll();
	public void await() throws InterruptedException;
}
