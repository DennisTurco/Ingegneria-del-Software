package Example06;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import it.unipr.informatica.concurrent.ExecutorService;
import it.unipr.informatica.concurrent.Future;

public class DownloadManager {
	
	private static final int BUFFER_SIZE = 1024;
	private ExecutorService executorService;
	
	public DownloadManager(int connections) {
		if(connections < 1) throw new IllegalArgumentException("connections < 1");
		
		this.executorService = ExecutorService.newFixedThreadPool(connections);
	}
	
	public void shutdown() {
		executorService.shutdown();
	}
	
	public Future<ResourceContent> download(String url) throws IOException {
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
			
			return new ResourceContent(url, data);
			
		}
		
	}
	
}
