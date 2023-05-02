package Example14;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SimpleDownloadManager implements DownloadManager {
	
	private static final int BUFFER_SIZE = 1024;
	
	@Override
	public ResourceContent download(String url) throws IOException {
		if (url == null) throw new IllegalArgumentException("url == null");
		
		try (InputStream inputStream = new URL(url).openStream();
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			
			byte[] buffer = new byte[BUFFER_SIZE];
			
			int read = bufferedInputStream.read(buffer);
			
			while (read >= 0) {
				byteArrayOutputStream.write(buffer, 0, read);
				read = bufferedInputStream.read(buffer);
			}
			
			byte[] data = byteArrayOutputStream.toByteArray();
			
			return new ResourceContent(url, data);
			
		} catch (IOException exception) {
			throw exception;
		} catch (Throwable exception) {
			throw new IOException(exception);
		}
	}
	
}
