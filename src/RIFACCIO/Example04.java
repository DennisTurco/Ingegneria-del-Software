package RIFACCIO;

import java.util.function.UnaryOperator;

public class Example04 {

    private void go() {
        AtomicReference<Integer> count = new AtomicReference<>(1); 
        Incrementer incrementer = new Incrementer();
        int i = count.get();

        // MODO 1
        while(i <= 10) {
            System.out.println(i);
            i = count.updateAndGet(incrementer);
        }

        System.out.println();

        // MODO 2
        UnaryOperator<Integer> operator = new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer value) {
                return value+1;
            }  
        };      
        
        while (i <= 20) {
            System.out.println(i);
            i = count.getAndUpdate(operator);
        }
        
        System.out.println();

        // MODO 3
        while (i <= 30) {
            System.out.println(i);
            i = count.updateAndGet((x) -> {
                return x+1;
            });    
        }

        System.out.println();

        //MODO 4
        while (i <= 40) {
            System.out.println(i);
            i = count.updateAndGet((x) -> x+1);
        }
        
    }

    public static void main(String[] args) {
        new Example04().go();
    }
    
    // INNER CLASS
    private static class Incrementer implements UnaryOperator<Integer> {

        @Override
        public Integer apply(Integer value) {
            return value+1;
        }

    }
}
