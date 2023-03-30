package Example05;

public class Example05 {
    
    public void go() {
        DownloadManager manager = new DownloadManager(4);

        manager.download("https://www.google.it");
        manager.download("https://www.youtube.it");
        manager.download("https://www.amazon.it");
        manager.download("https://www.misswebsite.com");
        manager.download("https://www.ebay.it");
        manager.download("https://www.maruko.it");

        try {
            Thread.sleep(10000);
            manager.shutdown();
        } catch (InterruptedException exception) {
            return;
        }
    }

    public static void main(String[] args) {
        new Example05().go();
    }
}
