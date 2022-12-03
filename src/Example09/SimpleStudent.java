package Example09; 

//facendo finta che il package "Example09" fosse in un project a se stante...
//questo file andrebbe messo nel package "it.unipr.informatica.examples.model.simple;"

//per lo stesso discordo andrebbe anche messo:
//import it.unipr.informatica.examples.model.Student;

public final class SimpleStudent implements Student, Cloneable {
	private int ID;

	private String name;

	private String surname;

	public SimpleStudent() {
		this.ID = 0;

		this.name = this.surname = "";
	}

	public SimpleStudent(int ID, String name, String surname) {
		if (ID < 1)
			throw new IllegalArgumentException("ID < 1");
		
		if (surname == null || surname.length() == 0)
			throw new IllegalArgumentException("surname == null || surname.length() == 0");
		
		if (name == null || name.length() == 0)
			throw new IllegalArgumentException("name == null || name.length() == 0");

		this.ID = ID;
		this.name = name;
		this.surname = surname;
	}

	@Override
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		if (name == null || name.length() == 0)
			throw new IllegalArgumentException("name == null || name.length() == 0");

		this.name = name;
	}

	@Override
	public String getSurname() {
		return surname;
	}

	@Override
	public void setSurname(String surname) {
		if (surname == null || surname.length() == 0)
			throw new IllegalArgumentException("surname == null || surname.length() == 0");

		this.surname = surname;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SimpleStudent))
			return false;
		
		SimpleStudent otherStudent = (SimpleStudent)other;
		
		return ID == otherStudent.ID && name.equals(otherStudent.name) && surname.equals(otherStudent.surname);
	}
	
	@Override
	protected SimpleStudent clone() throws CloneNotSupportedException {
		return new SimpleStudent(ID, name, surname);
	}

	@Override
	public int hashCode() {
		return (ID + name + surname).hashCode();
	}
	
	@Override
	public String toString() {
		return "ID=" + ID + ", surname=" + surname + ", name=" + name;
	}
}
