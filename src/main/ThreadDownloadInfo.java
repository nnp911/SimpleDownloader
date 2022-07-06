package main;

//Display download info
public class ThreadDownloadInfo implements Runnable {
    private static final double MB = 1024d * 1024d;
    private long httpFileLength;
    public double finishedSize;
    public double prevSize;
    public volatile double downsize;

    public ThreadDownloadInfo(long httpFileLength) {
        this.httpFileLength = httpFileLength;
    }

    @Override
    public void run() {
        String httpFileSize = String.format("%.2f", httpFileLength / MB);
        int speed = (int) ((downsize - prevSize) / 1024d);//download speed in kb
        prevSize = downsize;
        double remainSize = httpFileLength - finishedSize - downsize;//remaining file size
        String remainTime = String.format("%.1f", remainSize / 1024d / speed);//remaining time
        if ("Infinity".equalsIgnoreCase(remainTime)) {
            remainTime = "-";
        }
        String currFileSize = String.format("%.2f", (downsize - finishedSize) / MB);//current downloaded size
        String downInfo = String.format("Downloaded %smb/%smb, speed %skb/s, time remain %ss"
                , currFileSize, httpFileSize, speed, remainTime);
        System.out.print("\r");
        System.out.print(downInfo);
    }
}
