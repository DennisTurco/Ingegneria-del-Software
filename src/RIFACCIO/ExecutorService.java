package RIFACCIO;

import java.util.concurrent.Callable;

public interface ExecutorService extends Executor {
    public void shutdown();

    public Future<?> submit(Runnable task);
    public <T> Future<T> submit(Callable<T> task);

    public void submit(Runnable task, Callback<?> callback);
    public <T> void submit(Callable<T> task, Callback<T> callback);
}