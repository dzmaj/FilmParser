package filmparser;

import java.util.Arrays;

public class PacketFactory {
    public static GamePacket createPacket(byte[] bytes, int start) throws Exception {
        if (start == 88679) {
            System.err.println("---------");
        }
        GamePacket packet = null;
        int len = bytes[start];
        if (len < 0) {
//            System.err.println("Error reading packet at " + start + " length is " + len);
            len = (bytes[start] & 0xff);
//            System.err.println("new len " + len);
//            System.err.println(FilmParser.getByteString(bytes, start, start + len));
//            System.exit(1);
        } else if (len == 0) {
            throw new Exception("Error reading packet at " + start + " length is 0");
        }
        int end = start + len;
//        int end = start + bytes[start];
        byte[] packetBytes = Arrays.copyOfRange(bytes, start, end);
        byte type = packetBytes[1];
        try {
            switch (type) {
                case 10: // Chat packet
                    packet = new ChatPacket(packetBytes);
                    break;
                case 0: // Something went wrong
                    throw new Exception("Bad packet type: " + type);
                case 1: // State? packet
                    //break;
                case 2: // ?
                    //break;
                case 3: // Movement packet
                    //break;
                case 4: // ?
                    //break;
                case 5: // Attack ground
                    //break;
                case 6: // ?
                    //break;
                case 7: // ?
                    //break;
                case 8: // ?
                    //break;
                case 9: // ?
                    //break;
                case 11: //?
                    //break;
                case 12: // Unit Trade
                    //break;
                case 13: // ?
                    //break;
                case 15: // ?
                    //break;
                case 16: // Player info packet
                    //break;
                case 0x15: // Player drop packet?
                    //break;
                    packet = new GamePacket(packetBytes);
                    break;

                default: // Unidentified packets or something went wrong
//                    System.err.println("Throwing out packet type: " + type);
                    throw new Exception("Unrecognized packet type: " + type);
                    // keep skipping
                    // Create basic GamePacket

            }
        }
        catch (IllegalArgumentException e) {
            throw new Exception("Exception in PacketFactory.createPacket: start=" + start + ", length=" + bytes[start] + e.getMessage());
        }
        catch (Exception e) {
//            e.printStackTrace();
            throw new Exception("Exception in PacketFactory.createPacket: start=" + start + ", length=" + bytes[start]);
        }
        packet.setLength(len);

        return packet;
    }
}
