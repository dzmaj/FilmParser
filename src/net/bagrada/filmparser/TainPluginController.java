package net.bagrada.filmparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

public class TainPluginController {
    public static final String TAIN_URI = "http://tain.totalcodex.net/plugins/find";

    // For now storing this in a static map, storing in a database would be good in the future
    // Should reduce number of requests to tain api in large collections of films
    private static final ConcurrentMap<Plugin, String> PLUGIN_STRING_MAP = new ConcurrentHashMap<>();

    public static String getUrl(Plugin plugin) {
        // First check the plugin map to see if we have already retrieved the url previously
        String responseUrl = PLUGIN_STRING_MAP.get(plugin);
        if (responseUrl != null) {
            return responseUrl;
        }
        // If not, continue on and make a request to the tain
        String pluginName = plugin.getName();
        String pluginUrl = plugin.getUrl();
        long pluginChecksum = plugin.getChecksumValue();
        try {
            URL url = new URL(TAIN_URI);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            Map<String,String> arguments = new HashMap<>();
            arguments.put("plugin_name", pluginName);
            arguments.put("plugin_url", pluginUrl);
            arguments.put("plugin_checksum", "" + pluginChecksum);
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            // Do something with http.getInputStream()
            if (http.getResponseCode() == 200) {
                try(InputStream is = http.getInputStream()) {
                    responseUrl = new String(is.readAllBytes());
                    PLUGIN_STRING_MAP.putIfAbsent(plugin, responseUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseUrl;
    }
}
