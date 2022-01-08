package filmparser;

import filmparser.packets.GamePacket;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FilmParser {
    private Film film;
    byte[] file;
    private static final int PLUGINS_START_ADDR = 0x92;
    private static final int PLUGINS_NUMBER_ADDR = 0x8e;
    private static final int GAME_BUILD_ADDR = 0x86;
    private static final int MESH_TAG_ADDR = 0x70;
    private static final int PLAYER_COUNT_ADDR = 0x290;
    private static final int UNITS_LENGTH_ADDR = 0x5a;
    private static final int UNITS_START_ADDR = 0x0a6f;

    public Film parseFilm(String filePath) {
        // Check if file path works
        // create film
        this.film = new Film();
        Path path = Paths.get(filePath);
        try {
            System.out.println("\tReading file");
            file =  Files.readAllBytes(path);
            System.out.println("\tChecking file type");
            if(!checkFileIsRecordingType()) {
                throw new Exception("Not m2rec file");
            }
            System.out.println("\tParse film name");
            parseFilmName();
            System.out.println("\tParse mesh tag");
            parseMeshTag();
            System.out.println("\tParse game build");
            parseGameBuildNumber();
            System.out.println("\tParse plugins");
            parsePlugins();
            System.out.println("\tParse players");
            parsePlayers();
            System.out.println("\tParse packets");
            parsePackets();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
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
        // Game build number appears to be off sometimes
        film.setBuild(parseToInt(GAME_BUILD_ADDR));
    }
    private void parsePlugins() {
        int numPlugins = parseToInt(PLUGINS_NUMBER_ADDR);
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
        System.arraycopy(file, start, checksum, 0, 4);
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
//        Map<Integer, Player> playerMap = new HashMap<>();
        for (int i = 0; i < numPlayers; i++) {
            // 12 Byte header, each player 124 bytes
            players[i] = parsePlayer(PLAYER_COUNT_ADDR + 12 + 124 * i);
//            playerMap.put((int) players[i].getIndex(), players[i]);
        }
        film.setPlayers(players);
//        film.setPlayerMap(playerMap);
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

    private void parsePackets() {
        int start = UNITS_START_ADDR + ByteBuffer.wrap(file).getChar(UNITS_LENGTH_ADDR) + 2;
        List<GamePacket> packets = new ArrayList<>();
        Map<Integer, GamePacket> packetMap = new TreeMap<>();
        while (start < file.length) {
            GamePacket packet = null;
            try {
                packet = PacketFactory.createPacket(file, start);
                packetMap.put(start, packet);
                if (packet != null) {
                    packets.add(packet);
                    start += packet.getLength();
                } else {
                    start++;
                }
            } catch (Exception e) {
                start++; // Not sure the best way to recover from these errors
            }
        }
        film.setPackets(packets);
    }

    private char parseToChar(int start) {
        return ByteBuffer.wrap(file).getChar(start);
    }

    private int parseToInt(int start) {
        int a = Byte.toUnsignedInt(file[start]);
        int b = Byte.toUnsignedInt(file[start + 1]);
        return b | a << 8;
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
        StringBuilder str = new StringBuilder();
        int i = start;
        while (file[i] != 0) {
            str.append((char) file[i]);
            i++;
        }
        return str.toString();
    }
    public static String getByteString(byte[] bytes, int start, int end) {
        StringBuilder str = new StringBuilder();
        for (int i = start; i < end; i++) {
            str.append(bytes[i]).append(" ");
        }
        return str.toString();
    }
    public static String getByteString(byte[] bytes) {
        return getByteString(bytes, 0, bytes.length);
    }
    private boolean checkFileIsRecordingType() {
        return parseToString(36, 4).equals("reco");
    }


}
