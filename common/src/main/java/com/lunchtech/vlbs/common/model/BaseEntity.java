package com.lunchtech.vlbs.common.model;

import java.io.Serializable;

/**
 * Created by liruibo on 2017/11/6.
 */
public class BaseEntity implements Serializable {
    /***
     * 主键
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
