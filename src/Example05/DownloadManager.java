package Example05;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import concurrency.ExecutorService;
import concurrency.Executors;

public class DownloadManager {
    private static final int BUFFER_SIZE = 1024;
    private ExecutorService executorService;

    public DownloadManager(int connections) {
        if (connections < 1) throw new IllegalArgumentException("connections < 1");
        
        // creo un thread pool di n connessioni
        this.executorService = Executors.newFixedThreadPool(connections);
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public void download (String url) {
        if (url == null) throw new NullPointerException("url == null");

        executorService.execute(() -> downloadAndPlay(url));
    }   

    private void downloadAndPlay(String url) {
        try (InputStream inputStream = new URL(url).openStream();
            BufferedInputStream bufferInputStream = new BufferedInputStream(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream() ) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int read = bufferInputStream.read(buffer);
                while(read >= 0) {
                    outputStream.write(buffer, 0, read);
                    read = bufferInputStream.read(buffer);
                }

                byte[] data = outputStream.toByteArray();
                System.out.println("Downloaded" + data.length + " bytes from " + url);
        }  catch (Throwable throwable) {
            System.err.println("Cannot download with error: " + throwable.getMessage());
        }
    }
}
