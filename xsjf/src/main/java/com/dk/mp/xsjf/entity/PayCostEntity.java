package com.dk.mp.xsjf.entity;

/**
 * Created by cobb on 2017/8/2.
 */

public class PayCostEntity {

    private String xm;  //姓名
    private String xh;  //学号
    private String yingjiao;  //应缴款金额
    private String yijiao;  //已缴金额
    private String mianjiao;  //免缴金额
    private String wnqf;  //往年欠费
    private String hjje;  //缓缴金额
    private String dsh;  //待收款
    private String qgzx;  //勤工助学金额
    private String jzxj;  //奖助学金金额

    public PayCostEntity(String yingjiao, String yijiao, String mianjiao, String wnqf, String hjje, String dsh) {
        this.yingjiao = yingjiao;
        this.yijiao = yijiao;
        this.mianjiao = mianjiao;
        this.wnqf = wnqf;
        this.hjje = hjje;
        this.dsh = dsh;
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

    public String getYingjiao() {
        return yingjiao;
    }

    public void setYingjiao(String yingjiao) {
        this.yingjiao = yingjiao;
    }

    public String getYijiao() {
        return yijiao;
    }

    public void setYijiao(String yijiao) {
        this.yijiao = yijiao;
    }

    public String getMianjiao() {
        return mianjiao;
    }

    public void setMianjiao(String mianjiao) {
        this.mianjiao = mianjiao;
    }

    public String getWnqf() {
        return wnqf;
    }

    public void setWnqf(String wnqf) {
        this.wnqf = wnqf;
    }

    public String getHjje() {
        return hjje;
    }

    public void setHjje(String hjje) {
        this.hjje = hjje;
    }

    public String getDsh() {
        return dsh;
    }

    public void setDsh(String dsh) {
        this.dsh = dsh;
    }

    public String getQgzx() {
        return qgzx;
    }

    public void setQgzx(String qgzx) {
        this.qgzx = qgzx;
    }

    public String getJzxj() {
        return jzxj;
    }

    public void setJzxj(String jzxj) {
        this.jzxj = jzxj;
    }
}
