package com.sweet.test;

public class City {
    private String cid;
    private String cname;
    private String pid;

    public City(String cid, String cname, String pid) {
        this.cid = cid;
        this.cname = cname;
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
