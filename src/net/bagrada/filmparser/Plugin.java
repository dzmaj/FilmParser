package net.bagrada.filmparser;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

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
        for (byte b : checksum) {
            str += String.format("%02x", b);
        }
        return str;
    }

    public long getChecksumValue() {
        int val = ByteBuffer.wrap(checksum).getInt();
        return val & 0xffffffffL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plugin plugin = (Plugin) o;
        return Objects.equals(name, plugin.name) && Objects.equals(url, plugin.url) && Arrays.equals(checksum, plugin.checksum);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, url);
        result = 31 * result + Arrays.hashCode(checksum);
        return result;
    }
}
