package filmparser.packets;

import filmparser.Film;
import filmparser.FilmParser;

import java.util.Arrays;

public class AttackTargetPacket extends GamePacket{

    private int commandSubType;

    private int[] attackerIds;
    private int[] targetIds;

    public AttackTargetPacket(byte[] bytes) {
        super(bytes);
        byte[] data = super.getData();
        this.attackerIds = new int[FilmParser.parseToUInt(data, 2)];
        this.commandSubType = data[FilmParser.parseToUInt(data, 0)];
        for (int i = 0; i < this.attackerIds.length; i++) {
            this.attackerIds[i] = FilmParser.parseToUInt(data, 6 + 2 * i);
        }
        int numTargetsAddr = 3 + 2 * attackerIds.length;
        this.targetIds = new int[FilmParser.parseToUInt(data, numTargetsAddr)];
        for (int i = 0; i < this.targetIds.length; i++) {
            this.targetIds[i] = FilmParser.parseToUInt(data, numTargetsAddr + 2 * i);
        }
    }

    public int[] getTargetIds() {
        return targetIds;
    }

    public int getCommandSubType() {
        return commandSubType;
    }

    public int[] getAttackerIds() {
        return Arrays.copyOf(attackerIds, attackerIds.length);
    }


}