package Esami.Esame14_09_22Laboratorio;

import java.util.Random;

public class ResourceManagerImpl implements ResourceManager {
	
	private static ResourceManagerImpl instance;
	private boolean[] free;
	
	private ResourceManagerImpl(int nResources, int nWorkers) {
		this.free = new boolean[nResources];
		for (int i=0; i<nResources; ++i) {
			free[i] = true;
		}
		
		for (int i=0; i<nWorkers; ++i) {
			Worker worker = new Worker(nResources);
			worker.start();
		}
	}
	
	// singleton
	public static ResourceManagerImpl getInstance(int nResources, int nWorkers) {
		if (instance == null) {
			synchronized (ResourceManagerImpl.class) {
				if (instance == null) {
					instance = new ResourceManagerImpl(nResources, nWorkers);
				}
			}
		}
		return instance;
	}
	
	@Override
	public void acquireResources(int i, int j) {
		if (i == j) throw new IllegalArgumentException("i == j");
		
		synchronized (this) {
			while (!free[i] || !free[j]) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
			
			free[i] = false;
			free[j] = false;
			System.out.println("Thread: " + Thread.currentThread().getName() +  " --> Resources: " + i + ", " + j + " acquired");
		}
	}

	@Override
	public void freeResource(int i, int j) {
		if (i == j) throw new IllegalArgumentException("i == j"); 
		
		synchronized (this) {
			free[i] = true;
			free[j] = true;
			
			this.notify();
			
			System.out.println("Thread: " + Thread.currentThread().getName() +  " --> Resources: " + i + ", " + j + " released");
		}
	}
	
	
	// INNER CLASS
	private class Worker implements Runnable {
		
		private int nResources;
		private Thread thread;
		
		private Worker(int nResources) {
			this.nResources = nResources;
			this.thread = new Thread(this);
		}
		
		@Override
		public void run() {
			Random random = new Random();
			int i = (int) random.nextInt(nResources);
			int j = (int) random.nextInt(nResources);
			
			acquireResources(i, j);
			
			try {
				Thread.sleep(100 + i + j);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			freeResource(i, j);
		}
		
		private void start() {
			thread.start();
		}
		
	}

}
