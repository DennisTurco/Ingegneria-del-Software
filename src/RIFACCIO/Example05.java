package RIFACCIO;

public class Example05 {
    private void go() {
        DownloadManager downloadManager = new DownloadManager(4);

        downloadManager.download("https://www.google.it");
        downloadManager.download("https://www.youtube.it");
        downloadManager.download("https://www.amazon.it");
        downloadManager.download("https://www.missingwebsite.com");
        downloadManager.download("https://www.ebay.it");
        downloadManager.download("https://maruko.it");
    }

    public static void main(String[] args) {
        new Example05().go();
    }
}
