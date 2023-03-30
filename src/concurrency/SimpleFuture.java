package concurrency;

// un Oggetto Future e' una rapppresentazione di qualcosa che puo' esistere nel futuro
// parametrico perche' il futoro puo' essere di qualsiasi tipo.
// ci dice che nel futuro ci sara' qualcosa qui dentro, magari un eccezione, ma avremo qualcosa
// come se fosse un oggetto in pending (in attesa) che potra' poi essere un valore o un eccezione
// quando done e' true siquifica che ha finito di caricare
public class SimpleFuture<T> implements Future<T> {
    
    // per la sezione critica
    private Object mutex;

    // risultato di tipo T
    private T value;

    // booleano per capire se abbimo prodotto un risultato oppure eccezione
    // se done e' true: abbiamo o eccezione o oggetto
    // se done e' false non abbiamo nulla
    private boolean done;

    // per memorizzare l'eccezione
    private Throwable exception;

    SimpleFuture() {
        this.mutex = new Object();
        this.done = false;
        this.exception = null;
        this.value = null;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        synchronized (mutex) {
            if (!done) mutex.wait(); // si puo' anche mettere while

            if (exception != null) throw new ExecutionException(exception);

            return value;
        }
    }

    @Override
    public boolean isDone() {
        synchronized (mutex) {
            return done;
        }
    }

    // il set del valore o dell'eccezione non sono comprese dall'interfaccia, e quindi verranno realizzati qui internamente
	// ad ogni modo avranno una visibilita' protected
    public void setValue(T object) {
        synchronized (mutex) {

            // controllo per vedere se qualcuno ha compromesso il valore di done
            // se done == true significa che abbiamo gia' prodotto un risultato
            if (done) throw new IllegalStateException("done == true"); 
            
            value = object;

            // ha finito di caricare
            done = true;
            mutex.notifyAll();
        }
    }

    public void setException(Throwable object) {
        
        if (object == null) throw new IllegalArgumentException("object == null");
        
        synchronized (mutex) {

            // controllo per vedere se qualcuno ha compromesso il valore di done
            if (done) throw new IllegalStateException("done == true"); 
            
            exception = object;

            // ha finito di caricare
            done = true;
            mutex.notifyAll();
        }
    }

}