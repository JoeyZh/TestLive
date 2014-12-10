package com.taixin.android.bean;
/**
 * 定义设备信息
 * @author Joey
 *
 */
public class Device {
	/**
	 * 设备端口号
	 */
	private int mPort;
	/**
	 * 设备名称
	 */
	private String mName;
	/**
	 * 设备IP
	 */
	private String mHostIp;
	/**
	 * 平台信息
	 */
	private String mPlatform = "";
	
	public Device()
	{
		
	}
	public Device(int port,String name,String hostip)
	{
		this.mPort = port;
		this.mName = name;
		this.mHostIp = hostip;
	}
	public Device(int port,String name,String hostip,String platform)
	{
		this.mPort = port;
		this.mName = name;
		this.mHostIp = hostip;
		this.mPlatform = platform;
	}
	public void setPort(int port)
	{
		mPort = port;  
	}
	public void setName(String n)
	{
		mName = n;
	}
	public void setIp(String ip)
	{
		mHostIp = ip;
	}
	public void setPlatform(String platform)
	{
		mPlatform = platform;
	}
	/**
	 * 获取配对STB设备的端口号
	 * @return 设备端口号
	 */
	public int getPort()
	{
		return mPort;
	}
	/**
	 * 获取STB设备的备注名称
	 * @return
	 */
	public String getName()
	{
		return mName;
	}
	/**
	 * 获取STB设备的IP
	 * @return
	 */
	public String getIp()
	{
		return mHostIp;
	}
	/**
	 * 获取ＳＴＢ设备的平台信息
	 * @return
	 */
	public String getPlatform()
	{
		return mPlatform;
	}
}
