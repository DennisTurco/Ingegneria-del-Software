package Esami.Esame14_01_2022Laboratorio;

public class Worker {
	
	private Thread thread;
	
	public Worker(ResourceManager manager, int id) {
		thread = new Thread() {
			@Override
			public void run() {
				for(;;) {
					synchronized (manager) {
						Resource[] resources = manager.acquire(id);
						useAndPrint(resources[0], resources[1], resources[2]);
						for (int i=0; i<3; ++i) {
							resources[i].release();
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	public void start() {
		thread.start();
	}
	
	private void useAndPrint(Resource r1, Resource r2, Resource r3) {
		int t = r1.use() + r2.use() + r3.use();
		System.out.println(t);
	}
}