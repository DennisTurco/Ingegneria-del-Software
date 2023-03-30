package Example09.examples.model.simple;

import Example09.examples.model.Book;

public class SimpleBook implements Book, Cloneable{

    private int id;
    private String author;
    private String title;

    public SimpleBook() {
        this.id = 0;
        this.author = "";
        this.title = "";
    }

    public SimpleBook(int id, String author, String title) {
        if (id < 1) throw new IllegalArgumentException("id < 1");
        if (author == null || author.length() == 0) throw new IllegalArgumentException("author == null || author.length() == 0");
        if (title == null || title.length() == 0) throw new IllegalArgumentException("title == null || title.length() == 0");

        this.id = id;
        this.author = author;
        this.title = title;
    } 

    // ----- METODI GETTER
    @Override
    public int getID() {
        return id;        
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    // ----- METODI SETTER (non sono nell'interfaccia perche' i settere prevedono una decisione: cosa accadrebbe se decidessimo di usare sql anziche' csv??)
    public void setID(int id) {
        if (id < 1) throw new IllegalArgumentException("id < 1");

        this.id = id;
    }

    public void setAuthor(String author) {
        if (author == null || author.length() == 0) throw new IllegalArgumentException("author == null || author.lenght() == 0");

        this.author = author;
    }

    public void setTitle(String title) {
        if (title == null || title.length() == 0) throw new IllegalArgumentException("title == null || title.lenght() == 0");

        this.title = title;
    }

    // ----- ALTRI METODI
    @Override
    public String toString() {
        return "ID = " + id + ", author = " + author + ", title = " + title;
    }

    @Override
    public int hashCode() {
        // hashCode() ritorna valori 32 bit, che in queto caso genera un numero uguale per oggetti con stesso stato e diverso altrimenti
        return id + author.hashCode() + title.hashCode();
    } 

    @Override
    public SimpleBook clone() {
        return new SimpleBook(id, author, title);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SimpleBook)) return false;
        
        SimpleBook otherBook = (SimpleBook) object;
        return (otherBook.id == this.id && otherBook.author.equals(this.author) && otherBook.title.equals(this.title));
    }

}