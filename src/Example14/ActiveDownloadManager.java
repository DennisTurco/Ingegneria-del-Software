package Example14;

import it.unipr.informatica.aspects.Active;
import it.unipr.informatica.concurrent.Callback;
import it.unipr.informatica.concurrent.Future;

public interface ActiveDownloadManager extends Active<DownloadManager> {
	public Future<ResourceContent> download(String url);
	
	public void download(String url, Callback<ResourceContent> callback);
}
