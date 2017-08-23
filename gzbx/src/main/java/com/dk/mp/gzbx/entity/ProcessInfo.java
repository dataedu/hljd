package com.dk.mp.gzbx.entity;

import java.io.Serializable;

/**
 * 流程信息
 * @author dake
 *
 */
public class ProcessInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4130363949330288836L;
	private String id;
	private String name;//流程指定人
	private String status;//处理状态
	private String statusname;//处理状态对应名称
	private String time;//处理时间
	private String title;//处理标题
	private String repairstatus;//处理结果
	private String lc;//流程
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRepairstatus() {
		return repairstatus;
	}
	public void setRepairstatus(String repairstatus) {
		this.repairstatus = repairstatus;
	}
	public String getLc() {
		return lc;
	}
	public void setLc(String lc) {
		this.lc = lc;
	}
	
	
}
