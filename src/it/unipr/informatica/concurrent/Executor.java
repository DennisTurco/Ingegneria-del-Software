package concurrency;

public interface Executor {
    public void execute(Runnable command);
}
