package com.dk.mp.xsxx.entity;

/**
 * Created by cobb on 2017/8/8.
 */

public class KqInfimationEntity {

    private String title1;  //标题1（班主任：学生姓名，老师：学生姓名，学生：课程名称）
    private String title2;  //标题2（学号，学生角色时不需要）
    private String title3;  //项目名称（班主任：不需要，老师：班级名称，学生：时间）
    private String cdcs;  //迟到次数
    private String kkcs;  //旷课次数
    private String qjcs;  //请假次数
    private String ztcs;  //早退次数

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getCdcs() {
        return cdcs;
    }

    public void setCdcs(String cdcs) {
        this.cdcs = cdcs;
    }

    public String getKkcs() {
        return kkcs;
    }

    public void setKkcs(String kkcs) {
        this.kkcs = kkcs;
    }

    public String getQjcs() {
        return qjcs;
    }

    public void setQjcs(String qjcs) {
        this.qjcs = qjcs;
    }

    public String getZtcs() {
        return ztcs;
    }

    public void setZtcs(String ztcs) {
        this.ztcs = ztcs;
    }

    public KqInfimationEntity(String title1, String title2, String title3, String cdcs, String kkcs, String qjcs, String ztcs) {
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.cdcs = cdcs;
        this.kkcs = kkcs;
        this.qjcs = qjcs;
        this.ztcs = ztcs;
    }
}
