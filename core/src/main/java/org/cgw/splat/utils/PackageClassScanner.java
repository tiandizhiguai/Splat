package org.cgw.splat.utils;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;
import org.cgw.splat.exception.SplatException;
public class PackageClassScanner {

    private static final String      CLASS_EXT         = ".class";
    private static final String      JAR_FILE_EXT      = ".jar";
    private static final String      DOT               = ".";
    private static final String      ZIP_SLASH         = "/";
    private static final String      BLACK             = "";

    /**
     * ���packageNameΪ�գ����׳���ָ���쳣��</br> (����������һ��bug�����ɨ���ļ�ʱ��Ҫһ����·��Ϊ��ȡ�ַ��������������ڻ�û���޸�,���Լ��ϸ�����)
     * 
     * @param packageName
     */
    private static void ckeckNullPackageName(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
            throw new NullPointerException("packageName can't be null");
        }
    }

    /**
     * �ı� com -> com. �����ڱȽϵ�ʱ��ѱ��� completeTestSuite.class��ɨ���ȥ�����û��"." </br>��class����com��ͷ��class��Ҳ�ᱻɨ���ȥ,��ʵ���ƺ����ǰ����Ҫһ��
     * ".",����Ӱ�������
     * 
     * @param packageName
     * @return
     */
    private static String getWellFormedPackageName(String packageName) {
        return packageName.lastIndexOf(DOT) != packageName.length() - 1 ? packageName + DOT : packageName;
    }

    /**
     * ɨ���·��������class����������������class�ļ���</br> �����·��Ϊ com.abs + A.class �������� abs �����classNotFoundException</br> ��ΪclassName
     * Ӧ��Ϊ com.abs.A ����ȴ��Ϊ abs.A,�˹�����Ը��쳣���к��Դ���,�п�����һ�������Ƶĵط����Ժ���Ҫ�����޸�</br>
     * 
     * @param packageName ��·�� com | com. | com.abs | com.abs.
     * @param classFilter class�����������˵�����Ҫ��class
     * @return
     */
    public static List<Class<?>> scanPackage(String packageName) {
        // ���packageName �Ƿ�Ϊ�գ����Ϊ�վ��׳�NullPointException
        ckeckNullPackageName(packageName);
        // ʵ����һ������ P: ����class
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        // Ĭ��ɨ�����а��µ�class�ļ�
        String filePath = "";
        if (StringUtils.isNotBlank(packageName)) {
            filePath = packageName.replace(DOT, ZIP_SLASH);
        }

        Enumeration<URL> urls = ConfigurationFileResolverUtils.getClasspathFileUri(filePath);
        while (urls.hasMoreElements()) {
            // ��� classes
            URL url = urls.nextElement();
            fillClasses(new File(url.getPath()), getWellFormedPackageName(packageName),
                        classes);
        }
        return classes;
    }

    /**
     * �������������class ��䵽 classes
     * 
     * @param file ��·���µ��ļ�
     * @param packageName ��Ҫɨ��İ���
     * @param classFilter class������
     * @param classes List ����
     */
    private static void fillClasses(File file, String packageName, List<Class<?>> classes) {
        if (isDirectory(file)) {
            processDirectory(file, packageName, classes);
        } else if (isClass(file.getName())) {
            processClassFile(file, packageName, classes);
        } else if (isJarFile(file.getName())) {
            processJarFile(file, packageName, classes);
        }
    }

    /**
     * �������ΪĿ¼�����,��Ҫ�ݹ���� fillClasses����
     * 
     * @param directory
     * @param packageName
     * @param classFilter
     * @param classes
     */
    private static void processDirectory(File directory, String packageName, List<Class<?>> classes) {
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return isClass(pathname.getName()) || isDirectory(pathname) || isJarFile(pathname.getName());
            }
        };

        File[] files = directory.listFiles(fileFilter);

        for (File file : files) {
            fillClasses(file, packageName, classes);
        }
    }

    /**
     * ����Ϊclass�ļ������,�������������class �� classes
     * 
     * @param file
     * @param packageName
     * @param classFilter
     * @param classes
     */
    private static void processClassFile(File file, String packageName, List<Class<?>> classes) {
        final String filePathWithDot = file.getAbsolutePath().replace(File.separator, DOT);
        int subIndex = -1;
        if ((subIndex = filePathWithDot.indexOf(packageName)) != -1) {
            final String className = filePathWithDot.substring(subIndex).replace(CLASS_EXT, BLACK);
            fillClass(className, packageName, classes);
        }
    }

    /**
     * ����Ϊjar�ļ���������������������class �� classes
     * 
     * @param file
     * @param packageName
     * @param classFilter
     * @param classes
     */
    private static void processJarFile(File file, String packageName, List<Class<?>> classes) {
        try {
            for (ZipEntry entry : Collections.list(new ZipFile(file).entries())) {
                if (isClass(entry.getName())) {
                    final String className = entry.getName().replace(ZIP_SLASH, DOT).replace(CLASS_EXT, BLACK);
                    fillClass(className, packageName, classes);
                }
            }
        } catch (Throwable ex) {
            // ignore this ex
        }
    }

    /**
     * ���class �� classes
     * 
     * @param className
     * @param packageName
     * @param classes
     * @param classFilter
     */
    private static void fillClass(String className, String packageName, List<Class<?>> classes) {
        if (checkClassName(className, packageName)) {
            Class<?> clazz = null;
            try {
                clazz = ClassLoaderUtils.getCurrentThreadClassLoader().loadClass(className);
            } catch (ClassNotFoundException e) {
                throw new SplatException(e);
            }
            classes.add(clazz);
        }
    }

    private static boolean checkClassName(String className, String packageName) {
        return className.indexOf(packageName) == 0;
    }

    private static boolean isClass(String fileName) {
        return fileName.endsWith(CLASS_EXT);
    }

    private static boolean isDirectory(File file) {
        return file.isDirectory();
    }

    private static boolean isJarFile(String fileName) {
        return fileName.endsWith(JAR_FILE_EXT);
    }
}