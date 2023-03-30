package Example09.examples.model.simple;

import Example09.examples.model.Student;

public class SimpleStudent implements Student, Cloneable{

    private int id;
    private String name;
    private String surname;

    public SimpleStudent() {
        this.id = 0;
        this.name = "";
        this.surname = "";
    }

    public SimpleStudent(int id, String name, String surname) {
        if (id < 1) throw new IllegalArgumentException("id < 1");
        if (name == null || name.length() == 0) throw new IllegalArgumentException("name == null || name.length() == 0");
        if (surname == null || surname.length() == 0) throw new IllegalArgumentException("surname == null || surname.length() == 0");

        this.id = id;
        this.name = name;
        this.surname = surname;
    } 

    // ----- METODI GETTER
    @Override
    public int getID() {
        return id;        
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    // ----- METODI SETTER (non sono nell'interfaccia perche' i settere prevedono una decisione: cosa accadrebbe se decidessimo di usare sql anziche' csv??)
    public void setID(int id) {
        if (id < 1) throw new IllegalArgumentException("id < 1");

        this.id = id;
    }

    public void setname(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("name == null || name.lenght() == 0");

        this.name = name;
    }

    public void setsurname(String surname) {
        if (surname == null || surname.length() == 0) throw new IllegalArgumentException("surname == null || surname.lenght() == 0");

        this.surname = surname;
    }

    // ----- ALTRI METODI
    @Override
    public String toString() {
        return "ID = " + id + ", name = " + name + ", surname = " + surname;
    }

    @Override
    public int hashCode() {
        // hashCode() ritorna valori 32 bit, che in queto caso genera un numero uguale per oggetti con stesso stato e diverso altrimenti
        return id + name.hashCode() + surname.hashCode();
    } 

    @Override
    public SimpleStudent clone() {
        return new SimpleStudent(id, name, surname);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SimpleStudent)) return false;
        
        SimpleStudent otherBook = (SimpleStudent) object;
        return (otherBook.id == this.id && otherBook.name.equals(this.name) && otherBook.surname.equals(this.surname));
    }

}