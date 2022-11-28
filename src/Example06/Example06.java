package Example06;

import java.util.concurrent.ExecutionException;

import it.unipr.informatica.concurrent.ExecutionExeption;
import it.unipr.informatica.concurrent.Future;

public class Example06 {
	private void process(Future<ResourceContent> future) {
		try {
			ResourceContent content = future.get();
			
			System.out.println("Download " + content.getData().lengh + " bytes from " + content.getURL());
		} catch(ExecutionExeption executionExeption) {
			System.err.println("Cannot download with error: " + ExecutionException.getMessage());
		} catch(InterruptedException interruptedException) {
			System.err.println("Interrupted");
			System.exit(-1);
		}
	}
	
	private void go() {
		DownloadManager downloadManager = new DownloadManager();
		
		Future<ResourceContent> future1 = downloadManager.download("https://www.google.it");
		Future<ResourceContent> future2 = downloadManager.download("https://www.youtube.it");
		Future<ResourceContent> future3 = downloadManager.download("https://www.amazon.it");
		Future<ResourceContent> future4 = downloadManager.download("https://www.missingwebsite.com");
		Future<ResourceContent> future5 = downloadManager.download("https://www.ebay.it");
		Future<ResourceContent> future6 = downloadManager.download("https://www.unipr.it");
		
		process(future1);
		process(future2);
		process(future3);
		process(future4);
		process(future5);
		process(future6);
		
		downloadManager.shutdown();
	}
	
	public static final void main(String[] args) {
		new Example06().go();
	}
	
}
