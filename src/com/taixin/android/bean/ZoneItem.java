package com.taixin.android.bean;
/**
 *<dl>
 * <dt>ZoneItem.java</dt>
 * <dt>定义区域码</dt>
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
public class ZoneItem {
	
	private String areaCode;
	private String areaName;
	private int grade;
	private String parentCode;
	
	public ZoneItem(String code,String name,int grade,String parent)
	{
		areaCode = code;
		areaName = name;
		this.grade = grade;
		parentCode = parent;
	}
	public String getAreaCode()
	{
		return areaCode;
	}
	public String getAreaName()
	{
		return areaName;
	}
	public String getParentAreaCode()
	{
		return parentCode;
	}
	public int getGrade()
	{
		return grade;
	}

}
