package com.mp.dk.cwyd.entity;

/**
 * Created by cobb on 2017/8/2.
 */

public class BedMoveEntity {

    private String kssj;  //开始时间
    private String jssj;  //结束时间
    private String xm;  //姓名
    private String xh;  //学号
    private String id;  //申请id
    private String bj;  //班级

    public BedMoveEntity(String kssj, String jssj, String xm, String xh, String bj) {
        this.kssj = kssj;
        this.jssj = jssj;
        this.xm = xm;
        this.xh = xh;
        this.bj = bj;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }
}
