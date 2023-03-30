package Example09.examples;

import java.util.List;

import Example09.beans.Bean;
import Example09.beans.BeanLoader;
import Example09.examples.model.simple.SimpleBook;
import Example09.examples.model.simple.SimpleStudent;

public class Example09 {
    private void go() {

        try {
            // stampo il file Student.csv
            BeanLoader loader = new BeanLoader();
            List<SimpleStudent> studentBeans = loader.load(SimpleStudent.class, "Students.csv");
            for (Bean bean : studentBeans) {
                System.out.println(bean);
            }
            System.out.println();


            // non potrei scrivere questo:
            // List<CloneNotSupportedException> listBean = loader.load(CloneNotSupportedException.class, "prova.csv");
            // questo perche' l'oggetto CloneNotSupportedException non estende Bean
            // invece sia SimpleStudent, che SimpleBook estendono Bean.

            // stampo il file Books.csv
            List<SimpleBook> bookBeans = loader.load(SimpleBook.class, "Books.csv");
            for (Bean bean : bookBeans) { 
                System.out.println(bean);
            }
        } catch (Throwable throwable) {
            System.err.println("Cannot load beans with message " + throwable.getMessage());
        }

         
    }

    public static void main(String[] args) {
        new Example09().go();
    }
}
