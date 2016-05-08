package org.cgw.splat.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 通过正则表达是匹配文件。
 * 
 * @author guowei.cgw 2015年6月5日 下午2:08:22
 */
public class NameMatchingFilePatternUtils {

    /**
     * 匹配给定目录下的所有文件。
     * 
     * @param locationPattern
     * @param rootDirFile
     * @return
     */
    public static List<File> findNameMatchingFiles(String locationPattern, File rootDirFile) {
        return findNameMatchingFiles(locationPattern, rootDirFile.listFiles());
    }

    /**
     * 匹配给定一批文件中的文件。
     * 
     * @param locationPattern
     * @param files
     * @return
     */
    public static List<File> findNameMatchingFiles(String locationPattern, File[] files) {
        List<File> patternFiles = new ArrayList<File>();
        if (files == null || files.length == 0) {
            return patternFiles;
        }

        for (File patternFile : files) {
            boolean isMatchable = Pattern.matches(locationPattern, patternFile.getName());
            if (!isMatchable || !patternFile.isFile()) {
                continue;
            }
            patternFiles.add(patternFile);
        }
        
        return patternFiles;
    }
}