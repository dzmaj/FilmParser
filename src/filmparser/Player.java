package filmparser;

public class Player {
    private String name;
    private String teamName;
    private int[] primaryColor;
    private int[] secondaryColor;

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

    public int[] getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(int[] primaryColor) {
        this.primaryColor = primaryColor;
    }

    public int[] getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(int[] secondaryColor) {
        this.secondaryColor = secondaryColor;
    }
}
