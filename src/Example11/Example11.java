package Example11;

import java.util.LinkedList;
import java.util.List;

import it.unipr.informatica.aspects.SharedAspect;

public class Example11 {
	private void go() {
		
		//List<Integer> list = new LinkedList<>();
		
		// con questo vanno in mutua esclusione e quindi ne samperanno davvero 100000
		List<Integer> list = SharedAspect.attach(new LinkedList<>());
		
		// costruiamo 10 Thread
		for(int i = 0; i < 10; ++i) {
			
			// id non cambia, perchè viene creato e distrutto a tutte le iterazioni
			int id = i;
			
			// ogni Thread mette nella lista 10000 numeri interi
			new Thread(() -> {
				for (int c = 0; c < 10000; ++c)
					list.add(c);
				
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
		} catch(Throwable throwable) {
			// Blank
		}
	}

	public static void main(String[] args) {
		new Example11().go();
	}
}
