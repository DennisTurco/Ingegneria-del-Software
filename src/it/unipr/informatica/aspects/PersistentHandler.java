package aspects;

import java.io.IOException;
import java.io.Serializable;

public interface PersistentHandler<T extends Serializable> {
    public T get();
    public void rollback() throws IOException;
    public void commit() throws IOException;
}