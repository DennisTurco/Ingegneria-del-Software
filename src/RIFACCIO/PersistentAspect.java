package RIFACCIO;

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

    private PersistentAspect() {
        // blank
    }

    public static <T extends Serializable> PersistentHandler<T> attach(String filename, T object) throws IOException {
        return attach(new File(filename), object);
    }

    public static <T extends Serializable> PersistentHandler<T> attach(File file, T object) throws IOException {
        if (object == null) throw new IllegalArgumentException("object == null");
        if (file == null) throw new IllegalArgumentException("file == null");
        if (file.exists() && !file.isFile()) throw new IllegalArgumentException("file.exists() && !file.isFile()");

        InnerPersistentHandler<T> handler = new InnerPersistentHandler<T>(file);

        if (file.exists()) {
            handler.load();
        } else {
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

        @Override
        public T get() {
            if (target == null) throw new IllegalStateException("target == null");

            return target;
        }

        @Override
        public void rollback() throws IOException {
           load();
        }

        @Override
        public void commit() throws IOException {
            save();
        }

        private void load() throws IOException {
            try (InputStream inputStream = new FileInputStream(file);
			  BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			  ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream)) {
				
                Object object = objectInputStream.readObject();

                @SuppressWarnings("unchecked")
                T result = (T) object;
                
                target = result;

            } catch (IOException exception) {
                throw exception;
            } catch (Throwable throwable) {
                throw new IOException(throwable);
            }

            if (target == null) throw new IllegalStateException("target == null");
        }

        private void save() throws IOException {
            if (target == null) throw new IllegalStateException("target == null");

            try (OutputStream outputStream = new FileOutputStream(file);
              BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
              ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)){

                objectOutputStream.writeObject(target);
            } catch (IOException exception) {
                throw exception;
            } catch (Throwable throwable) {
                throw new IOException(throwable);
            }
        }

    }
    
}
