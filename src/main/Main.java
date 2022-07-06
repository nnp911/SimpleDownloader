package main;

import main.Util.LogUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = null;
        if (args == null || args.length == 0) {
            while (true) {
                LogUtil.info("Enter the url to download:");
                Scanner scanner = new Scanner(System.in);
                url = scanner.next();
                if (url != null) {
                    break;
                }
            }
        } else {
            url = args[0];
        }

        SimpleDownloader downloader = new SimpleDownloader();
        downloader.download(url);
    }
}
