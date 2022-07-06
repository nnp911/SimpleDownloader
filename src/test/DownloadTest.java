package test;

import main.SimpleDownloader;

public class DownloadTest {
    public static void main(String [] args) {
        SimpleDownloader d1 = new SimpleDownloader();
        d1.setDownloadFileUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4");
        String fileName = "out1.mp4";
        d1.setLocalFileFullPath(System.getProperty("user.home")+ "\\Downloads\\"+fileName);
        Thread t1 = new Thread(d1);
        t1.start();

        SimpleDownloader d2 = new SimpleDownloader();
        d2.setDownloadFileUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4");
        String fileName2 = "out2.mp4";
        d2.setLocalFileFullPath(System.getProperty("user.home")+ "\\Downloads\\"+fileName2);
        Thread t2 = new Thread(d2);
        t2.start();
    }

}
