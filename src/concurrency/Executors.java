package concurrency;

public class Executors {
    
    private Executors() {
        // blank
    }

    // questa classe può essere istanziata solo unicamente all'interno della classe stessa 
	// ha un unico costruttore ma privato, quindi ne creiamo una pubblica


    public static ExecutorService newFixedThreadPool(int count) {
        return new SimpleThreadPoolExecutorService(count);
    }
}
