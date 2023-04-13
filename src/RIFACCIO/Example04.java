package RIFACCIO;

import java.util.function.UnaryOperator;

public class Example04 {

    private void go() {
        AtomicReference<Integer> counter = new AtomicReference<>(1);
        Incrementer incrementer = new Incrementer();
        Integer i = counter.get();

        // MODO 1
        while ((i = counter.get()) <= 10) {
            System.out.println(i);
            i = counter.updateAndGet(incrementer);
        }

        System.out.println();

        // MODO 2
        UnaryOperator<Integer> inc = new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer value) {
                return value+1;
            }
        };

        while ((i = counter.get()) <= 20) {
            System.out.println(i);
            i = counter.updateAndGet(inc);
        }
        
        
        System.out.println();

        // MODO 3
        while ((i = counter.get()) <= 30) {
            System.out.println(i);
            i = counter.updateAndGet((x) -> {
                return x+1;
            });
        }
        

        System.out.println();

        //MODO 4
        while ((i = counter.get()) <= 40) {
            System.out.println(i);
            i = counter.updateAndGet((x) -> x+1 );
        } 
        
        
    }

    public static void main(String[] args) {
        new Example04().go();
    }
    
    // INNER CLASS
    private class Incrementer implements UnaryOperator<Integer> {
        @Override
        public Integer apply(Integer value) {
            return value+1;
        }  

    }
    
}
