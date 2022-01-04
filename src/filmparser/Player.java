package filmparser;

public class Player {
    private String name;
    private String teamName;
    private byte[] primaryColor;
    private byte[] secondaryColor;
    private byte index;
    private byte teamIndex;
    private byte icon;
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
