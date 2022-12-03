package esercizi_ripasso;

import it.unipr.informatica.concurrent.SimpleThreadPoolExecutorService;

public class Executors {
	
	private Executors() {
		// blank
	}
	
	public static ExecutorService newFixedThreadPool(int count) {
		return newFixedThreadPoolExecutorService(count);
	}
}
