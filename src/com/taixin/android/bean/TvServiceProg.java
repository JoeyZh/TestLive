package com.taixin.android.bean;

public class TvServiceProg {
	private int serviceid;
	private int tsid;
	private int pcrpid;
	private int version;
	private int pmtpid;
	private String Progname;
	private int type;
	private int free_ca;
	private int insdt;
	
	
	public TvServiceProg()
	{
		serviceid = 0;
		tsid = 0;
	//	pcrpid = 0;
		version = 0;
		Progname = "û�н�Ŀ";
		type = 1;
		free_ca = 0;
	}
	public TvServiceProg(int Serviceid,int Tsid,String name)
	{
		serviceid = Serviceid;
		tsid = Tsid;
	//	pcrpid = 0;
		version = 0;
		Progname = name;
		type = 1;
		free_ca = 0;
	}
	public void setServiceid(int id)
	{
		serviceid = id;
	}
	public void setTsid(int tsid)
	{
		this.tsid = tsid;
	}
	public void setPcrPid(int pid)
	{
		this.pcrpid = pid;
	}
	public void setVersion(int version)
	{
		this.version = version;
	}
	public void setPmtPid(int pid)
	{
		this.pmtpid = pid;
	}
	public void setProgname(String name)
	{
		this.Progname = name;
	}
	public void setType(int type){
		this.type = type;
	}
	public void setFree_ca(int free_ca){
		this.free_ca = free_ca;
	}
	public void setInsdt(int insdt)
	{
		this.insdt = insdt;
	}
	public int getServiceid()
	{
		return serviceid;
	}
	public int getTsid()
	{
		return tsid;
	}
	public int getPcrPid()
	{
		return pcrpid;
	}
	public String getProgname()
	{
		return Progname;
	}
	public int getVersion()
	{
		return version;
	}
	public int getPmtPid()
	{
		return pmtpid;
	}
	public int getFree_ca()
	{
		return free_ca;
	}
	public int getType()
	{
		return type;
	}
	public int getInsdt() {
		// TODO Auto-generated method stub
		return insdt;
	}
}
