package com.taixin.android.bean;

import java.io.Serializable;

/**
 * 
 * EPG 
 * @author Joey
 *
 */
public class Epg implements Serializable{
	
	private String mBeginTime;
	private String mDate;
	private long duration;
	private int mTsId;
	private int mServiceId;
	private String mContent;
	private String mDescription;
	
	public Epg()
	{
		this.mTsId = -1;
		this.mServiceId = -1;
		this.mBeginTime = "";
		this.mDescription = "";
		this.mContent = "";
		duration = 0;
	}
	/**
	 * 
	 * @param tsid int
	 * @param serviceid
	 * @param content
	 * @param beginTime
	 * @param Date
	 */
	public Epg(int tsid,int serviceid,String content,String beginTime,String Date)
	{
		mTsId = tsid;
		mServiceId = serviceid;
		
		this.mDescription = "";
		this.mContent = content;
		this.mDate = Date;
		this.mBeginTime = beginTime;
	}
	public void setTsId(int tsId)
	{
		mTsId = tsId;
	}
	public void setServiceId(int serviceId)
	{
		mServiceId = serviceId;
	}
	public void setTime(String begintime,String Date)
	{
		this.mDate = Date;
		this.mBeginTime = begintime;
	}
	public void setContent(String content)
	{
		mContent = content;
	}
	public void setDescriptioin(String des)
	{
		this.mDescription = des;
	}
	public int getTSID(){
		return mTsId;
	}
	
	public int getServiceID(){
		return mServiceId;
	}
	public String getContent(){
		return mContent;
	}
	
	public String getDescription(){
		return mDescription;
	}
	
	public String getBeginTime(){
		return mBeginTime;
	}
	
	public String getDate(){
		return mDate;
	}

}
