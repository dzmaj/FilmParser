package net.bagrada.filmparser.packets;

import net.bagrada.filmparser.FilmParser;

public class DetachUnitsPacket extends GamePacket{
    private int detachToPlayerIndex;
    private int[] ids;
    public DetachUnitsPacket(byte[] bytes) {
        super(bytes);
        var data = super.getData();
        this.ids = new int[data[5]];
        for (int i = 0; i < ids.length; i++) {
            this.ids[i] = FilmParser.parseToUInt(data, 6 + 2 * i);
        }
        this.detachToPlayerIndex = FilmParser.parseToUInt(data, 2);
    }

    public int getDetachToPlayerIndex() {
        return detachToPlayerIndex;
    }

    public int[] getIds() {
        return ids;
    }

    @Override
    public String getDataString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("TO = [ : ").append(detachToPlayerIndex);
        sb.append("]\n\t").append(ids.length).append(" Units :");
        for (int id: ids) {
            sb.append(" [").append(id).append("]");
        }
        return sb.toString();
    }
}
