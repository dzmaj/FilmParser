package filmparser.packets;

import filmparser.FilmParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GeneralActionPacket extends GamePacket{
    private static final Map<Byte, String> COMMAND_TYPE_MAP = new HashMap<>();
    static {
        COMMAND_TYPE_MAP.put((byte) 0, "STOP");
        COMMAND_TYPE_MAP.put((byte) 1, "SCATTER");
        COMMAND_TYPE_MAP.put((byte) 2, "RETREAT");
        COMMAND_TYPE_MAP.put((byte) 3, "SPECIAL ABILITY");
        COMMAND_TYPE_MAP.put((byte) 4, "GUARD");
        COMMAND_TYPE_MAP.put((byte) 5, "TAUNT");
    }

    private byte commandType;

    private int[] ids;

    public GeneralActionPacket(byte[] bytes) {
        super(bytes);
        byte[] data = super.getData();
        this.ids = new int[data[5]];
        this.commandType = data[3];
        // unit IDs start at data[6] for general actions
        for (int i = 6; i < this.ids.length; i++) {
            int a = Byte.toUnsignedInt(data[i]);
            int b = Byte.toUnsignedInt(data[i + 1]);
            this.ids[i] = b | a << 8;
        }
    }

    public String getCommandTypeString() {
        return COMMAND_TYPE_MAP.get(this.commandType);
    }

    public byte getCommandType() {
        return commandType;
    }

    public int[] getIds() {
        return Arrays.copyOf(ids, ids.length);
    }
}
