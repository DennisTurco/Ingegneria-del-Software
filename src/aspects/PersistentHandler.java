package aspects;

import java.io.IOException;
import java.io.Serializable;

// da quello che ho capito stiamo lavorando con i tipi bounded (bounded types).
// in questo caso il tipo parametrico non e' un tipo qualsiasi, ma deve essere compatibile con la classe Serializable
//L'interfaccia PersistentHandler è un'interfaccia generica in Java che definisce tre metodi senza corpo, che devono essere implementati dalle classi che la implementano.
public interface PersistentHandler<T extends Serializable> {
	/* 
	I tipi bounded in Java sono dei vincoli che possono essere imposti su un parametro di tipo in una classe o in una interfaccia. 
	Questi vincoli specificano che il parametro di tipo deve essere una sottoclasse di una classe specifica o deve implementare un'interfaccia specifica. 
	In altre parole, i tipi bounded limitano il tipo di oggetti che possono essere utilizzati come parametri di tipo in una determinata classe o interfaccia.
	Ad esempio, la riga di codice `PersistentHandler<T extends Serializable>` utilizza un tipo bounded per limitare il parametro di tipo `T` a classi 
	che implementano l'interfaccia `Serializable`. Ciò garantisce che l'oggetto persistente possa essere serializzato e quindi memorizzato in modo permanente 
	nella sorgente dati.
	*/
	
	//Il metodo get() restituisce un oggetto generico di tipo T che estende Serializable. 
	//Questo metodo viene utilizzato per ottenere l'oggetto persistente dalla sorgente dati.
	public T get();
	
	//Il metodo commit() salva le modifiche apportate all'oggetto persistente nella sorgente dati. 
	public void commit() throws IOException;
	
	//Questo metodo viene utilizzato quando si desidera annullare le modifiche apportate all'oggetto persistente e ripristinare lo stato precedente.
	public void rollback() throws IOException;
}
