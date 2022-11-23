package esercizi_ripasso;

public class ReentrantLock implements Lock{
	
	private Thread owner;
	private int counter;
	private Object condition;
	
	public ReentrantLock() {
		this.owner = new Thread();
		this.counter = 0;
		this.condition = new Object();
	}
	
	@Override
	public void lock() {

		
	}

	@Override
	public void unlock() {

		
	}

	@Override
	public Condition newCondition() {
		return new InnerCondition();
	}
	
	
	//INNER CLASS
	private class InnerCondition implements Condition {
		
		private Object condition;
		
		public InnerCondition() {
			this.condition = new Object();
		}

		@Override
		public void signal() {
			
			
		}

		@Override
		public void signalAll() {

			
		}

		@Override
		public void await() throws InterruptedException {

			
		}
		
	}

}
