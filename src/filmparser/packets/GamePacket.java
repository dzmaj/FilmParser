package filmparser.packets;

import filmparser.FilmParser;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Arrays;

public class GamePacket {
    private int length;
    private byte type;
    private byte sender;
    private int tic;
    private Duration time;
    private byte[] data;

    public GamePacket() {
    }

    public GamePacket(byte[] bytes) {
        this.length = FilmParser.parseToUInt(bytes);
        this.type = bytes[2];
        this.sender = bytes[3];
        this.tic = ByteBuffer.wrap(bytes).getInt(4);
        this.time = Duration.ofMillis((long)tic * 1000 / 30);
        this.data = Arrays.copyOfRange(bytes, 8, length);
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

    public int getTic() {
        return tic;
    }

    public void setTic(int tic) {
        this.tic = tic;
    }

    public String getDataString() {
        return FilmParser.getByteString(data);
    }
    public String getTypeString() {
        return switch (type) {
            case 1 -> "OOS CHECK";
            case 2 -> "GENERAL ACTION";
            case 3 -> "MOVE";
            case 4 -> "ATTACK TARGETS";
            case 5 -> "ATTACK GROUND";
            case 6 -> "PICK UP OBJECT";
            case 7 -> "RENAME";
            case 8 -> "ROTATE FORMATION";
            case 10 -> "CHAT";
            case 11 -> "DETACH UNIT?";
            case 12 -> "UNIT TRADE";
            case 16 -> "INITIALIZE PLAYER";
            case 17 -> "INVENTORY";
            case 21 -> "PLAYER DROP";
            default -> "UNKNOWN (" + type + ")";
        };
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(tic);
        sb.append(" (").append(String.format("%02d:%02d.%03d",
                time.toMinutes(), time.toSecondsPart(), time.toMillisPart())).append(") : ");
//        sb.append("length=").append(length);
        sb.append(getTypeString()).append(" : ");
        sb.append("FROM = [ : ").append(sender);
        sb.append("] : ");
        sb.append(getDataString());
        return sb.toString();
    }
}
