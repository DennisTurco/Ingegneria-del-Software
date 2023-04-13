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

        // costruiamo 10 Thread
        for (int i=0; i<10; i++) {
            // id non cambia, perchè viene creato e distrutto a tutte le iterazioni
            int id = i;
            // ogni Thread mette nella lista 10000 numeri interi
            new Thread(() -> {
                for (int j=0; j<10000; j++) {
                    list.add(j);
                }

                // dobbiamo mettere id perchè i non è final e quindi non è visibile dalla Inner Class
                System.out.println("Thread " + id + " done");

            }).start();
        }

        try {
            // aspettiamo 5 secondi per evitare la join
            Thread.sleep(5000);

            // se usiamo "List<Integer> list = new LinkedList<>();" non stamperà quello che ci aspeettiamo (ossia 100000) ma un valore molto inferiore
			// questo perchè non siamo in mutua esclusione quindi alcuni thread riscriveranno su stessi puntatori

            System.out.println(list.size());
        } catch (Throwable throwable) {
            // blank
        }
    }

    public static void main(String[] args) {
        new Example11().go();
    }
}