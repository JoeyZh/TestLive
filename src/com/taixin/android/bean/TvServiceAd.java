package com.taixin.android.bean;
/**
 * 
 * @author Administrator
 *
 */
public class TvServiceAd {
	private int version;
	private int atype;
	private int mtype;
	private String text;
	private String file_name;
	private int tsid;
	private int serviceid;
	private int freqMHZ;
	private int symKHZ;
	private String starttime;
	private String endtime;
	
	public TvServiceAd(){
		
	}
	
	public TvServiceAd(int tsid, int serviceid, String file_name)
	{
		this.tsid = tsid;
		this.serviceid = serviceid;
		this.file_name = file_name;
	}

	public void setVersion(int ver)
	{
		version = ver;
	}
	public void setAtype(int type)
	{
		atype = type;
	}
	
	public void setMtype(int type)
	{
		mtype = type;
	}
	
	public void setText(String txt)
	{
		text = txt;
	}
	
	public void setFileName(String name)
	{
		file_name = name;
	}
	
	public void setTSID(int id)
	{
		tsid = id;
	}
	
	public void setServiceID(int id){
		serviceid = id;
	}
	
	public void setFreqMHZ(int freq)
	{
		freqMHZ = freq;
	}
	
	public void setSymKHZ(int sym)
	{
		symKHZ = sym;
	}
	
	public void setStartTime(String time)
	{
		starttime = time;
	}
	
	public void setEndTime(String time)
	{
		endtime = time;
	}
	
	
	
	
	public int getVersion()
	{
		return version;
	}
	
	public int getAtype()
	{
		return atype;
	}
	
	public int getMtype()
	{
		return mtype;
	}
	
	public String getText()
	{
		return text;
	}
	
	public String getFileName()
	{
		return file_name;
	}
	
	public int getTSID()
	{
		return tsid;
	}
	
	public int getServiceID()
	{
		return serviceid;
	}
	
	public int getFreqMHZ()
	{
		return freqMHZ;
	}
	
	public int getSymKHZ()
	{
		return symKHZ;
	}
	
	public String getStartTime()
	{
		return starttime;
	}
	
	public String getEndTime()
	{
		return endtime;
	}
}
