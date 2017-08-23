package com.dk.mp.gzbx.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 故障申请实体对象
 * @author janebo
 *
 */
public class Malfunction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;//发起人
	private String title;//标题
	private String status;//状态
	private String statusname;//状态
	private String time;//时间
	private String address;//地点
	private String device;//设备
	private String des;//问题描述
	
	private List<ProcessInfo> list;//流程信息
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public List<ProcessInfo> getList() {
		return list;
	}
	public void setList(List<ProcessInfo> list) {
		this.list = list;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	
	

}
