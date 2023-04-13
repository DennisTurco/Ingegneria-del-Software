package Example14;

import aspects.Active;
import concurrency.Callback;
import concurrency.Future;

public interface ActiveDownloadManager extends Active<DownloadManager> {
	public Future<ResourceContent> download(String url);
	
	public void download(String url, Callback<ResourceContent> callback);
}
