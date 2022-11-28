package esercizi_individuali;

import java.util.function.UnaryOperator;
import esercizi_ripasso.AtomicReference;

public class Exercise04 {
	
	private void go() {
		AtomicReference<Integer> counter = new AtomicReference<>(1);
		int i = counter.get();
		
		
		// METODO 1 --> INNER CLASS
		Incrementer incrementer = new Incrementer();
		while(i <= 10) {
			System.out.println(i);
			i = counter.updateAndGet(incrementer);
		}
		
		System.out.println("\n");
		
		// METODO 2 --> INTERFACCIA FUNZIONALE
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
		
		
		// METODO 3 --> LAMBDA-EXPRESSION
		while(i <= 30) {
			System.out.println(i);
			i = counter.updateAndGet((x) -> {
				return x + 1;
			});
		}
		
		
		
		// METODO 4 --> LAMBDA-EXPRESSION V2
		while(i <= 40) {
			System.out.println(i);
			i = counter.updateAndGet((x) -> x + 1);
		}
		
		
	}
	
	public static final void main(String[] args) {
		new Exercise04().go();
	}

	
	// INNER CLASS PER METODO 1
	private static class Incrementer implements UnaryOperator<Integer>{
		@Override
		public Integer apply(Integer value) {
			return value+1;
		}		
	}
	
	
}