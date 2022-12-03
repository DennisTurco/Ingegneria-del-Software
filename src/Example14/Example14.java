package Example14;

import it.unipr.informatica.aspects.ActiveAspect;
import it.unipr.informatica.aspects.ActiveHandler;
import it.unipr.informatica.concurrent.Future;

public class Example14 {	
	
	private void process(ResourceContent content) {
		System.out.println("Downloaded " + content.getData().length + " bytes from " + content.getURL());
	}
	
	private void go() {
		DownloadManager downloadManager = new SimpleDownloadManager();
		
		ActiveHandler<ActiveDownloadManager> downloadManager = new ActiveAspect().attach(ActiveDownloadManager.class, downloadManager);
		
		ActiveDownloadManager activeDownloadManager = downloadManagerHandler.get();
		
		activeDownloadManager.download("https://www.unipr.it", (ResourceContent content) -> process(content));
		
		Future<ResourceContent> future = activeDownloadManager.download("https://www.google.it");
		
		try {
			ResourceContent content = future.get();
			process(content);
		} catch(Throwable throwable) {
			throwable.printStackTrace();
		}
		
		downloadManagerHandler.shutdown();
	
	public static void main(String[] args) {
		new Example14().go();
	}
}
