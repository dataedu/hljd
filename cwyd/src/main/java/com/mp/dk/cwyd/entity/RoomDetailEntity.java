package com.mp.dk.cwyd.entity;

/**
 * Created by cobb on 2017/8/3.
 */

public class RoomDetailEntity {
    private String kssj;  //开始时间
    private String jssj;  //结束时间
    private String xm;  //姓名
    private String xh;  //学号
    private String yx;  //院系：申请id
    private String bj;  //班级
    private String tsqk;  //调宿情况
    private String sqly;  //申请理由

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

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

    public String getYx() {
        return yx;
    }

    public void setYx(String yx) {
        this.yx = yx;
    }

    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }

    public String getTsqk() {
        return tsqk;
    }

    public void setTsqk(String tsqk) {
        this.tsqk = tsqk;
    }

    public String getSqly() {
        return sqly;
    }

    public void setSqly(String sqly) {
        this.sqly = sqly;
    }
}
