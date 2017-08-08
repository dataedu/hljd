package com.dk.mp.knsrd.entity;

/**
 * Created by cobb on 2017/8/8.
 */

public class SqjxEntity {

    private String xmid;  //项目id
    private String xmmc;  //项目名称
    private String xmje;  //项目金额

    public SqjxEntity(String xmmc, String xmje) {
        this.xmmc = xmmc;
        this.xmje = xmje;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getXmje() {
        return xmje;
    }

    public void setXmje(String xmje) {
        this.xmje = xmje;
    }
}
