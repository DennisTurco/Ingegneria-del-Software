package RIFACCIO;

import java.util.LinkedList;
import java.util.List;

public class Example11 {

    private void go() {
        List<Integer> list = SharedAspect.attach(new LinkedList<>());

        for (int i=0; i<10; ++i) {
            int id = i;

            Consumer consumer = new Consumer(id, list);
            consumer.run();
        }

        try {
            Thread.sleep(5000);
            System.out.println(list.size());
        } catch(Throwable throwable) {
            throwable.printStackTrace();
        }
        
    }

    public static void main(String[] args) {
        new Example11().go();
    }


    // INNER CLASS
    private static class Consumer implements Runnable{
        private int id;
        private List<Integer> list;

        private Consumer(int id, List<Integer> list) {
            if (id < 0) throw new IllegalArgumentException("id < 1");
            if (list == null) throw new NullPointerException("list == null");
            this.id = id;
            this.list = list;
        }

        @Override
        public void run() {
            for (int j=0; j<10000; ++j) {
                list.add(j);
            }

            System.out.println("Thread " + id + " terminated");
        }

    }
}
