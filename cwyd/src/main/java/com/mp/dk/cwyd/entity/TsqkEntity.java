package com.mp.dk.cwyd.entity;

import java.io.Serializable;

/**
 * Created by cobb on 2017/8/3.
 */

public class TsqkEntity implements Serializable{

    private String tsqkid;  //调宿情况id
    private String tsqkmc;  //调宿情况名称

    public TsqkEntity(String tsqkid, String tsqkmc) {
        this.tsqkid = tsqkid;
        this.tsqkmc = tsqkmc;
    }

    public String getTsqkid() {
        return tsqkid;
    }

    public void setTsqkid(String tsqkid) {
        this.tsqkid = tsqkid;
    }

    public String getTsqkmc() {
        return tsqkmc;
    }

    public void setTsqkmc(String tsqkmc) {
        this.tsqkmc = tsqkmc;
    }
}
