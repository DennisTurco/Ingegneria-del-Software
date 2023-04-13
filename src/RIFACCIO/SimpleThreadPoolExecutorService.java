package RIFACCIO;

import java.util.concurrent.Callable;

public class SimpleThreadPoolExecutorService implements ExecutorService {

    private Worker[] workers;
    private boolean shutdown;
    private BlockingQueue<Runnable> tasks;


    public SimpleThreadPoolExecutorService(int count) {
        if (count < 1) throw new IllegalArgumentException("count < 1");
        
        this.shutdown = false;
        this.tasks = new LinkedBlockingQueue<>();
        this.workers = new Worker[count];

        for (int i=0; i<workers.length; ++i) {
            Worker worker = new Worker();
            workers[i] = worker;
            worker.start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) throw new NullPointerException("command == null");

        synchronized (tasks) {
            if (shutdown) throw new RejectedExecutionException("shutdown == true");

            try {   
                tasks.put(command);
            } catch (InterruptedException e) {
                // blank
            }
        }
    }

    @Override
    public void shutdown() {
        synchronized (tasks) {

            this.shutdown = true;

            for (int i=0; i<workers.length; ++i) {
                workers[i].shutdown();
            }
        }
    }


    // EXAMPLE 06
    @Override
    public Future<?> submit(Runnable task) {
        if (task == null) throw new NullPointerException("task == null");

        SimpleFuture<Runnable> future = new SimpleFuture<>();

        execute(() -> {
            try {
                task.run();
                future.setValue(null);
            } catch (Throwable exception) {
                future.setException(exception);
            }
            
        });

        return future;

    }

    // EXAMPLE 06
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException("task == null");

        SimpleFuture<T> future = new SimpleFuture<>();

        execute(() -> {
            try {
                T result = task.call();
                future.setValue(result);
            } catch (Throwable throwable) {
                future.setException(throwable);
            }
        });

        return future;
    }

    // EXAMPLE07
    @Override
    public void submit(Runnable task, Callback<?> callback) {
        if (task == null) throw new NullPointerException("task == null");
        if (callback == null) throw new NullPointerException("callback == null");

        execute(() -> {
            try {
                task.run();
                callback.onSuccess(null);
            } catch (Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    // EXAMPLE07
    @Override
    public <T> void submit(Callable<T> task, Callback<T> callback) {
        if (task == null) throw new NullPointerException("task == null");
        if (callback == null) throw new NullPointerException("callback == null");

        execute(() -> {
            try {
                T result = task.call();
                callback.onSuccess(result);
            } catch (Throwable throwable) {
                callback.onFailure(throwable);
            }
        });


    }


    // INNER CLASS
    private class Worker implements Runnable {

        private boolean shutdown;
        private Thread thread;

        private Worker() {
            this.shutdown = false;
            this.thread = new Thread(this);
        }

        @Override
        public void run() {
            for (;;) {
                synchronized (tasks) {
                    if (shutdown) return;
                }

                try {
                    Runnable runnable = tasks.take();
                    runnable.run();     
                } catch (InterruptedException interruptedException) {
                    return;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }

        private void start() {
            thread.start();
        }

        private void shutdown() {
            synchronized (tasks) {
                shutdown = false;
                thread.interrupt();
            }
        }

    }

    

    
}