package Example04;

import java.util.function.UnaryOperator;

import concurrency.atomic.AtomicReference;

public class Example04 {
    public void go() {
        AtomicReference<Integer> counter = new AtomicReference<>(1);
        int i = counter.get();  // i avra' valore 1
        Incrementer incrementer = new Incrementer();

        // modo 1
        while((i = counter.get()) <= 10) {
            System.out.println(i);
            i = counter.updateAndGet(incrementer);
        }

        counter.set(1);
        i = counter.get();

        // modo 2
        UnaryOperator<Integer> operator = new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer value) {
                return value+1;
            }

        };

        while(i <= 20) {
            System.out.println(i);
            i = counter.getAndUpdate(operator);
        }

        // modo 3
        while(i <= 30) {
            System.out.println(i);
            i = counter.updateAndGet((x) -> {
                return x+1;
            });
        }

        // modo 4
        while(i <= 40) {
            System.out.println(i);
            i = counter.updateAndGet((x) -> x+1);
        }

    }

    public static void main(String[] args) {
        new Example04().go();
    }

    // INNER CLASS 
    private static class Incrementer implements UnaryOperator<Integer>{

        @Override
        public Integer apply(Integer value) {
            return value+1;
        }
    }
}
