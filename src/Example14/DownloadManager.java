package Example14;

import java.io.IOException;

public interface DownloadManager {
	public ResourceContent download(String url) throws IOException;
}
