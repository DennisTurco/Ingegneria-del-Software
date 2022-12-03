package Example09;

// facendo finta che il package "Example09" fosse in un project a se stante...
// questo file andrebbe messo nel package "it.unipr.informatica.examples.model"

// per lo stesso discordo andrebbe anche messo:
// import it.unipr.informatica.beans.bean

public interface Student extends Bean{
	public int getID();
	public String getName();
	public String getSurname();
}
