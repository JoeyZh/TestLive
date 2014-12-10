package com.taixin.android.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 节目分组信息
 * @author Joey
 *
 */
public class ProgramWithGroup {
	/**
	 * 分组名称
	 */
	private String mGroupName;
	/**
	 * 分组索引
	 */
	private int mGroupId;
	/**
	 * 分组节目列表
	 */
	private ArrayList<Program> mProgramList;
	/**
	 * 对应分组控件的resource id
	 */
	private int mResId;
	
	private boolean hasProgramFlag;
	
	/**
	 * 分组容器显示的哈希表
	 */
	private ArrayList<HashMap<String,Object>> mProgramMapList;
	
	
	private HashMap mChannelGroupMap = new HashMap();
	
	public ProgramWithGroup()
	{
		mProgramList = new ArrayList<Program>();
		mProgramMapList = new ArrayList<HashMap<String,Object>>();
		hasProgramFlag = false;
		mResId = -1;
		mChannelGroupMap.clear();
	}
	
	public void setProgramMapList(ArrayList<HashMap<String,Object>> maplist)
	{
		mProgramMapList = maplist;
	}
	public ArrayList<HashMap<String,Object>> getProgramMapList()
	{
		return mProgramMapList;
	}
	public void setProgramList(ArrayList<Program> list)
	{
		mProgramList = list;
	}
	public void setHasProgramFlag(boolean flag)
	{
		hasProgramFlag = flag;
	}
	public ArrayList<Program> getProgramList()
	{
		return mProgramList;
	}
	public void setResourseId(int id)
	{
		mResId = id;
	}
	public int getResourceId()
	{
		return mResId;
	}
	public String getGroupName() {
		return mGroupName;
	}
	public void setGroupName(String groupname) {
		this.mGroupName = groupname;
	}
	public int getGroupId() {
		return mGroupId;
	}
	public void setGroupId(int groupid) {
		this.mGroupId = groupid;
	}
	
	public void add(Program item)
	{
		if(mProgramList!=null)
			mProgramList.add(item);
	}
	public void remove(int index)
	{
		if(index <0)
			return;
		if(mProgramList == null)
			return;
		if(mProgramList.size()<index)
			return;
		mProgramList.remove(index);
	}
	
	public void clear()
	{
		mProgramList.clear();
		hasProgramFlag = false;
	}
	public void set(int index,Program item)
	{
		mProgramList.set(index, item);
	}
	public boolean isHavingProgramFlag()
	{
		return hasProgramFlag;
	}
	public void addMapItem(HashMap<String,Object> item)
	{
		if(mProgramMapList!=null)
			mProgramMapList.add(item);
	}
	public void removeMapItem(int index)
	{
		if(index <0)
			return;
		if(mProgramMapList == null)
			return;
		if(mProgramMapList.size()<index)
			return;
		mProgramMapList.remove(index);
	}
	
	public void clearMapList()
	{
		mProgramMapList.clear();
	}
	public void setMapItem(int index,HashMap<String,Object> item)
	{
		mProgramMapList.set(index, item);
	}
	
	public void setChannelMap(HashMap map)
	{
		mChannelGroupMap = map;
	}
	public HashMap getChannelMap()
	{
		return mChannelGroupMap;
	}
}
