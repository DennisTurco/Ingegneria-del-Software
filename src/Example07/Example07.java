package Example07;

public class Example07 {
    
	private void process(ResourceContent content) {
		System.out.println("Downloaded " + content.getData().length + " bytes from " + content.getURL());
	}

	private void go() {
		DownloadManager downloadManager = new DownloadManager(4);

		downloadManager.download("https://www.google.it", this::process);
		downloadManager.download("https://www.youtube.it", this::process);
		downloadManager.download("https://www.amazon.it", this::process);
		downloadManager.download("https://www.missingwebsite.com", this::process);
		downloadManager.download("https://www.ebay.it", this::process);
		downloadManager.download("https://www.unipr.it", this::process);

		downloadManager.shutdown();
	}

	public static void main(String[] args) {
		new Example07().go();
	}
}