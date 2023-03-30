package concurrency;

import java.util.concurrent.Callable;

public interface ExecutorService extends Executor{
    public void shutdown();

    // Le submit servono per dare la task alla ThreadPool ed eseguirla

    // AGGIUNTO PER EXAMPLE 06
    public Future<?> submit(Runnable tasks);
    public <T> Future<T> submit(Callable<T> tasks);

    // AGGIUNTO PER EXAMPLE 07
    public void submit(Runnable task, Callback<?> callback);
    public <T> void submit(Callable<T> task, Callback<T> callback); 
}
