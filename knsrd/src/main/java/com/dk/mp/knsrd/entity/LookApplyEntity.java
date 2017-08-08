package com.dk.mp.knsrd.entity;

/**
 * Created by cobb on 2017/8/3.
 */

public class LookApplyEntity {

    private String sqdc;  //申请档次名称
    private String sqsj;  //申请时间
    private String sqzt;  //申请状态
    private String sqid;  //申请id

    public LookApplyEntity(String sqdc, String sqsj, String sqzt) {
        this.sqdc = sqdc;
        this.sqsj = sqsj;
        this.sqzt = sqzt;
    }

    public String getSqdc() {
        return sqdc;
    }

    public void setSqdc(String sqdc) {
        this.sqdc = sqdc;
    }

    public String getSqsj() {
        return sqsj;
    }

    public void setSqsj(String sqsj) {
        this.sqsj = sqsj;
    }

    public String getSqzt() {
        return sqzt;
    }

    public void setSqzt(String sqzt) {
        this.sqzt = sqzt;
    }

    public String getSqid() {
        return sqid;
    }

    public void setSqid(String sqid) {
        this.sqid = sqid;
    }
}
