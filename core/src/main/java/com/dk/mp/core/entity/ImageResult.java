package com.dk.mp.core.entity;

/**
 * 图片上传返回结果实体
 * 作者：janabo on 2017/12/6 11:54
 */
public class ImageResult {
    private String fileId;
    private String state;
    private String msg;
    private String url;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
