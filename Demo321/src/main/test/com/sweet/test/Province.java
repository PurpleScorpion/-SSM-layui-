package com.sweet.test;

public class Province {
    private String pid;
    private String pname;

    public Province() {
    }

    public Province(String pid, String pname) {
        this.pid = pid;
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
