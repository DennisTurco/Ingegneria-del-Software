package Example05;

public class Example05 {
	private void go() {
		DownloadManager downloadManager = new DownloadManager(4);
		downloadManager.download("https://www.google.it");
		downloadManager.download("https://www.youtube.it");
		downloadManager.download("https://www.amazon.it");
		downloadManager.download("https://www.missingwebsite.com"); // link volutamente errato
		downloadManager.download("https://www.ebay.it");
		downloadManager.download("https://www.unipr.it");
		
		try {
			Thread.sleep(10000);
			downloadManager.shutdown();
		} catch (InterruptedException interruptedException) {
			return;
		}
	}
	
	public static final void main(String[] args) {
		new Example05().go();
	}
}
