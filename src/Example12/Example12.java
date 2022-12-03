package Example12;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import it.unipr.informatica.aspects.LoggingAspect;

public class Example12 {
	private void go() {
		Comparator<String> comparator = new CaseInsensitiveComparator();
		
		comparator = LoggingAspect.attach(comparator);
		
		// un TreeSet è un albero binario che organizza le sue foglia a profondità minima 
		Set<String> set = new TreeSet<>(comparator);
		
		set.add("Verdi");
		set.add("Bianchi");
		set.add("Neri");
		set.add("Rossi");
		
		set.contains("Neri");
		set.contains("Viola");
	}

	public static void main(String[] args) {
		new Example12().go();
	}
	
	private static class CaseInsensitiveComparator implements Comparator<String> {
		
		// confronta 2 elementi:
		// se sono uguali ritorna 0
		// se il sinistro è minore del destro ritorniamo un numero negativo
		// altrimenti positivo
		@Override
		public int compare(String left, String right) {
			if (left == null || right == null) throw new IllegalArgumentException("o1 == null || o2 == null");
			return left.compareToIgnoreCase(right);
		}
	}
}
