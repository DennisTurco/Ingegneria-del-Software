package Example13.examples.model.simple;

import Example13.examples.model.Book;

public class SimpleBook implements Book, Cloneable {

	private int id;
	private String author;
	private String title;

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
	public int getID() {
		return id;
	}

	public void setID(int id) {
		if (id < 1) throw new IllegalArgumentException("id < 1");

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

		SimpleBook otherBook = (SimpleBook) other;

		return id == otherBook.id && title.equals(otherBook.title) && author.equals(otherBook.author);
	}

	@Override
	public SimpleBook clone() {
		return new SimpleBook(id, author, title);
	}

	@Override
	public int hashCode() {
		return id + title.hashCode() + author.hashCode();
	}

	@Override
	public String toString() {
		return "ID=" + id + ", author=" + author + ", title=" + title;
	}
}
