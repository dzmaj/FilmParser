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
        this.commandSubType = FilmParser.parseToUInt(data, 0);
        for (int i = 0; i < this.attackerIds.length; i++) {
            this.attackerIds[i] = FilmParser.parseToUInt(data, 4 + 2 * i);
        }
        int numTargetsAddr = 4 + 2 * attackerIds.length;
        this.targetIds = new int[FilmParser.parseToUInt(data, numTargetsAddr)];
        for (int i = 0; i < this.targetIds.length; i++) {
            this.targetIds[i] = FilmParser.parseToUInt(data, numTargetsAddr + 2 + 2 * i);
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

    @Override
    public String getDataString() {
        StringBuilder sb = new StringBuilder();
        sb.append("COMMAND SUBTYPE=").append(getCommandSubType()).append(" : ");
        sb.append("\n\t").append(attackerIds.length).append(" ATTACKERS :");
        for (int id: attackerIds) {
            sb.append(" [").append(id).append("]");
        }
        sb.append("\n\t").append(targetIds.length).append(" TARGETS :");
        for (int id: targetIds) {
            sb.append(" [").append(id).append("]");
        }
        return sb.toString();
    }
}
