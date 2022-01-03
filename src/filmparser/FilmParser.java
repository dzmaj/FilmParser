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

    public Film parseFilm(String filePath) {
        // Check if file path works
        // create film
        this.film = new Film();
        Path path = Paths.get(filePath);
        try {
            f =  Files.readAllBytes(path);
            parseFilmName();
            parseMeshTag();
            parseGameBuildNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return film;
    }
    private void parseFilmName() {
        film.setName(parseToString(0x4, 0x24));
    }
    private void parseMeshTag() {
        film.setMeshTag(parseToString(0x70, 0x74));
    }
    private void parseGameBuildNumber() {
        int a = Byte.toUnsignedInt(f[0x86]);
        int b = Byte.toUnsignedInt(f[0x87]);
        int c = b | a << 8;
//        System.out.println(Integer.toHexString(a));
//        System.out.println(Integer.toHexString(b));
//        System.out.println(Integer.toHexString(c));
        int num = c;
        film.setBuild(num);
    }
    private String parseToString(int start, int end) {
        // zero terminated
        String str = "";
        for (int i = start; i < end; i++) {
            if (f[i] != 0) {
                char c = (char) f[i];
                str += c;
            } else {
                break;
            }
        }
        return str;
    }



}
