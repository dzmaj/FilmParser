package filmparser;

import filmparser.packets.ChatPacket;
import filmparser.packets.DetachUnitsPacket;
import filmparser.packets.GamePacket;
import filmparser.packets.StatePacket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class FilmParserTest {
    private static boolean logAllPackets;
    private static boolean logChat;
    private static boolean logMovement;
    private static boolean logGeneralAction;
    private static boolean logAttackTarget;
    private static boolean logState;
    private static boolean logOther;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Recording Parser: ");
        System.out.println("Log all packets? (y/n)");
        String option = sc.nextLine();
        switch (option) {
            case "y","yes","Y","YES" -> logAllPackets = true;
        }
        if (!logAllPackets) {
            System.out.println("Log chat packets? (y/n)");
            option = sc.nextLine();
            switch (option) {
                case "y","yes","Y","YES" -> logChat = true;
            }
            System.out.println("Log OOS check packets? (y/n)");
            option = sc.nextLine();
            switch (option) {
                case "y","yes","Y","YES" -> logState = true;
            }
            System.out.println("Log other packets? (y/n)");
            option = sc.nextLine();
            switch (option) {
                case "y","yes","Y","YES" -> logOther = true;
            }
//            System.out.println("Log movement packets? (1)");
//            option = sc.nextLine();
//            logChat = option.equals("1");
//            System.out.println("Log general action packets? (1)");
//            option = sc.nextLine();
//            logChat = option.equals("1");
        }
        System.out.println("Enter the directory to parse: ");
        String path = sc.nextLine();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            Arrays.stream(listOfFiles).parallel().filter(File::isFile).forEach(file -> {
                try {
                    parseFile(file.getPath());
                } catch (Exception e) {
                   e.printStackTrace();
                }
            });
//            for (File listOfFile : listOfFiles) {
//                if (listOfFile.isFile()) {
//                    try {
//                        parseFile(listOfFile.getPath());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
        }


    }
    public static void parseFile(String inputPath) throws IOException {


        FilmParser fp = new FilmParser();
        System.out.println("Starting:\t" + inputPath);
        Film film = fp.parseFilm(inputPath);
        if (film != null) {
            File file = new File(inputPath + ".txt");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            System.out.println("Writing :\t" + file.getPath());
            pw.println("Recording Name: " + film.getName());
            pw.println("Mesh Tag ID: " + film.getMeshTag());
            pw.println("Game Build: " + film.getBuild());
            pw.println("Difficulty: " + film.getDifficultyString());
            pw.println("Game Type: " + film.getGameTypeString());
            pw.printf("Time Limit: %d:%d:%02d\n", film.getTimeLimitDuration().toHours(),
                                                film.getTimeLimitDuration().toMinutesPart(),
                                                film.getTimeLimitDuration().toSecondsPart());
            pw.printf("Planning Time: %d:%02d\n", film.getPlanningTimeLimitDuration().toMinutes(),
                                                film.getPlanningTimeLimitDuration().toSecondsPart());
            for (Plugin plugin: film.getPlugins()) {
                pw.println("Plugin: " + plugin.getName());
                pw.println("URL: " + plugin.getUrl());
                pw.println("Checksum: 0x" + plugin.getChecksumString());
                String str = "";
                try {
                    str = TainPluginController.getUrl(plugin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pw.println(str);
            }
            pw.println("-----Player List-----");
            for (Player player : film.getPlayers()) {
                pw.println(player.getName());
            }
            pw.println("-----Packet Log-----");
            pw.println("Time : Type : Sender : Content");
            for (GamePacket packet : film.getPackets()) {
                boolean print;
                if (logAllPackets) {
                    print = true;
                } else if (packet instanceof ChatPacket && logChat) {
                    print = true;
                } else if (packet instanceof StatePacket && logState) {
                    print = true;
                } else if (logOther) {
                    print = true;
                } else {
                    print = false;
                }
                if (print) {
                    StringBuilder sb = new StringBuilder(128);
                    sb.append(packet);
                    Player sender = film.getPlayers()[((packet.getSender()))];
                    if (sender != null) {
                        String s = "FROM = [";
                        sb.insert(sb.indexOf(s) + s.length(), sender.getName());
                    }
                    if (packet instanceof DetachUnitsPacket) {
                        String s = "TO = [";
                        Player from = film.getPlayers()[((DetachUnitsPacket) packet).getDetachToPlayerIndex()];
                        sb.insert(sb.indexOf(s) + s.length(), from.getName());
                    }
                    pw.println(sb);
                }
            }
            System.out.println("Done writing:\t" + inputPath);
            pw.close();
        } else {
            System.out.println("Error:\t" + inputPath);
        }

    }
}
