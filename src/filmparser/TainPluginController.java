package filmparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class TainPluginController {
    public static final String TAIN_URI = "http://tain.totalcodex.net/plugins/find";

    public static String getUrl(String pluginName, String pluginUrl, long pluginChecksum) {
        String responseUrl = null;

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
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            // Do something with http.getInputStream()
            try(InputStream is = http.getInputStream()) {
                responseUrl = new String(is.readAllBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseUrl;
    }
}
