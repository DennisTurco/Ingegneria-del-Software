package Example09;

//facendo finta che il package "Example09" fosse in un project a se stante...
//questo file andrebbe messo nel package "it.unipr.informatica.examples.model.simple;"

//per lo stesso discordo andrebbe anche messo:
//import it.unipr.informatica.examples.model.Book;

public class SimpleBook implements Book, Cloneable {
	private int id;
	private String author;
	private String title;
	
	// questo costruttore viola il contratto perchè imposta id = 0 ma non importa per ora
	public SimpleBook() {
		this.id = 0;
		this.author = this.title = "";
	}

	public SimpleBook(int id, String author, String title) {
		if (id < 1) throw new IllegalArgumentException("id < 1");
		if (author == null || author.length() == 0) throw new IllegalArgumentException("author == null || author.length() == 0");
		if (title == null || title.length() == 0) throw new IllegalArgumentException("title == null || title.length() == 0");
		
		this.id = id;
		this.author = author;
		this.title = title;
	}

	@Override
	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		if (author == null || author.length() == 0) throw new IllegalArgumentException("author == null || author.length() == 0");

		this.author = author;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title == null || title.length() == 0) throw new IllegalArgumentException("title == null || title.length() == 0");

		this.title = title;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SimpleBook)) return false;
		
		SimpleBook otherBook = (SimpleBook)other;
		
		return id == otherBook.id && title.equals(otherBook.title) && author.equals(otherBook.author);
	}
	
	@Override
	public SimpleBook clone() {
		return new SimpleBook(id, author, title);
	}
	
	// ritorna un valore a 32 bit che deve essere descrittivo dello stato
	@Override
	public int hashCode() {
		//return (id + title + author).hashCode();
		return id + title.hashCode() + author.hashCode();
	}
	
	
	// questo metodo è pensato per dare una descrizione testuale dello stato dell'oggetto
	@Override
	public String toString() {
		return "id = " + id + ", author = " + author + ", title = " + title;
	}
}
