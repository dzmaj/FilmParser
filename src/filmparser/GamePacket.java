package filmparser;

import java.nio.ByteBuffer;

public class GamePacket {
    private byte length;
    private byte type;
    private byte sender;
    private int time;

    public GamePacket() {
    }

    public GamePacket(byte[] bytes) {
        this.length = bytes[0];
        this.type = bytes[1];
        this.sender = bytes[3];
        this.time = ByteBuffer.wrap(bytes).getInt(4);
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
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
