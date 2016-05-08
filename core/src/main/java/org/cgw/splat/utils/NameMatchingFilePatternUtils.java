package org.cgw.splat.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * ͨ����������ƥ���ļ���
 * 
 * @author guowei.cgw 2015��6��5�� ����2:08:22
 */
public class NameMatchingFilePatternUtils {

    /**
     * ƥ�����Ŀ¼�µ������ļ���
     * 
     * @param locationPattern
     * @param rootDirFile
     * @return
     */
    public static List<File> findNameMatchingFiles(String locationPattern, File rootDirFile) {
        return findNameMatchingFiles(locationPattern, rootDirFile.listFiles());
    }

    /**
     * ƥ�����һ���ļ��е��ļ���
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