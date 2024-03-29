package Example13;

import java.util.ArrayList;
import java.util.List;

import aspects.PersistentAspect;
import aspects.PersistentHandler;
import Example13.examples.model.simple.SimpleBook;

public class Example13 {
	private void printAndAddBooks(List<Book> books) {
		if (!books.isEmpty()) {
			System.out.println("Current books:");
		
			for(Book book : books) {
				System.out.println(book);
			}
		}
		
		for(int i = 0; i < 3; ++i) {
			int n = 10 + (int)(90 * Math.random());
			books.add(new SimpleBook(n, "Author #" + n, "Title #" + n));
		}		
	}
	
	private void go() {
		try {
			PersistentHandler<ArrayList<Book>> bookHandler = PersistentAspect.attach("Books.dat", new ArrayList<Book>());
			
			List<Book> books = bookHandler.get();
		
			printAndAddBooks(books);
			
			bookHandler.commit();
			
			System.out.println("Books saved");
		} catch(Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Example13().go();
	}
}
