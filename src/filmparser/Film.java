package filmparser;

public class Film {
    private String name;
    private String meshTag;
    private int build;
    private int numPlugins;
    private Plugin[] plugins;
    private int numPlayers;
    private Player[] players;

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
