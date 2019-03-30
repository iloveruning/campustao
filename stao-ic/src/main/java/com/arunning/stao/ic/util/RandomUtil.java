package com.arunning.stao.ic.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenliangliang
 * @date 2019/3/25
 */
public class RandomUtil {

    private RandomUtil() {
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static String dateRandom() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

}
