package net.bagrada.filmparser.packets;

import net.bagrada.filmparser.Coordinate;
import net.bagrada.filmparser.FilmParser;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MovementPacket extends GamePacket{

    private static final Map<Integer, String> FORMATION_TYPE_MAP = new HashMap<>();
    static {
        FORMATION_TYPE_MAP.put(0, "SHORT LINE");
        FORMATION_TYPE_MAP.put(1, "LONG LINE");
        FORMATION_TYPE_MAP.put(2, "LOOSE LINE");
        FORMATION_TYPE_MAP.put(3, "STAGGERED LINE");
        FORMATION_TYPE_MAP.put(4, "BOX");
        FORMATION_TYPE_MAP.put(5, "RABBLE");
        FORMATION_TYPE_MAP.put(6, "SHALLOW ENCIRCLEMENT");
        FORMATION_TYPE_MAP.put(7, "DEEP ENCIRCLEMENT");
        FORMATION_TYPE_MAP.put(8, "VANGUARD");
        FORMATION_TYPE_MAP.put(9, "CIRCLE");
    }
    private int commandSubType;
    private int formation;
    private int subpacketLength; // Different for 1.8.4 on
    private int numberOfWaypoints;
    private Coordinate[] destinations;
    private Coordinate startPoint;
    private long rotation;
    private double rotationStandard;

    private int[] ids;

    public MovementPacket(byte[] bytes, int gameVersion) {
        super(bytes);
        byte[] data = super.getData();
        this.commandSubType = FilmParser.parseToUInt(data, 0);
        this.formation = FilmParser.parseToUInt(data, 2);
        this.rotation = ByteBuffer.wrap(data).getInt(4) & 0xffffffffL;
        this.rotationStandard = this.rotation / 11930464.71;
        this.ids = new int[FilmParser.parseToUInt(data, 8)];
        for (int i = 0; i < this.ids.length; i++) {
            this.ids[i] = FilmParser.parseToUInt(data, 10 + 2 * i);
        }
        int waypointAddr = 10 + ids.length * 2;
        this.numberOfWaypoints = FilmParser.parseToUInt(data, waypointAddr);
        if (numberOfWaypoints > 0) {
            destinations = new Coordinate[numberOfWaypoints];
        }
        for (int i = 0; i < this.numberOfWaypoints; i++) {
            int y = ByteBuffer.wrap(data).getInt(waypointAddr + 2 + i * 8);
            int x = ByteBuffer.wrap(data).getInt(waypointAddr + 2 + i * 8 + 4);
            destinations[i] = new Coordinate(x, y);
        }
        if (numberOfWaypoints > 0 && gameVersion >= 456) {
            int y = ByteBuffer.wrap(data).getInt(data.length - 8);
            int x = ByteBuffer.wrap(data).getInt(data.length - 4);
            startPoint = new Coordinate(x, y);
        } else {
            startPoint = null;
        }
    }


    public int getCommandSubType() {
        return commandSubType;
    }

    public int[] getIds() {
        return Arrays.copyOf(ids, ids.length);
    }

    @Override
    public String getDataString() {
        StringBuilder sb = new StringBuilder();
        sb.append("COMMAND SUBTYPE = ").append(getCommandSubType()).append(" : ");
        sb.append("FORMATION = ").append(FORMATION_TYPE_MAP.get(formation));
//        if (rotation != 0) {
            sb.append("\n\tFACING = ").append(String.format("%.1f", rotationStandard));
//        }
        sb.append("\n\t").append(ids.length).append(" UNITS =");
        for (int id: ids) {
            sb.append(" [").append(id).append("]");
        }
        sb.append("\n\t").append(numberOfWaypoints).append(" WAYPOINTS =");
        if (destinations != null) {
            for (Coordinate coordinate : destinations) {
                sb.append(" ").append(coordinate);
            }
        }
        if (startPoint !=  null) {
            sb.append("\n\tSTART POINT = ").append(startPoint);
        }
        return sb.toString();
    }
}
