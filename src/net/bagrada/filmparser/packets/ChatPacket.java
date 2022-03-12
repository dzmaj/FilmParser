package net.bagrada.filmparser.packets;

import net.bagrada.filmparser.FilmParser;

import java.nio.ByteBuffer;

public class ChatPacket extends GamePacket {
    private boolean whispered;
    private String message;

    public ChatPacket() {
    }

    public ChatPacket(byte[] bytes) {
        super(bytes);
        this.whispered = FilmParser.parseToUInt(bytes, 8) != 0;
        StringBuilder str = new StringBuilder();
        for (int i = 10; i < bytes.length - 1; i++) {
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

    @Override
    public String getDataString() {
        String str = whispered ? "WHISPER : " : "YELL : ";
        return str + message;
    }
}
