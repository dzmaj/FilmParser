package filmparser;

public class Player {
    private String name;
    private String teamName;
    private byte[] primaryColor;
    private byte[] secondaryColor;
    private byte index; // not exactly the index actually
    private byte teamIndex; // not the team index
    private byte icon;

    public byte getIndex() {
        return index;
    }

    public void setIndex(byte index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", teamName='" + teamName + '\'' +
                ", index=" + index +
                ", teamIndex=" + teamIndex +
                '}';
    }

    public byte getTeamIndex() {
        return teamIndex;
    }

    public void setTeamIndex(byte teamIndex) {
        this.teamIndex = teamIndex;
    }

    public byte getIcon() {
        return icon;
    }

    public void setIcon(byte icon) {
        this.icon = icon;
    }

    public char getRank() {
        return rank;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }

    private char rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public byte[] getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(byte[] primaryColor) {
        this.primaryColor = primaryColor;
    }

    public byte[] getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(byte[] secondaryColor) {
        this.secondaryColor = secondaryColor;
    }
}
