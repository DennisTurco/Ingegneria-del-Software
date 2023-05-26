package Esami.Esame10_02_22Laboratorio;


public class Main {
	
	private void go() {
		
		final int DIM = 10;
		
		WorkerManager manager = WorkerManager.getInstance();
		
		for (int i=0; i<DIM; ++i) {
			Worker worker = new WorkerImpl(manager);
			
			InnerThread thread = new InnerThread(worker);
			thread.start();
		}
	}
	
	public static void main(String[] args) {
		new Main().go();
	}
	
	private class InnerThread implements Runnable {
		
		private Worker worker;
		private Thread thread;
		
		private InnerThread(Worker worker) {
			this.worker = worker;
			this.thread = new Thread(this);
		}
		
		@Override
		public void run() {
			worker.execute();
			System.out.println("done " + Thread.currentThread().getId());
		}
		
		private void start() {
			thread.start();
		}
		
	}
}
