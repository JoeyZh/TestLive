package com.taixin.android.bean;

import android.util.Log;
/**
 *<dl>
 * <dt>ZoneItem.java</dt>
 * <dt>定义EPG信息</dt>
 * <dt>Copyright: </dt>
 * <dd>Copyright (C) 2014</dd>
 * <dt>Company: </dt>
 * <dd>山东泰信电子股份有限公司</dd>
 * <dt>CreateDate: </dt>
 * <dd>2014-03-18</dd>
 * </dl>
 * @author zhouye
 *
 */
public class EPGService {
	
	public int tsID = -1;
	public int serviceID = -1;
	public int eventID;
	
	public String eventName_zh;
	public String description;
	public String data,time;
	/**
	 * 0 normal
	 * 1 playbacking
	 * 2 recording
	 * 3 ordered
	 * 4 recorded
	 */
	public static final int ON_RECORDING = 2;
	public static final int NORMAL = 0;
	public static final int ON_PLAYBACKING = 1;
	public static final int ORDERED = 3;
	public static final int RECORDED = 4;
	/**
	 * EPG 持续时长
	 */
	public int duration;
	/**
	 * 电视台名称
	 */
	public String serviceName = "";
	/**
	 * pvr 录制时长
	 */
	public int pvr_duration;
	
	public void setServiceName(String name){
		this.serviceName=name;
	}
	public String getServiceName(){
		return this.serviceName;
	}
	
	/**
	 * 当前epg对应的ts路径
	 */
	public String filePath;
	
	public String fileName;
	public long fileLength;
	
	
	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(int fileLength) {
		this.fileLength = fileLength;
	}

	
	
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 0 :无状态
	 * 1 ：当前正在回放
	 * 2：当前正在录制
	 * 3：已经预约
	 * 4：可以回放
	 */ 
	public int recordStatus;
	public void setRecordStatus(int status)
	{
		recordStatus = status;
	}
	public int getStatus()
	{
		return recordStatus;
	}
	public String show_time;
	
	public EPGService()
	{
		
	}
	
	public EPGService(int ttsID,int tserviceID, int teventID, String teventName_zh, 
			String tDescription, String tdata,String ttime, int tDuration){
		tsID = ttsID;
		serviceID = tserviceID;
		eventID = teventID;
		eventName_zh = teventName_zh;
		description = tDescription;
		duration = tDuration;
		data = tdata;
		time = ttime;
		
		String t[] = time.split(":");
		show_time = t[0]+":"+t[1];
	}
	
	public int getTSID(){
		return tsID;
	}
	
	public int getServiceID(){
		return serviceID;
	}
	
	public int getEventID(){
		return eventID;
	}
	
	public String getZHEventName(){
		return eventName_zh;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getData(){
		return data;
	}
	
	public String getTime(){
		return time;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public String getShowTime(){
		return show_time;
	}
}

