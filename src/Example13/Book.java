package Example13;


//immaginando che questo file sia in un project separato, esso sarebbe nel package:
//"it.unipr.informatica.examples.model"
//dovremmo quindi aggiungere:
//	import it.unipr.informatica.beans.Bean;

public interface Book extends Bean {
	public int getID();

	public String getAuthor();

	public String getTitle();
}