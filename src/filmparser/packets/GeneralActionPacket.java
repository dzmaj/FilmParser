package filmparser.packets;

import filmparser.FilmParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GeneralActionPacket extends GamePacket{
    private static final Map<Integer, String> COMMAND_TYPE_MAP = new HashMap<>();
    static {
        COMMAND_TYPE_MAP.put(0, "STOP");
        COMMAND_TYPE_MAP.put(1, "SCATTER");
        COMMAND_TYPE_MAP.put(2, "RETREAT");
        COMMAND_TYPE_MAP.put(3, "SPECIAL ABILITY");
        COMMAND_TYPE_MAP.put(4, "GUARD");
        COMMAND_TYPE_MAP.put(5, "TAUNT");
    }

    private int commandSubType;

    private int[] ids;

    public GeneralActionPacket(byte[] bytes) {
        super(bytes);
        byte[] data = super.getData();
        this.ids = new int[data[5]];
        this.commandSubType = data[3];
        // unit IDs start at data[6] for general actions, 2 bytes long
        for (int i = 0; i < this.ids.length; i++) {
            this.ids[i] = FilmParser.parseToUInt(data, 6 + 2 * i);
        }
    }

    public String getCommandTypeString() {
        return COMMAND_TYPE_MAP.get(this.commandSubType);
    }

    public int getCommandSubType() {
        return commandSubType;
    }

    public int[] getIds() {
        return Arrays.copyOf(ids, ids.length);
    }
}
