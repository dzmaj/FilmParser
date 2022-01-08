package filmparser;

import filmparser.packets.ChatPacket;
import filmparser.packets.GamePacket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FilmParserTest {
    private static boolean logAllPackets;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Recording Parser: ");
        System.out.println("Log only chat packets (1) or all packets (2)?");
        String option = sc.nextLine();
        logAllPackets = option.equals("2");
        System.out.println("Enter the directory to parse: ");
        String path = sc.nextLine();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    try {
                        parseFile(listOfFile.getPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }
    public static void parseFile(String inputPath) throws IOException {


        FilmParser fp = new FilmParser();
        System.out.println("Parsing " + inputPath);
        Film film = fp.parseFilm(inputPath);
        if (film != null) {
            File file = new File(inputPath + ".txt");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            System.out.println("Writing " + file.getPath());
            pw.println("Recording Name: " + film.getName());
            pw.println("Mesh Tag ID: " + film.getMeshTag());
            pw.println("Game Build: " + film.getBuild());
            for (Plugin plugin: film.getPlugins()) {
                pw.println("Plugin: " + plugin.getName());
                pw.println("URL: " + plugin.getUrl());
                pw.println("Checksum: 0x" + plugin.getChecksumString());
                String str = "";
                try {
                    str = TainPluginController.getUrl(plugin.getName(), plugin.getUrl(), plugin.getChecksumValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pw.println(str);
            }
            pw.println("-----Player List-----");
            for (Player player : film.getPlayers()) {
                pw.println(player.getName());
            }
            pw.println("-----Chat Log-----");
            pw.println("Time : Type : Sender : Content");
            for (GamePacket packet : film.getPackets()) {
                String str = packet.getTime() + "(" + (packet.getTime() / 30 / 60) + ":" + ((int) (((packet.getTime() / 30.0) % 60.0) * 100))/100.0 + ") : ";

                str += packet.getType() + " : ";
                if (packet != null) {
                    Player sender = film.getPlayers()[((packet.getSender()))];
                    if (sender != null) {
                        str += sender.getName();
                    } else {
                        str += "[null]";
                    }
                    if (packet instanceof ChatPacket) {
                        str += ((ChatPacket) packet).isWhispered() ? " (Whisper) : " : " (Yell) : ";
                        str += ((ChatPacket) packet).getMessage();
                        pw.println(str);
                    } else {
                        str += " : Type " + packet.getType() + " : ";
                        str += FilmParser.getByteString(packet.getData(), 7, packet.getLength());
                        if (logAllPackets) {
                            pw.println(str);
                        }
                    }

                } else {
                    pw.println("packet error");
                }
            }
            System.out.println("\tDone writing\n");
            pw.close();
        } else {
            System.out.println("Error parsing " + inputPath);
        }

    }
}
