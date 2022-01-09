package filmparser;

import filmparser.packets.AttackTargetPacket;
import filmparser.packets.ChatPacket;
import filmparser.packets.GamePacket;
import filmparser.packets.GeneralActionPacket;

import java.util.Arrays;

public class PacketFactory {
    public static GamePacket createPacket(byte[] bytes, int start) throws Exception {
        GamePacket packet = null;
        int len = FilmParser.parseToUInt(bytes, start);
        if (len <= 0) {
            throw new Exception("Error reading packet at " + start + " length is " + len);
        }
        int end = start + len;
//        int end = start + bytes[start];
        byte[] packetBytes = Arrays.copyOfRange(bytes, start, end);
        byte type = packetBytes[2];
        try {
            switch (type) {
                case 10: // Chat packet
                    packet = new ChatPacket(packetBytes);
                    break;
//                case 0: // Something went wrong
//                    throw new Exception("Bad packet type: " + type);
                case 2: // general action
                    packet = new GeneralActionPacket(packetBytes);
                    break;
                case 4: // attack unit
                    packet = new AttackTargetPacket(packetBytes);
                    break;
                case 1: // state
                    //break;
                case 3: // movement
                    //break;
                case 5: // attack ground
                    //break;
                case 6: // pick up object
                    //break;
                case 7: // rename units
                    //break;
                case 8: // rotate formation
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
                case 21: // Player drop packet?
                    //break;
                    packet = new GamePacket(packetBytes);
                    break;

                default: // Unidentified packets or something went wrong
//                    System.err.println("Throwing out packet type: " + type);
                    System.out.println("Unrecognized packet type: " + type + " at " + start);
//                    System.exit(2);
//                    throw new Exception("Unrecognized packet type: " + type + " at " + start);
                    // keep skipping
                    // Create basic GamePacket

            }
        }
        catch (IllegalArgumentException e) {
            throw new Exception("Exception in PacketFactory.createPacket: start=" + start + ", length=" + bytes[start] + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Exception in PacketFactory.createPacket: start=" + start + ", length=" + bytes[start]);
        }
        if (packet != null) {
            packet.setLength(len);
        }

        return packet;
    }
}
