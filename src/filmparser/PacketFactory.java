package filmparser;

import filmparser.packets.*;

import java.util.Arrays;

public class PacketFactory {
    public static GamePacket createPacket(byte[] bytes, int start) throws Exception {
        GamePacket packet;
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
                case 1: // state
                    packet = new StatePacket(packetBytes);
                    break;
                case 2: // general action
                    packet = new GeneralActionPacket(packetBytes);
                    break;
                case 3: // movement
                    packet = new MovementPacket(packetBytes, FilmParser.parseToUInt(bytes, FilmParser.GAME_BUILD_ADDR));
                    break;
                case 4: // attack unit
                    packet = new AttackTargetPacket(packetBytes);
                    break;
                case 5: // attack ground
                    packet = new AttackGroundPacket(packetBytes);
                    break;
                case 6: // pick up object
                    packet = new PickUpObjectPacket(packetBytes);
                    break;
                case 7: // rename units
                    packet = new RenamePacket(packetBytes);
                    break;
                case 8: // rotate formation
                    packet = new RotateFormationPacket(packetBytes);
                    break;
                case 10: // Chat packet
                    packet = new ChatPacket(packetBytes);
                    break;
                case 12: // Unit Trade
                    packet = new UnitTradePacket(packetBytes);
                    break;
                case 16: // Player info packet
                    packet = new InitializePlayerPacket(packetBytes);
                    break;
                case 9: // ?
                    //break;
                case 11: // Detach units?
                    //break;
                case 13: //?
                    //break;
                case 15: // ?
                    //break;
                case 17: // Inventory switch
                    //break;
                case 21: // Player drop packet?
                    //break;
                default: // Unidentified packets
//                    System.out.println("Unrecognized packet type: " + type + " at " + start);
                    packet = new GamePacket(packetBytes);
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
        packet.setLength(len);

        return packet;
    }
}
