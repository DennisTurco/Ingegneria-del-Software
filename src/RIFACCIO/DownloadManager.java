package RIFACCIO;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class DownloadManager {

    private static final int BUFFER_SIZE = 1024;
    private ExecutorService executorService;

    public DownloadManager(int connections) {
        if (connections < 1) throw new IllegalArgumentException("connections <  1");
        
        executorService = Executors.newFiExecutorService(connections);
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public void download(String url) {
        if (url == null) throw new IllegalArgumentException("url == null");

        executorService.execute(() -> downloadAndPrint(url));
    }

    private void downloadAndPrint(String url) {
        try (InputStream inputStream = new URL(url).openStream();
          BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            
            byte[] buffer = new byte[BUFFER_SIZE];
            int read = bufferedInputStream.read(buffer);
            while (read >= 0) {
                outputStream.write(buffer, 0, read);
                read = bufferedInputStream.read(buffer);
            }

            byte[] data = outputStream.toByteArray();
            System.out.println("Downloaded" + data.length +" bytes from " + url);

        } catch (Throwable throwable) {
            System.out.println("Cannot download with error: " + throwable.getMessage());
        }
    }


    // EXAMPLE 06
    public Future<ResourceContent> download2(String url) {
        if (url == null) throw new IllegalArgumentException("url == null");

        return executorService.submit(() -> downloadResourceContent(url));
    }

    // EXAMPLE 07
    public void download(String url, Callback<ResourceContent> callback) {
        if (url == null) throw new IllegalArgumentException("url == null");

        executorService.submit(() -> downloadResourceContent(url), callback);
    }



    // EXAMPLE 06 and EXAMPLE07 
    private ResourceContent downloadResourceContent(String url) throws IOException{
        try (InputStream inputStream = new URL(url).openStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[BUFFER_SIZE];

            int read = bufferedInputStream.read(buffer);

            while (read >= 0) {
                outputStream.write(buffer, 0, read);
                read = bufferedInputStream.read(buffer);
            }

            byte[] data = outputStream.toByteArray();
            
            return new ResourceContent(url, data);
        }
    }
}