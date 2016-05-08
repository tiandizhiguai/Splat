package org.cgw.splat.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        System.out.println(new BigDecimal("").toPlainString());
    }

    // 统计文件的内容个数
    public static void fileStrSum() {
        try {
            Map<String, Integer> strCount = new HashMap<String, Integer>();
            BufferedReader reader = new BufferedReader(new FileReader("d:/rpc_ip4.txt"));
            String str = null;
            while ((str = reader.readLine()) != null) {
                if (strCount.containsKey(str)) {
                    int count = strCount.get(str);
                    count++;
                    strCount.put(str, count);
                } else {
                    strCount.put(str, 1);
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("d:/rpc_ip4"));
            for (String key : strCount.keySet()) {
                writer.write(key + "," + strCount.get(key));
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e) {

        }
    }

    // 文件内容排序
    public static void fileContextSort() {

        List<String> thread2 = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("d:/jstack.2015-11-06_2"));
            String threadInfo = null;
            while ((threadInfo = reader.readLine()) != null) {
                if (!threadInfo.contains("tid=")) {
                    continue;
                }
                threadInfo = threadInfo.substring(threadInfo.indexOf("\"") + 1, threadInfo.lastIndexOf("\""));
                thread2.add(threadInfo);
                Collections.sort(thread2);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("d:/jstack.2015-11-06_2.tmp"));
            for (String thread : thread2) {
                writer.write(thread);
                writer.write("\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
