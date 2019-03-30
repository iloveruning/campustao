package com.arunning.stao.ic.util;

import org.springframework.lang.Nullable;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenliangliang
 * @date 2019/3/25
 */
public class FileTypeUtil {

    private static Set<String> imgTypeSet = new HashSet<>();
    private static Set<String> videoTypeSet = new HashSet<>();
    private static Set<String> docTypeSet = new HashSet<>();

    static {

        imgTypeSet.add("jpg");
        imgTypeSet.add("png");
        imgTypeSet.add("jpeg");
        imgTypeSet.add("webp");
        imgTypeSet.add("bmp");
        imgTypeSet.add("svg");
        imgTypeSet.add("raw");
        imgTypeSet.add("gif");
        imgTypeSet.add("tif");
        imgTypeSet.add("psd");

        videoTypeSet.add("flv");
        videoTypeSet.add("mp4");
        videoTypeSet.add("rmvb");
        videoTypeSet.add("wmv");
        videoTypeSet.add("avi");
        videoTypeSet.add("3gp");
        videoTypeSet.add("dmv");
        videoTypeSet.add("rm");
        videoTypeSet.add("mpe");
        videoTypeSet.add("mov");
        videoTypeSet.add("mkv");
        videoTypeSet.add("f4v");
        videoTypeSet.add("webm");

        docTypeSet.add("pdf");
        docTypeSet.add("doc");
        docTypeSet.add("docx");
        docTypeSet.add("xls");
        docTypeSet.add("xlsx");
        docTypeSet.add("ppt");
        docTypeSet.add("pptx");
        docTypeSet.add("txt");
        docTypeSet.add("md");

    }


    private FileTypeUtil() {
    }

    public static String getFileType(File file) {

        String name = file.getName();

        return getFileType(name);
    }

    @Nullable
    public static String getFileType(String file) {

        if (file == null) {
            return null;
        }

        int i = file.lastIndexOf(".");

        if (i < 0) {
            return null;
        }
        return file.substring(i + 1);
    }

    public static boolean isImage(File file) {
        String fileType = getFileType(file);

        if (fileType == null) {
            return false;
        }
        return imgTypeSet.contains(fileType.toLowerCase());
    }

    public static boolean isVideo(File file) {
        String fileType = getFileType(file);

        if (fileType == null) {
            return false;
        }
        return videoTypeSet.contains(fileType.toLowerCase());
    }

    public static boolean isDoc(File file) {
        String fileType = getFileType(file);

        if (fileType == null) {
            return false;
        }
        return docTypeSet.contains(fileType.toLowerCase());
    }


    public static boolean isVideo(String fileType) {
        if (fileType == null) {
            return false;
        }
        return videoTypeSet.contains(fileType.toLowerCase());
    }

    public static boolean isImage(String fileType) {
        if (fileType == null) {
            return false;
        }
        return imgTypeSet.contains(fileType.toLowerCase());
    }

    public static boolean isDoc(String fileType) {
        if (fileType == null) {
            return false;
        }
        return docTypeSet.contains(fileType.toLowerCase());
    }


}
