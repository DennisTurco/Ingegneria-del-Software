package Example11;

import java.util.LinkedList;
import java.util.List;

import aspects.SharedAspect;

public class Example11 {
    
    private void go() {
        
        // usando questo tipo di oggetto alla fine non avremo effettivamente 10000 come risultato, ma un po meno perche' non ci garantisce la mutua escusione di per se
        //List<Integer> list = new LinkedList<>();

        // utilizzando questo tipo di oggetto alla fine otterremo 10000 come risultato perche' lo SharedAspect ci garantisce condivisione in mutua escusione 
        List<Integer> list = SharedAspect.attach(new LinkedList<>());

        for (int i=0; i<10; i++) {
            int id = i;
            new Thread(() -> {
                for (int j=0; j<10000; j++) {
                    list.add(j);
                }

                System.out.println("Thread " + id + " done");

            }).start();
        }

        try {
            Thread.sleep(5000);
            System.out.println(list.size());
        } catch (Throwable throwable) {
            // blank
        }
    }

    public static void main(String[] args) {
        new Example11().go();
    }
}
