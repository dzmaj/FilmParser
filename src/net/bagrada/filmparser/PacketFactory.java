package net.bagrada.filmparser;

import net.bagrada.filmparser.packets.*;

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
            packet = switch (type) {
                case 1 -> new StatePacket(packetBytes);
                case 2 -> new GeneralActionPacket(packetBytes);
                // Need the game build because of packet format change in 1.8.4 for movement
                case 3 -> new MovementPacket(packetBytes, FilmParser.parseToUInt(bytes, FilmParser.GAME_BUILD_ADDR));
                case 4 -> new AttackTargetPacket(packetBytes);
                case 5 -> new AttackGroundPacket(packetBytes);
                case 6 -> new PickUpObjectPacket(packetBytes);
                case 7 -> new RenamePacket(packetBytes);
                case 8 -> new RotateFormationPacket(packetBytes);
                case 10 -> new ChatPacket(packetBytes);
                case 11 -> new DetachUnitsPacket(packetBytes);
                case 12 -> new UnitTradePacket(packetBytes);
                case 16 -> new InitializePlayerPacket(packetBytes);
                default -> new GamePacket(packetBytes);
                // Create basic GamePacket
            };
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
