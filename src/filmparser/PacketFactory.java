package filmparser;

import java.util.Arrays;

public class PacketFactory {
    public static GamePacket createPacket(byte[] bytes, int start) {
        GamePacket packet = null;
        byte[] packetBytes = Arrays.copyOfRange(bytes, bytes[start], start + bytes[start]);
        byte type = bytes[start + 1];
        switch (type) {
            case 0x0a: // Chat packet
                packet = new ChatPacket(packetBytes);
                break;
            case 0x01: // State? packet
                //break;
            case 0x03: // Movement packet
                //break;
            case 0x15: // Player drop packet?
                //break;
            case 0x10: // Player info packet
                //break;
            default: // Unidentified packets
                // Create basic GamePacket
                packet = new GamePacket(packetBytes);
                break;
        }

        return packet;
    }
}
