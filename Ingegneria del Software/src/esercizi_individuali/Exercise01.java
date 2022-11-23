package esercizi_individuali;


public class Exercise01{
	
	private Object mutex = new Object();
	
	private boolean waitInProgress;
	
	public void go() {
		
		waitInProgress = false;
		
		Thread notifyThread = new Thread(this::doNotify);
		Thread waitThread = new Thread(this::doWait);
		
		notifyThread.start();
		waitThread.start();
	}
	
	
	public static void main(String[] argv) {
		new Exercise01().go();
	}
	
	private void doWait() {
		System.out.println("Wait Started");
		
		synchronized (mutex) {
			waitInProgress = true;
			
			System.out.println("Wait notifyAll()");
			mutex.notifyAll();
			
			try {
				System.out.println("Wait wait()");
				mutex.wait();
			} catch(Throwable throwable) {}
			
			waitInProgress = false;
			
		}
		
		System.out.println("Wait Terminated");
	}
	
	private void doNotify() {
		System.out.println("Notify Started");
		
		synchronized (mutex) {
			try {
				while(!waitInProgress) {
					System.out.println("Notify wait()");
					mutex.wait();
				}
				
				Thread.sleep(5000);
				
				System.out.println("Notify notifyAll()");
				mutex.notifyAll();
				
			} catch(Throwable trowable) {}
			
			
		}
		
		System.out.println("Notify Terminated");
	}
}