package com.lunchtech.vlbs.common.utils;

import org.joda.time.DateTime;

/**
 * 版本号生成器
 * <p>
 * Created by radish on 9/11/15.
 */
public class VersionGenerator {

    public static String generate(File file) {
        //TODO 以后加上CPU序列号等保证唯一
        String sysDate = DateTime.now().toString("yyyyMMddHHmmssSSS");
        return sysDate + "_" + (file.getId()==null?"":file.getId());
    }
}
