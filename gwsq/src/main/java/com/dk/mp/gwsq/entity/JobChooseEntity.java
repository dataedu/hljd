package com.dk.mp.gwsq.entity;

/**
 * Created by cobb on 2017/8/4.
 */

public class JobChooseEntity {

    private String jcid;  //节次id
    private String jcmc;  //节次名称
    private String sfyk;  //是否有课

    public JobChooseEntity(String jcid,String jcmc) {
        this.jcid = jcid;
        this.jcmc = jcmc;
    }

    public String getJcid() {
        return jcid;
    }

    public void setJcid(String jcid) {
        this.jcid = jcid;
    }

    public String getJcmc() {
        return jcmc;
    }

    public void setJcmc(String jcmc) {
        this.jcmc = jcmc;
    }

    public String getSfyk() {
        return sfyk;
    }

    public void setSfyk(String sfyk) {
        this.sfyk = sfyk;
    }
}
