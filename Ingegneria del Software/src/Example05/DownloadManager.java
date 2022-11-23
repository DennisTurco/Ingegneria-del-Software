package Example05;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import it.unipr.informatica.concurrent.ExecutorService;
import it.unipr.informatica.concurrent.Executors;

public final class DownloadManager {
	private static final int BUFFER_SIZE = 1024;
	
	private ExecutorService executorService;
	
	public DownloadManager(int connection) {
		if (connection < 1) throw new IllegalArgumentException("connection < 1");
		
		this.executorService = Executors.newFixedThreadPool(connection);
	}
	
	public void shutdown() {
		executorService.shutdown();
	}
	
	public void download(String url) {
		if (url == null) throw new IllegalArgumentException("url == null");
		
		executorService.execute(() -> downloadAndPrint(url));
	}
	
	private void downloadAndPrint(String url) {
		// ByteArrayOutputStream stream di output in cui possiamo scrivere dati che man mano si allunga
		try (InputStream inputStream = new URL(url).openStream();
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[BUFFER_SIZE];
			
			int read = bufferedInputStream.read(buffer);
			
			while(read >= 0) {
				outputStream.write(buffer, 0, read);
				read = bufferedInputStream.read(buffer);
			}
			
			byte[] data = outputStream.toByteArray();
			
			System.out.println("Download " + data.length + " byte from " + url);
			
		} catch(Throwable throwable) {
			System.err.println("Cannot download with error: " + throwable.getMessage());
		}
		
	}
	
}
