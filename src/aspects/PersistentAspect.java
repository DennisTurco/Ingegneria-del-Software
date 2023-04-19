package aspects;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class PersistentAspect {

	// non voglio che questa classe venga istanziata
	private PersistentAspect() {
		// blank
	}
    
    public static <T extends Serializable> PersistentHandler<T> attach(String fileName, T object) throws IOException {
		return attach(new File(fileName), object);
	}

	public static <T extends Serializable> PersistentHandler<T> attach(File file, T object) throws IOException {
		if (object == null) throw new IllegalArgumentException("object == null");
		if (file == null) throw new IllegalArgumentException("file == null");
		//Questo è un modo per garantire che il parametro file passato a un metodo debba essere un file regolare esistente. Se il parametro non è un file regolare esistente, 
		//viene sollevata un'eccezione per segnalare l'errore e interrompere l'esecuzione del metodo.
		if (file.exists() && !file.isFile()) throw new IllegalArgumentException("file.exists() && !file.isFile()");

		InnerPersistentHandler<T> handler = new InnerPersistentHandler<T>(file);

		// se il file esiste viene caricato e reimpostato a valori esistenti
		if (file.exists()) {
			handler.load();
		}
        else {
			handler.target = object;
        }

		return handler;
	}


    // INNER CLASS
    private static class InnerPersistentHandler<T extends Serializable> implements PersistentHandler<T> {

        private File file;
        private T target;

        private InnerPersistentHandler(File file) {
            this.file = file;
            this.target = null;
        }

		// permette di accedere all'oggetto a cui è stato attaccato l'oggetto persistent
        @Override
        public T get() {
            if (target == null) throw new IllegalStateException("target == null");

            return target;
        }

		// carica l'oggetto dal file
        @Override
        public void rollback() throws IOException {
            load();
        }

		// salva l'oggetto sul file
        @Override
        public void commit() throws IOException {
            save();
        }

	    /*
	 Il codice utilizza un costrutto `try-with-resources` per aprire un file e leggere un oggetto dalla sorgente dati. 
	Il costrutto `try-with-resources` in Java è utilizzato per gestire automaticamente le risorse come i file e le connessioni al database. 
	In questo caso, l'oggetto `InputStream` viene creato per aprire il file, e viene utilizzato un oggetto `BufferedInputStream` 
	per migliorare le prestazioni delle operazioni di lettura. Infine, viene utilizzato un oggetto `ObjectInputStream` per leggere 
	l'oggetto dalla sorgente dati.

	Il metodo `readObject()` dell'oggetto `ObjectInputStream` viene utilizzato per leggere l'oggetto dalla sorgente dati. 
	Questo metodo restituisce un oggetto generico di tipo `Object`, che deve essere convertito nel tipo `T` utilizzando il casting.
	    */
        private void load() throws IOException {
			try (InputStream inputStream = new FileInputStream(file);
					BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
					ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream)) {
				Object object = objectInputStream.readObject(); // si chiedono i dati all'object input stream che lo chiede al buffer input stream, che lo chiede al file input stream e quindi viene letto il file

				@SuppressWarnings("unchecked")
				T result = (T) object;

				target = result;
			} catch (IOException exception) {
				throw exception;
			} catch (Throwable throwable) {
				throw new IOException(throwable);
			}

			if (target == null) throw new IllegalStateException("target == null"); //??? non dovrebbe essere in un finally??
		}

		// salvataggio su file
		private void save() throws IOException {
			if (target == null) throw new IllegalStateException("target == null");

			// l'otput stream serve per scrivere su flussi di dati
			try (OutputStream outputStream = new FileOutputStream(file);
					BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)) {
				objectOutputStream.writeObject(target);
			} catch (IOException exception) {
				throw exception;
			} catch (Throwable throwable) {
				throw new IOException(throwable);
			}
		}

    }



}
