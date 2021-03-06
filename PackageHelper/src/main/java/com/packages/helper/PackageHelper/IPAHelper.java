package com.packages.helper.PackageHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

public class IPAHelper {
	/**
     * 解析ipa文件
     *
     * @param file ipa的文件路径
     * @return
     */
    public static Map<String, Object> parseIpa(File file) {
        Map<String, Object> map = new HashMap();
        try {
            // File file = new File(url);
            InputStream is = new FileInputStream(file);
            ZipInputStream zipIns = new ZipInputStream(is);
            ZipEntry ze;
            InputStream infoIs = null;
            while ((ze = zipIns.getNextEntry()) != null) {
                if (!ze.isDirectory()) {
                    String name = ze.getName();
                    if (null != name && name.toLowerCase().contains(".app/info.plist")) {
                        ByteArrayOutputStream _copy = new ByteArrayOutputStream();
                        int chunk = 0;
                        byte[] data = new byte[1024];
                        while (-1 != (chunk = zipIns.read(data))) {
                            _copy.write(data, 0, chunk);
                        }
                        infoIs = new ByteArrayInputStream(_copy.toByteArray());
                        break;
                    }
                }
            }

            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(infoIs);

            //如果想要查看有哪些key ，可以把下面注释放开
            for (String keyName : rootDict.allKeys()) {
                System.out.println(keyName + ":" + rootDict.get(keyName));
            }
            // 应用名CFBundleName
            NSString parameters = (NSString) rootDict.get("CFBundleName");
            map.put("appName", parameters.toString());

            // 应用包名CFBundleDisplayNameCFBundleIdentifier
            parameters = (NSString) rootDict.get("CFBundleIdentifier");
            map.put("appId", parameters.toString());
            // 应用版本名
            parameters = (NSString) rootDict.objectForKey("CFBundleShortVersionString");
            map.put("versionName", parameters.toString());
            // 应用版本号
            parameters = (NSString) rootDict.get("CFBundleVersion");
            map.put("versionCode", parameters.toString());
            parameters = (NSString) rootDict.get("CFBundleDisplayName");
            map.put("packetSize", (double) (file.length() * 100 / 1024 / 1024) / 100 + " MB");
            infoIs.close();
            is.close();
            zipIns.close();

        } catch (Exception e) {
            System.out.println("ipa包解析异常！");
        }
        return map;
    }
}
