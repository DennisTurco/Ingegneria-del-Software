package Example14;

import aspects.ActiveAspect;
import aspects.ActiveHandler;
import concurrent.Future;

public class Example14 {
	
	private void process(ResourceContent content) {
		System.out.println("Downloaded " + content.getData().length + " bytes from " + content.getURL());
	}
	
	private void go() {
			
		DownloadManager downloadManager = new SimpleDownloadManager();
		
		ActiveHandler<ActiveDownloadManager> activeHandler = ActiveAspect.attach(ActiveDownloadManager.class, downloadManager);
	
		ActiveDownloadManager activeDownloadManager = activeHandler.get(); 
				
		activeDownloadManager.download("https://www.unipr.it", (ResourceContent content) -> process(content));
		
		Future<ResourceContent> future = activeDownloadManager.download("https://www.google.it");
		
		try {
			ResourceContent content = future.get();

			process(content);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}

		activeHandler.shutdown();
	}
	

	public static void main(String[] args) {
		new Example14().go();
	}
}
