package com.sweet.model;

import java.util.Date;

public class Userfiles {
    private String ufid;

    private String uid;

    private String fid;

    private Date uploadtime;

    private Integer issecondpass;

    public String getUfid() {
        return ufid;
    }

    public void setUfid(String ufid) {
        this.ufid = ufid == null ? null : ufid.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Integer getIssecondpass() {
        return issecondpass;
    }

    public void setIssecondpass(Integer issecondpass) {
        this.issecondpass = issecondpass;
    }
}