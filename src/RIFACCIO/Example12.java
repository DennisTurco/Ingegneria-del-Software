package RIFACCIO;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Example12 {
    
    private void go() {
        Comparator<String> comparator = new CaseIntensiveComparator();

        comparator = LoggingAspect.attach(comparator);

        Set<String> set = new TreeSet<>(comparator);
        set.add("Verdi");
        set.add("Binachi");
        set.add("Neri");
        set.add("Rossi");

        set.contains("Neri");
        set.contains("Viola");
     }

    public static void main(String[] args) {
        new Example12().go();
    }   

    // INNER CLASS
    private class CaseIntensiveComparator implements Comparator<String>{

        @Override
        public int compare(String left, String right) {
            if (left == null || right == null) throw new NullPointerException("left == null || right == null");
            return left.compareToIgnoreCase(right);
        }

    }
}
