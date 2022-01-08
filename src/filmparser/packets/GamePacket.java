package filmparser.packets;

import java.nio.ByteBuffer;

public class GamePacket {
    private int length;
    private byte type;
    private byte sender;
    private int time;
    private byte[] data;

    public GamePacket() {
    }

    public GamePacket(byte[] bytes) {
        if (bytes[0] == 0) {
            throw new IllegalArgumentException("size 0 buffer");
        }
        this.length = bytes[0];
        this.type = bytes[1];
        this.sender = bytes[2];
        this.time = ByteBuffer.wrap(bytes).getInt(3);
        this.data = bytes;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getSender() {
        return sender;
    }

    public void setSender(byte sender) {
        this.sender = sender;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
