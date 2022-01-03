package filmparser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilmParser {
    private String path;
    private Film film;
    byte[] f;
    private static final int BUFFER_SIZE = 16384;
    private static final int PLUGINS_START_ADDR = 0x92;
    private static final int PLUGINS_NUMBER_ADDR = 0x8e;
    private static final int GAME_BUILD_ADDR = 0x86;
    private static final int MESH_TAG_ADDR = 0x70;

    public Film parseFilm(String filePath) {
        // Check if file path works
        // create film
        this.film = new Film();
        Path path = Paths.get(filePath);
        try {
//            System.out.println("Reading file");
            f =  Files.readAllBytes(path);
//            System.out.println("Parse film name");
            parseFilmName();
//            System.out.println("Parse mesh tag");
            parseMeshTag();
//            System.out.println("Parse game build");
            parseGameBuildNumber();
//            System.out.println("Parse plugins");
            parsePlugins();
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
        film.setBuild(parseToNumber(GAME_BUILD_ADDR));
    }
    private void parsePlugins() {
        int numPlugins = parseToNumber(PLUGINS_NUMBER_ADDR);
        System.out.println("Number of Plugins: " + numPlugins);
        Plugin[] plugins = new Plugin[numPlugins];
        int start = PLUGINS_START_ADDR;
        for (int i = 0; i < numPlugins; i++) {
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
            checksum[i] = f[start + i];
        }
        p.setChecksum(checksum);
        // plugin name
        p.setName(parseToString(start + 4));
        p.setUrl(parseToString(start + 4 + p.getName().length() + 1));
        return p;
    }

    private int parseToNumber(int start) {
        int a = Byte.toUnsignedInt(f[start]);
        int b = Byte.toUnsignedInt(f[start + 1]);
        int c = b | a << 8;
        return c;
    }
    private String parseToString(int start, int length) {
        // zero terminated with end index
        String str = "";
        for (int i = start; i < start + length; i++) {
            if (f[i] != 0) {
                char c = (char) f[i];
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
        while (f[i] != 0) {
            str += (char) f[i];
            i++;
        }
        return str;
    }


}
