package com.packages.helper.PackageHelper;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 用于解析apk
 */
public class APKHelper {
    /**
     * 获取一个包下的所有加壳工具名称
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getApkShellsName(String fileName) throws IOException {
        List<String> fileNames = getSoFiles(fileName);
        String shellsName = getShellsName(fileNames);
        return shellsName;
    }

    /**
     * 从文件名列表中获取 壳名
     *
     * @param fileNames
     * @return
     */
    private static String getShellsName(List<String> fileNames) {
        Map<String, String> maps = getLibMap();
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < fileNames.size(); i++) {
            for (String key : maps.keySet()) {
                if (fileNames.get(i).toLowerCase().endsWith(key.toLowerCase())) {
                    set.add(maps.get(key));
                    break;
                }
            }
        }

        if (set.size() == 0) {
            return "未加壳";
        }
        StringBuffer sb = new StringBuffer();
        for (String s : set) {
            sb.append(s).append("、");
        }
        return sb.substring(0, sb.length() - 1);


    }

    /**
     * 获取所有加壳名称
     *
     * @return
     */
    private static Map<String, String> getLibMap() {
        Map<String, String> map = new HashMap<>();
        map.put("libchaosvmp.so", "娜迦");
        map.put("libddog.so", "娜迦");
        map.put("libfdog.so", "娜迦");
        map.put("libedog.so", "娜迦企业版");

        map.put("libexec.so", "爱加密");
        map.put("libexecmain.so", "爱加密");
        map.put("ijiami.dat", "爱加密");
        map.put("ijiami.ajm", "爱加密企业版");

        map.put("libsecexe.so", "梆梆免费版");
        map.put("libsecmain.so", "梆梆免费版");
        map.put("libSecShell.so", "梆梆免费版");

        map.put("libDexHelper.so", "梆梆企业版");
        map.put("libDexHelper-x86.so", "梆梆企业版");

        map.put("libprotectClass.so", "360");
        map.put("libjiagu.so", "360");
        map.put("libjiagu_art.so", "360");
        map.put("libjiagu_x86.so", "360");

        map.put("libegis.so", "通付盾");
        map.put("libNSaferOnly.so", "通付盾");

        map.put("libnqshield.so", "网秦");

        map.put("libbaiduprotect.so", "百度");

        map.put("aliprotect.dat", "阿里聚安全");
        map.put("libsgmain.so", "阿里聚安全");
        map.put("libsgsecuritybody.so", "阿里聚安全");
        map.put("libmobisec.so", "阿里聚安全");

        map.put("libtup.so", "腾讯");
        map.put("libexec.so", "腾讯");
        map.put("libshell.so", "腾讯");
        map.put("mix.dex", "腾讯");
        map.put("lib/armeabi/mix.dex", "腾讯");
        map.put("lib/armeabi/mixz.dex", "腾讯");

        map.put("libtosprotection.armeabi.so", "腾讯御安全");
        map.put("libtosprotection.armeabi-v7a.so", "腾讯御安全");
        map.put("libtosprotection.x86.so", "腾讯御安全");

        map.put("libnesec.so", "网易易盾");

        map.put("libAPKProtect.so", "APKProtect");

        map.put("libkwscmm.so", "几维安全");
        map.put("libkwscr.so", "几维安全");
        map.put("libkwslinker.so", "几维安全");

        map.put("libx3g.so", "顶像科技");

        map.put("libapssec.so", "盛大");

        map.put("librsprotect.so", "瑞星");
        return map;
    }

    /**
     * 解析apk包，返回里面的每一个文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private static List<String> getSoFiles(String fileName) throws IOException {
        List<String> list = new ArrayList<>();
        ZipInputStream zipIn = null;
        BufferedReader br = null;
        try {
            zipIn = new ZipInputStream(new FileInputStream(fileName));
            br = new BufferedReader(new InputStreamReader(zipIn, "utf-8"));
            ZipEntry zipFile;
            while ((zipFile = zipIn.getNextEntry()) != null) {
                if (!zipFile.isDirectory()) {
                    list.add(zipFile.getName());
                }

            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (br != null) {
                try {

                    br.close();
                } finally {

                }
            }
            if (zipIn != null) {
                try {
                    zipIn.close();
                } finally {

                }
            }

        }

    }
}
