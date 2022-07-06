package main;

import main.Util.FileUtil;
import main.Util.HttpUtil;
import main.Util.LogUtil;


import java.io.*;
import java.net.HttpURLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleDownloader {
    public static final int BYTE_SIZE = 1024 * 100;
    public ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void download(String url) {
        String httpFileName = HttpUtil.getHttpFileName(url);
        String localPath = System.getProperty("user.home") + "\\Downloads\\" + httpFileName;
        httpFileName = localPath; //full local file path

        //get local downloaded file size
        long localFileLength = FileUtil.getFileLength(httpFileName);

        HttpURLConnection httpURLConnection = null;
        ThreadDownloadInfo threadDownloadInfo = null;
        try {
            httpURLConnection = HttpUtil.getHttpURLConnection(url);
            int contentLength = httpURLConnection.getContentLength();

            //check if file is already downloaded
            if (localFileLength >= contentLength) {
                LogUtil.info("[] is already downloaded", httpFileName);
                return; //exit
            }
            threadDownloadInfo = new ThreadDownloadInfo(contentLength);
            scheduledExecutorService.scheduleAtFixedRate(threadDownloadInfo, 1, 1, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream input = httpURLConnection.getInputStream();
             BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
             FileOutputStream fileOutputStream = new FileOutputStream(httpFileName);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ) {
            int len = -1;
            byte[] buffer = new byte[BYTE_SIZE];
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                threadDownloadInfo.downsize += len;
                bufferedOutputStream.write(buffer,0,len);
            }
        } catch (FileNotFoundException e) {
            LogUtil.error("File is not found[]", url);
        } catch (Exception e) {
            LogUtil.error("Download failed");
        } finally {
            System.out.print("\r");
            System.out.println("Download complete");
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            scheduledExecutorService.shutdownNow();
        }
    }

}
