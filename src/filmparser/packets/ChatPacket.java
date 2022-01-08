package filmparser.packets;

import filmparser.packets.GamePacket;

public class ChatPacket extends GamePacket {
    private boolean whispered;
    private String message;

    public ChatPacket() {
    }

    public ChatPacket(byte[] bytes) {
        super(bytes);
        this.whispered = bytes[8] != 0;
        StringBuilder str = new StringBuilder();
        for (int i = 9; i < bytes.length - 2; i++) {
            str.append((char) bytes[i]);
        }
        this.message = str.toString();
    }

    public boolean isWhispered() {
        return whispered;
    }

    public void setWhispered(boolean whispered) {
        this.whispered = whispered;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
