package Example04;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;


// java non fa mai troncamenti, promozioni, ecc...
// L'unico modo per fare una conversione e' tramite un cast esplicito

// usando una lamda-expression e' come se stiamo lavorando con una inner class statica,
// quindi non manipola il contenuto della classe contenitrice

public class Example04 {
	private void go() {
		AtomicReference<Integer> counter = new AtomicReference<>(1); // il tipo non puo' essere int perchè e' di tipo primitivo (se avessimo dovuto mettere un tipo float, avremmo messo Float)
		
		// METODO 1: inner class
		int i = counter.get();
		Incrementer incrementer = new Incrementer();
		while(i <= 10) {
			System.out.println(i);
			
			// qui pero' non garantiamo che l'accesso sia atomico in quanto la l'ettura e l'incremento non avengono nello stesso lock
			//counter.set(counter.get() + 1);
			
			
			//counter.updateAndGet(new Incrementer()); //tutte le vlte che si chiama il metodo corrente viene creato un oggetto della classe Incrementer
		
			i = counter.updateAndGet(incrementer);
		}
		
		
		// METODO 2: interfaccia funzionale.
		UnaryOperator<Integer> operator = new UnaryOperator<Integer>() { 
			@Override
			public Integer apply(Integer value) {
				return value + 1;
			}
		
		};
		while(i <= 20) {
			System.out.println(i);
			i = counter.updateAndGet(operator);
		}
		
		
		// METODO 3: labda-expression: anzichè l'interfaccia funzionale, usiamo lambda expression, il vantaggio grosso è che non costruuiamo l'oggetto e ciò non porterà in funzione il garbage collector
		while(i <= 30) {
			System.out.println(i);
			i = counter.updateAndGet((x) -> {
				return x + 1;
			});
		}
		
		// METODO 4: labda-expression versione 2
		while(i <= 40) {
			System.out.println(i);
			i = counter.updateAndGet((x) -> x + 1);
		}
		
	}
	
	public static final void main(String[] args) {
		new Example04().go();
	}
	
	// inner class
	private static class Incrementer implements UnaryOperator<Integer> {
		@Override
		public Integer apply(Integer value) {
			return value + 1;
		}
	}
}
