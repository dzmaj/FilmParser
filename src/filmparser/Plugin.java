package filmparser;

import java.nio.ByteBuffer;

public class Plugin {
    private String name;
    private String url;
    private byte[] checksum;
    private int length;

    public Plugin() {
        name = "";
        url = "";
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.length = 4 + name.length() + url.length() + 2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getChecksum() {
        return checksum;
    }

    public void setChecksum(byte[] checksum) {
        this.checksum = checksum;
    }

    public String getChecksumString() {
        String str = "";
        for (int i = 0; i < checksum.length; i++) {
            str += String.format("%02x", checksum[i]);
        }
        return str;
    }

    public long getChecksumValue() {
        int val = 0;
        val = ByteBuffer.wrap(checksum).getInt();
        // should be 2578814965
        long unsignedValue = val & 0xffffffffL;
        return unsignedValue;
    }
}
