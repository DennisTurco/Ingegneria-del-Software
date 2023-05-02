package Example14;

import aspects.Active;
import concurrent.Callback;
import concurrent.Future;

public interface ActiveDownloadManager extends Active<DownloadManager>{
	public Future<ResourceContent> download(String url);
	public void download(String url, Callback<ResourceContent> callback);
}
