package com.taixin.android.bean;

import java.util.HashMap;

/**
 * <dt>Program.java</dt>
 * 节目列表描述
 * @author zhouye
 *
 */
public class Program {
	private int mTsId;
	private int mServiceId;
	/**
	 * 在所有节目分组的索引
	 */
	private int mLogicNum;
	/**
	 * 台标图片名称
	 */
	private String mLogoName;
	/**
	 * 节目名称
	 */
	private String mName;
	
	private Epg mCurrentEpg;
	private int frequence = 0;
	private int mGroupId = 0;
	
	private HashMap mChannelMap = new HashMap();
	/**
	 * 7583平台中处理的TSID
	 * 在下载logo,epg是需要用到该数据
	 * @return
	 */
	public int gettmpTsID()
	{
		if(frequence == 0)
			return mTsId;
		int tmp = mTsId<<16|frequence;
		return tmp;
	}
	/**
	 * @return
	 */
	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int freq) {
		this.frequence = freq;
	}

	public Program(){
		this.mLogoName = "";
		this.mName = "";
		mCurrentEpg = new Epg();
		mChannelMap.clear();
	}
	
	public Program(int tsid, int serviceid, String logo_name,String name){
		mTsId = tsid;
		mServiceId = serviceid;
		this.mLogoName= logo_name;
		this.mName = name;
		mCurrentEpg = new Epg(tsid,serviceid,"","","");
		mChannelMap.clear();
	}

	public void setTSID(int id){
		mTsId = id;
		mCurrentEpg.setTsId(id);
	}
	
	public void setServiceID(int id){
		mServiceId = id;
		mCurrentEpg.setServiceId(id);
	}
	public void setName(String name)
	{
		mName = name;
	}
	public void SetLogoName(String name){
		this.mLogoName = name;
	}
	public void setGroupId(int id)
	{
		mGroupId = id;
	}
	
	public void setContent(String content){
		mCurrentEpg.setContent(content);
	}
	public int getTSID(){
		return mTsId;
	}
	
	public int getServiceID(){
		return mServiceId;
	}
	
	public String getLogoName(){
		return mLogoName;
	}
	
	public String getName(){
		return mName;
	}
	
	public String getContent(){
		return mCurrentEpg.getContent();
	}
	public Epg getCurrentEpg()
	{
		return mCurrentEpg;
	}
	public void setEpgContent(Epg epg)
	{
		mCurrentEpg = epg;
	}
	public void setDescription(String des)
	{
		mCurrentEpg.setDescriptioin(des);
	}
	public int getGroupId()
	{
		return mGroupId;
	}
	public void setLogicNum(int index)
	{
		mLogicNum = index;
	}
	public int getLogicNum()
	{
		return mLogicNum;
	}

	public void setChannelMap(HashMap map)
	{
		mChannelMap = map;
	}
	public HashMap getChannelMap()
	{
		return mChannelMap;
	}
}
