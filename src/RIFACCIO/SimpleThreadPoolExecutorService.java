package RIFACCIO;

public class SimpleThreadPoolExecutorService implements ExecutorService {

    private Worker[] workers;
    private boolean shutdown;
    private BlockingQueue<Runnable> tasks;

    public SimpleThreadPoolExecutorService(int count) {
        if (count < 1) throw new IllegalArgumentException("count < 1");

        this.shutdown = false;
        this.tasks = new LinkedBlockingQueue<>();
        this.workers = new Worker[count];
        for (int i=0; i<count; ++i)  {
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
            } catch (InterruptedException exception) {
                // blank
            }
        }
    }

    @Override
    public void shutdown() {
        synchronized (tasks) {
            shutdown = true;

            for (int i=0; i<workers.length; ++i) {
                workers[i].shutdown();
            }
        }
    }


    // INNER CLASS
    private class Worker implements Runnable {

        private boolean shutdown;
        private Thread thread;

        @Override
        public void run() {
            for(;;) {

                synchronized (thread) {
                    if (shutdown) return;
                }

                try {
                    Runnable command = tasks.take();
                    command.run();
                } catch (InterruptedException exception) {
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
            shutdown = true;
            thread.interrupt();
        }
         
    }

}
