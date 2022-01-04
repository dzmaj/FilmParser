package filmparser;

import java.net.StandardSocketOptions;

public class FilmParserTest {
    public static void main(String[] args) {
        System.out.println("Recording Parser: ");
        String path = "testfilm.m2rec";
        FilmParser fp = new FilmParser();
        Film film = fp.parseFilm(path);
        System.out.println("Recording Name: " + film.getName());
        System.out.println("Mesh Tag ID: " + film.getMeshTag());
        System.out.println("Game Build: " + film.getBuild());
        for (Plugin plugin: film.getPlugins()) {
            System.out.println("Plugin: " + plugin.getName());
            System.out.println("URL: " + plugin.getUrl());
            System.out.println("Checksum: 0x" + plugin.getChecksumString());
//            System.out.println("          " + plugin.getChecksumValue());
            String str = TainPluginController.getUrl(plugin.getName(), plugin.getUrl(), plugin.getChecksumValue());
            System.out.println(str);
        }
        for (Player player : film.getPlayers()) {
            System.out.println("Player: " + player.getName());
        }


    }
}
