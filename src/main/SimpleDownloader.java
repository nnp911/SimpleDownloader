package main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class SimpleDownloader implements Runnable{
    private static BufferedOutputStream outStream;
    private static InputStream inputStream;
    private static String downloadFileUrl;
    private static URLConnection urlConnection;
    private static int fileSize;
    private static String localFileFullPath;
    private static final int bufferSize = 4096;


    @Override
    public void run() {
        try {
            URL url;
            byte[] buf;
            int byteRead, byteWritten = 0;
            url = new URL(getFinalLocation(downloadFileUrl));
            outStream = new BufferedOutputStream(new FileOutputStream(localFileFullPath));

            urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();
            fileSize = urlConnection.getContentLength();

            buf = new byte[bufferSize];
            while ((byteRead = inputStream.read(buf)) != -1) {
                outStream.write(buf, 0, byteRead);
                byteWritten += byteRead;
                //System.out.println((byteWritten/fileSize * 100.0) + "%");
                System.out.println(byteWritten);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //To get the final location of the file if the server redirect
    public synchronized String getFinalLocation(String address) throws IOException {
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int status = conn.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER) {
                String newLocation = conn.getHeaderField("Location");
                return getFinalLocation(newLocation);
            }
        }
        return address;
    }

    public void setLocalFileFullPath(String localFileName) {
        SimpleDownloader.localFileFullPath = localFileName;
    }

    public void setDownloadFileUrl(String fileAddress) {
        SimpleDownloader.downloadFileUrl = fileAddress;
    }
}
