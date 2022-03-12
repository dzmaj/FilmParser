package net.bagrada.filmparser;

import net.bagrada.filmparser.packets.GamePacket;

import java.time.Duration;
import java.util.HashMap;
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
    private int gameType;
    private int timeLimit;
    private int planningTimeLimit;
    private int difficulty;

    private static final Map<Integer, String> GAME_TYPE_MAP = new HashMap<>();
    static {
        GAME_TYPE_MAP.put(0, "BODY COUNT");
        GAME_TYPE_MAP.put(1, "STEAL THE BACON");
        GAME_TYPE_MAP.put(2, "LAST MAN ON THE HILL");
        GAME_TYPE_MAP.put(3, "SCAVENGER HUNT");
        GAME_TYPE_MAP.put(4, "FLAG RALLY");
        GAME_TYPE_MAP.put(5, "CAPTURE THE FLAG");
        GAME_TYPE_MAP.put(6, "BALLS ON PARADE");
        GAME_TYPE_MAP.put(7, "TERRITORIES");
        GAME_TYPE_MAP.put(8, "CAPTURES");
        GAME_TYPE_MAP.put(9, "KING OF THE HILL");
        GAME_TYPE_MAP.put(10, "STAMPEDE");
        GAME_TYPE_MAP.put(11, "ASSASSIN");
        GAME_TYPE_MAP.put(12, "HUNTING");
        GAME_TYPE_MAP.put(13, "CUSTOM");
        GAME_TYPE_MAP.put(14, "KING OF THE HILL (TFL)");
        GAME_TYPE_MAP.put(15, "KING OF THE MAP");
    }
    private static final Map<Integer, String> DIFFICULTY_NAME_MAP = new HashMap<>();
    static {
        DIFFICULTY_NAME_MAP.put(0, "TIMID");
        DIFFICULTY_NAME_MAP.put(1, "SIMPLE");
        DIFFICULTY_NAME_MAP.put(2, "NORMAL");
        DIFFICULTY_NAME_MAP.put(3, "HEROIC");
        DIFFICULTY_NAME_MAP.put(4, "LEGENDARY");
    }

    public String getDifficultyString() {
        return DIFFICULTY_NAME_MAP.getOrDefault(difficulty, "UNKNOWN");
    }

    public Duration getTimeLimitDuration() {
        return Duration.ofMillis((long)timeLimit / 30 * 1000);
    }

    public Duration getPlanningTimeLimitDuration() {
        return Duration.ofMillis((long)planningTimeLimit / 30 * 1000);
    }

    public String getGameTypeString() {
        return GAME_TYPE_MAP.getOrDefault(gameType, "UNKNOWN");
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getPlanningTimeLimit() {
        return planningTimeLimit;
    }

    public void setPlanningTimeLimit(int planningTimeLimit) {
        this.planningTimeLimit = planningTimeLimit;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

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
