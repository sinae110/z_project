package com.vo;

import java.util.List;

public class ticketingBean {
	String st_name;
	String arv_name;
	String startTimelist;
	String arrivalTime;
	String howlongTime;
	List<ticketingBean> ttList;
	
	public ticketingBean() {
		super();
	}
	public ticketingBean(List<ticketingBean> ttList) {
		super();
		this.ttList = ttList;
	}


	public ticketingBean(String st_name, String arv_name, String startTimelist, String arrivalTime, String howlongTime) {
		super();
		this.st_name = st_name;
		this.arv_name = arv_name;
		this.startTimelist = startTimelist;
		this.arrivalTime = arrivalTime;
		this.howlongTime = howlongTime;
	}
	
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public String getArv_name() {
		return arv_name;
	}
	public void setArv_name(String arv_name) {
		this.arv_name = arv_name;
	}
	public String getStartTimelist() {
		return startTimelist;
	}
	public void setStartTimelist(String startTimelist) {
		this.startTimelist = startTimelist;
	}
	public String getHowlongTime() {
		return howlongTime;
	}
	public void setHowlongTime(String howlongTime) {
		this.howlongTime = howlongTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public List<ticketingBean> getTtList() {
		return ttList;
	}
	public void setTtList(List<ticketingBean> ttList) {
		this.ttList = ttList;
	}

}
