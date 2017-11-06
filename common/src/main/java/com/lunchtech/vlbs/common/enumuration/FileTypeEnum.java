package com.lunchtech.vlbs.common.enumuration;

import java.util.EnumSet;

/**
 *
 * 文件类型
 *
 * Created by radish on 9/11/15.
 */
public enum FileTypeEnum {

    /**
     *苹果
     */
    IOS(1, "IOS"),

    /**
     *安卓
     */
    ANDROID(2, "ANDROID"),

    /**
     * M1
     */
    M1(3, "M1"),

    /**
     * C1
     */
    C1(4, "C1");

    /**
     * 状态名称
     */
    private final String statusName;
    /**
     * 状态值
     */
    private final int statusValue;

    FileTypeEnum(int statusValue, String statusName) {
        this.statusName = statusName;
        this.statusValue = statusValue;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getStatusValue() {
        return statusValue;
    }

    /**
     * 根据枚举值查找枚举类型
     *
     * @param statusValue
     * @return
     */
    public static FileTypeEnum getEnumByValue(int statusValue) {
        for (FileTypeEnum resultEnum : EnumSet.allOf(FileTypeEnum.class)) {
            if (resultEnum.getStatusValue() == statusValue) {
                return resultEnum;
            }
        }
        throw new RuntimeException(statusValue + "不是resultEnum的合法value");
    }
}
