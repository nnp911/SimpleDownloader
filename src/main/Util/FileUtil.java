package main.Util;

import java.io.File;

public class FileUtil {
    public static long getFileLength(String path){
        File file = new File(path);
        return file.exists() && file.isFile() ? file.length() : 0;
    }
}
