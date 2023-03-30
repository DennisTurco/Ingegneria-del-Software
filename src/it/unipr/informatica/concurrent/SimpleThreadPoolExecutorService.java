package concurrency;

import java.util.concurrent.Callable;

public class SimpleThreadPoolExecutorService implements ExecutorService{

    private Worker[] workers;
    private BlockingQueue<Runnable> tasks;
    private boolean shutdown;

    public SimpleThreadPoolExecutorService(int count) {
        if (count < 1) throw new IllegalArgumentException("count < 1");
        
        // inizializzazione attributi
        this.shutdown = false;
        this.workers = new Worker[count];
        this.tasks = new LinkedBlockingQueue<>();
        
        // costruzione worker
        for (int i=0; i<count; i++) {
            Worker worker = new Worker();
            workers[i] = worker;
            worker.start();
        }
    }

    @Override
    public void shutdown() {
        synchronized (tasks) {

            // imposto a true perche' sono in shutdown
            this.shutdown = true;

            // faccio la shutdown per ogni istanza di workers
            int length = workers.length;
            for (int i=0; i<length; i++) {
                workers[i].shutdown();
            }
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

    // AGGIUNTO PER EXAMPLE 06 - CON FUTURE
    @Override
    public Future<?> submit(Runnable tasks) {
        if (tasks == null) throw new NullPointerException("tasks == null");

        SimpleFuture<?> future = new SimpleFuture<>();

        execute(() -> {
            try {
                tasks.run(); // esecuzione thread
                future.setValue(null); // caso funzionante
            } catch (Throwable throwable) {
                future.setException(throwable); // caso eccezione
            }
        });

        return future;
    }

    // AGGIUNTO PER EXAMPLE 06 - CON FUTURE
    @Override
    public <T> Future<T> submit(Callable<T> tasks) {
        if (tasks == null) throw new NullPointerException("tasks == null");

        SimpleFuture<T> future = new SimpleFuture<>();

        execute(() -> {
            try {
                T result = tasks.call();
                future.setValue(result); // caso funzionante
            } catch (Throwable throwable) {
                future.setException(throwable); // caso eccezione
            }
        });

        return future;
    }

    // AGGIUNTO PER EXAMPLE 07 - CON CALLBACK
    @Override
    public void submit(Runnable task, Callback<?> callback) {
        
        if (task == null) throw new NullPointerException("task == null");
        if (callback == null) throw new NullPointerException("callback == null");

        execute(() -> {
            try {
                task.run(); // esecuzione thread
                callback.onSuccess(null); // caso funzionante
            } catch (Throwable throwable) {
                callback.onFailure(throwable); // caso eccezione
            }
        });
        
    }

    // AGGIUNTO PER EXAMPLE 07 - CON CALLBACK
    @Override
    public <T> void submit(Callable<T> task, Callback<T> callback) {

        if (task == null) throw new NullPointerException("task == null");
        if (callback == null) throw new NullPointerException("callback == null");
        
        execute(() -> {
            try {
                T result = task.call();
                callback.onSuccess(result); // caso funzionante
            } catch (Throwable throwable) {
                callback.onFailure(throwable); // caso eccezione
            }
        });
    }

    

    // INNER CLASS
    private class Worker implements Runnable{

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
                    // mando in run un Thread dal mio tasks
                    Runnable runnable = tasks.take();
                    runnable.run();
                } catch (InterruptedException interruptedException) {
                    return;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }

        public void start() {
            thread.start();
        }

        public void shutdown() {
            synchronized (tasks) {
                // imposto shutdown a true e interrompo il thread
                shutdown = true;
                thread.interrupt();
            }
        }


    }
    
}