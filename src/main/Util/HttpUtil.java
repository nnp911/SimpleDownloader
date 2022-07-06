package main.Util;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
    public static HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL httpsUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpsUrl.openConnection();
        //simulate a browser download
        httpURLConnection.setRequestProperty(
                "User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        return httpURLConnection;
    }

    /*
     * Get file name /name.type
     */
    public static String getHttpFileName(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index + 1);
    }
}
