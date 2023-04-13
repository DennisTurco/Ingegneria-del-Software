package RIFACCIO;

public final class ResourceContent {
    private String url;
    private byte[] data;

    public ResourceContent(String url, byte[] data) {
        if (url == null || url.length() == 0) throw new IllegalArgumentException("url == null || url.length == 0");
        if (data == null) throw new IllegalArgumentException("data == null");

        this.data = data;
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    public byte[] getData() {
        return data;
    }
}
