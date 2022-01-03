package filmparser;

public class Plugin {
    private String name;
    private String url;
    private int[] checksum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int[] getChecksum() {
        return checksum;
    }

    public void setChecksum(int[] checksum) {
        this.checksum = checksum;
    }
}
