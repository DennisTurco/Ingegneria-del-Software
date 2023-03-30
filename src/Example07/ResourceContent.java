package Example07;

public final class ResourceContent {
    private String url;
    private byte[] data;

    public ResourceContent(String url, byte[] data) {
        if (url == null) throw new IllegalArgumentException("url == null");
        if (data == null) throw new IllegalArgumentException("data == null");
    
        this.url = url;
        this.data = data;
    }

    public String getURL() {
        return url;
    }

    public byte[] getData() {
        return data;
    }
}
