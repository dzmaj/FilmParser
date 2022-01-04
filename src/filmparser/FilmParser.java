package filmparser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

public class FilmParser {
    private String path;
    private Film film;
    byte[] file;
    private static final int BUFFER_SIZE = 16384;
    private static final int PLUGINS_START_ADDR = 0x92;
    private static final int PLUGINS_NUMBER_ADDR = 0x8e;
    private static final int GAME_BUILD_ADDR = 0x86;
    private static final int MESH_TAG_ADDR = 0x70;
    private static final int PLAYER_COUNT_ADDR = 0x290;

    public Film parseFilm(String filePath) {
        // Check if file path works
        // create film
        this.film = new Film();
        Path path = Paths.get(filePath);
        try {
//            System.out.println("Reading file");
            file =  Files.readAllBytes(path);
//            System.out.println("Parse film name");
            parseFilmName();
//            System.out.println("Parse mesh tag");
            parseMeshTag();
//            System.out.println("Parse game build");
            parseGameBuildNumber();
//            System.out.println("Parse plugins");
            parsePlugins();
            parsePlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return film;
    }
    private void parseFilmName() {
        film.setName(parseToString(4, 32));
    }
    private void parseMeshTag() {
        film.setMeshTag(parseToString(MESH_TAG_ADDR, 4));
    }
    private void parseGameBuildNumber() {
        // Game build number appears to be off by 8
        film.setBuild(parseToInt(GAME_BUILD_ADDR) + 8);
    }
    private void parsePlugins() {
        int numPlugins = parseToInt(PLUGINS_NUMBER_ADDR);
        System.out.println("Number of Plugins: " + numPlugins);
        Plugin[] plugins = new Plugin[numPlugins];
        int start = PLUGINS_START_ADDR;
        for (int i = 0; i < numPlugins; i++) {
            // Plugins are zero terminated instead of fixed length
            // So need to use length after reading each plugin to determine where to start next one
            plugins[i] = parsePlugin(start);
            start += plugins[i].getLength();
        }
        film.setPlugins(plugins);
    }
    private Plugin parsePlugin(int start) {
        // plugin consists of checksum length 4, zero terminated name, and then zero terminated url
        Plugin p = new Plugin();
        // plugin checksum
        byte[] checksum = new byte[4];
        for (int i = 0; i < 4; i++) {
            checksum[i] = file[start + i];
        }
        p.setChecksum(checksum);
        // plugin name
        p.setName(parseToString(start + 4));
        // plugin url
        p.setUrl(parseToString(start + 4 + p.getName().length() + 1));
        return p;
    }

    private void parsePlayers() {
        int numPlayers = parseToInt(PLAYER_COUNT_ADDR);
        film.setNumPlayers(numPlayers);
        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            // 12 Byte header, each player 124 bytes
            players[i] = parsePlayer(PLAYER_COUNT_ADDR + 12 + 124 * i);
        }
        film.setPlayers(players);
    }

    private Player parsePlayer(int start) {
        Player player = new Player();
        player.setIndex(file[start + 7]);
        player.setTeamIndex(file[start + 11]);
        player.setIcon(file[start + 33]);
        player.setRank(parseToChar(start + 34));
        player.setName(parseToString(start + 36, 32));
        player.setTeamName(parseToString(start + 68, 32));
        byte[] primaryColor = Arrays.copyOfRange(file, start + 100, start + 108);
        player.setPrimaryColor(primaryColor);
        byte[] secondaryColor = Arrays.copyOfRange(file, start + 108, start + 116);
        player.setSecondaryColor(secondaryColor);
        return player;
    }

    private char parseToChar(int start) {
        return ByteBuffer.wrap(file).getChar(start);
    }

    private int parseToInt(int start) {
        int a = Byte.toUnsignedInt(file[start]);
        int b = Byte.toUnsignedInt(file[start + 1]);
        int c = b | a << 8;
        return c;
    }
    private String parseToString(int start, int length) {
        // zero terminated with end index
        String str = "";
        for (int i = start; i < start + length; i++) {
            if (file[i] != 0) {
                char c = (char) file[i];
                str += c;
            } else {
                break;
            }
        }
        return str;
    }
    private String parseToString(int start) {
        // zero terminated
        String str = "";
        int i = start;
        while (file[i] != 0) {
            str += (char) file[i];
            i++;
        }
        return str;
    }


}
