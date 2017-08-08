package com.dk.mp.czdh.entity;

/**
 * Created by cobb on 2017/8/8.
 */

public class CzdhEntity {

    private String xm;
    private String xh;
    private String pm;
    private String zf;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getZf() {
        return zf;
    }

    public void setZf(String zf) {
        this.zf = zf;
    }

    public CzdhEntity(String xm, String xh, String pm, String zf) {
        this.xm = xm;
        this.xh = xh;
        this.pm = pm;
        this.zf = zf;
    }
}
