package filmparser;

import filmparser.packets.GamePacket;

import java.util.List;
import java.util.Map;

public class Film {
    private String name;
    private String meshTag;
    private int build;
    private int numPlugins;
    private Plugin[] plugins;
    private int numPlayers;
    private Player[] players;
    private List<GamePacket> packets;
    private Map<Integer, GamePacket> packetMap;

    public Map<Integer, GamePacket> getPacketMap() {
        return packetMap;
    }

    public void setPacketMap(Map<Integer, GamePacket> packetMap) {
        this.packetMap = packetMap;
    }

    public Film() {

    }

    public List<GamePacket> getPackets() {
        return packets;
    }

    public void setPackets(List<GamePacket> packets) {
        this.packets = packets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeshTag() {
        return meshTag;
    }

    public void setMeshTag(String meshTag) {
        this.meshTag = meshTag;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    public int getNumPlugins() {
        return numPlugins;
    }

    public void setNumPlugins(int numPlugins) {
        this.numPlugins = numPlugins;
    }

    public Plugin[] getPlugins() {
        return plugins;
    }

    public void setPlugins(Plugin[] plugins) {
        this.plugins = plugins;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
