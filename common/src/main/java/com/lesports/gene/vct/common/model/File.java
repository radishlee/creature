package com.lesports.gene.vct.common.model;

import org.springframework.data.annotation.Id;

/**
 * 储存的文件对象
 * <p>
 * Created by radish on 9/11/15.
 */
public class File {
    @Id
    private String id;
    //父亲对象 上一个版本 pid=-1 表示是第一个版本
    private String pid = "-1";


    private String curVer;//版本号

    private String url;//文件url

    private Integer type;//文件类型 1.ios 2.android

    private String name;//文件名称

    private String info;//文件信息

    private String auth;//文件作者
    //是否最新版本
    private boolean isLatest = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCurVer() {
        return curVer;
    }

    public void setCurVer(String curVer) {
        this.curVer = curVer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setIsLast(boolean isLast) {
        this.isLatest = isLast;
    }

    public boolean isLatest() {
        return isLatest;
    }

    public void setIsLatest(boolean isLatest) {
        this.isLatest = isLatest;
    }
}
