package Esami.Esame14_09_22Laboratorio;

public class ResourceManagerImpl implements ResourceManager {
	
	private Worker[] workers;
	private boolean[] resources;
	private static ResourceManagerImpl instance;
	
	private ResourceManagerImpl(int resorcesCount, int workersCount) {
		if (resorcesCount <= 0) throw new IllegalArgumentException("resourcesCount <= 0");
		if (workersCount <= 0) throw new IllegalArgumentException("workersCount <= 0");
		
		this.resources = new boolean[resorcesCount];
		for (int i=0; i<resorcesCount; ++i) {
			resources[i] = true;
		}
		
		this.workers = new Worker[workersCount];
		for (int i=0; i<workersCount; ++i) {
			Worker worker = new Worker(resorcesCount);
			workers[i] = worker;
		}
	}
	
	// singleton
	public static ResourceManagerImpl getInstance(int resorcesCount, int workersCount) {
		if (instance == null) {
			synchronized (ResourceManagerImpl.class) {
				if (instance == null) {
					instance = new ResourceManagerImpl(resorcesCount, workersCount);
				}
			}
		}
		return instance;
	}
	
	
	public void startAcquiring(int i) {
		workers[i].start();
	}
	
	public void shutdown() {
		for (Worker worker: workers) {
			worker.shutdown();
		}
	}
	
	@Override
	public void acquireResources(int i, int j) {
		synchronized (this) {
			
			while (!resources[i] || !resources[j]) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException("terminated while acquiring a resource: " + i + ", " + j);
				} 
			}
			
			resources[i] = false;
			resources[j] = false;
			
			System.out.println("Thread: " + Thread.currentThread().getName() + ", Resource: " + i + " acquired");
			System.out.println("Thread: " + Thread.currentThread().getName() + ", Resource: " + j + " acquired");
		}
	}

	@Override
	public void freeResources(int i, int j) {
		
		synchronized (this) {
			
			resources[i] = true;
			resources[j] = true;
			
			this.notifyAll();
			
			System.out.println("Thread: " + Thread.currentThread().getName() + ", Resource: " + i + " released");
			System.out.println("Thread: " + Thread.currentThread().getName() + ", Resource: " + j + " released");
		}
	}
	
	
	// INNER CLASS
	private class Worker implements Runnable {
		
		private Thread thread;
		private final int resourcesCount;
		
		private Worker(int resourcesCount) {
			this.thread = new Thread(this);
			this.resourcesCount = resourcesCount;
		}
		
		@Override
		public void run() {
			while(true) {
				int i = (int) (Math.random()*resourcesCount);
				int j = (int) (Math.random()*resourcesCount);
				
				if (i == j) continue;
				
				try {
					acquireResources(i, j);
					Thread.sleep(100 + i + j);
					freeResources(i, j);
				} catch (InterruptedException e) {
					System.err.println("Thread: " + Thread.currentThread().getName() + " terminated");
					return;
				} catch (RuntimeException e) {
					System.err.println(e.getMessage());
					return;
				}
				
			}
		}
		
		private void start() {
			thread.start();
		}
		
		private void shutdown() {
			thread.interrupt();
		}
		
	}

}
