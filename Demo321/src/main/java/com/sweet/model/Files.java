package com.sweet.model;

import java.util.Date;

public class Files {
    private String fid;

    private String url;

    private String md5;

    private Date uploadtime;

    private Long alreadysize;

    private Long allsize;

    private Integer isfinish;

    private String uid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Long getAlreadysize() {
        return alreadysize;
    }

    public void setAlreadysize(Long alreadysize) {
        this.alreadysize = alreadysize;
    }

    public Long getAllsize() {
        return allsize;
    }

    public void setAllsize(Long allsize) {
        this.allsize = allsize;
    }

    public Integer getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(Integer isfinish) {
        this.isfinish = isfinish;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
}