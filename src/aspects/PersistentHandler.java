package aspects;

import java.io.IOException;
import java.io.Serializable;

// da quello che ho capito stiamo lavorando con i tipi bounded (bounded types).
// in questo caso il tipo parametrico non e' un tipo qualsiasi, ma deve essere compatibile con la classe Serializable
public interface PersistentHandler<T extends Serializable> {
	public T get();
	public void commit() throws IOException;
	public void rollback() throws IOException;
}