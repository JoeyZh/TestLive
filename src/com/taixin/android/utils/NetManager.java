package com.taixin.android.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetManager {
	private Context context;
	   //定义一个WifiManager对象
	private WifiManager mWifiManager;
	//定义一个WifiInfo对象
	private WifiInfo mWifiInfo;
		//扫描出的网络连接列表
	private List<ScanResult> mWifiList;
		//网络连接列表
	private List<WifiConfiguration> mWifiConfigurations;
	WifiLock mWifiLock;
	private InetAddress address;
	private boolean ping;
	private String hostname;
	public NetManager(Context context)
	{
		this.context = context; 
		mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		mWifiInfo = mWifiManager.getConnectionInfo();
	}
	//打开wifi
	public void openWifi(){
		if(!mWifiManager.isWifiEnabled()){
			mWifiManager.setWifiEnabled(true);
		}
	}
		//关闭wifi
	public void closeWifi(){
		if(!mWifiManager.isWifiEnabled()){
			mWifiManager.setWifiEnabled(false);
		}
	}
	 // 检查当前wifi状态  
	    public int checkState() {  
	        return mWifiManager.getWifiState();  
	   }  
	//锁定wifiLock
	public void acquireWifiLock(){
		mWifiLock.acquire();
	}
		//解锁wifiLock
		public void releaseWifiLock(){
			//判断是否锁定
			if(mWifiLock.isHeld()){
				mWifiLock.acquire();
			}
		}
		//创建一个wifiLock
		public void createWifiLock(){
			mWifiLock=mWifiManager.createWifiLock("test");
		}
		//得到配置好的网络
		public List<WifiConfiguration> getConfiguration(){
			return mWifiConfigurations;
		}
		//指定配置好的网络进行连接
		public void connetionConfiguration(int index){
			if(index>mWifiConfigurations.size()){
				return ;
			}
			//连接配置好指定ID的网络
			mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId, true);
		}
		public void startScan(){
			mWifiManager.startScan();
			//得到扫描结果
			mWifiList=mWifiManager.getScanResults();
			//得到配置好的网络连接
			mWifiConfigurations=mWifiManager.getConfiguredNetworks();
		}
		public String getMacAddress(){
			return (mWifiInfo==null)?"NULL":mWifiInfo.getMacAddress();
		}
		public String getBSSID(){
			return (mWifiInfo==null)?"NULL":mWifiInfo.getBSSID();
		}
		public int getIpAddress(){
			return (mWifiInfo==null)?0:mWifiInfo.getIpAddress();
		}
		//得到连接的ID
		public int getNetWordId(){
			return (mWifiInfo==null)?0:mWifiInfo.getNetworkId();
		}
		//得到wifiInfo的所有信息
		public String getWifiInfo(){
			return (mWifiInfo==null)?"NULL":mWifiInfo.toString();
		}
		public String getSSID()
		{
			return (mWifiInfo==null)?"NULL":mWifiInfo.getSSID();
		}
		public boolean ping(String host)
		{
			Log.d("NetManager","host = "+host);
			try {
				Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 "+host);
				int returnVal =-1;
				try {
					returnVal = p1.waitFor();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ping = (returnVal == 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ping;
			
		}
		
		public String getLocalIpAddress() {
			new Thread()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					 try {
					        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
					            NetworkInterface intf = en.nextElement();
					            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					                InetAddress inetAddress = enumIpAddr.nextElement();
					                if (!inetAddress.isLoopbackAddress()) {
					                	Log.i("NetManager",inetAddress.getHostName());
					                    hostname = inetAddress.getHostName();
					                }
					            }
					        }
					    } catch (SocketException ex) {
					    	hostname = null;
					        Log.e("NetManager", ex.getMessage());
					    }
				}
				
			}.start();
		   
		    return hostname;
		}
		/**检查是否接入无线网络*/
		public boolean isWifiable() {
			// TODO Auto-generated method stub
			ConnectivityManager mgrConn = (ConnectivityManager) context   
	                .getSystemService(Context.CONNECTIVITY_SERVICE);   
	        return ((mgrConn.getActiveNetworkInfo() != null && 
	        		mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)); 
		}
		/**检查是否可以上网，wifi和移动网络*/
		public boolean isNetAvaible()
		{
			ConnectivityManager mgrConn = (ConnectivityManager) context   
	                .getSystemService(Context.CONNECTIVITY_SERVICE);   
			 TelephonyManager mgrTel = (TelephonyManager) context   
		                .getSystemService(Context.TELEPHONY_SERVICE);   
			return ((mgrConn.getActiveNetworkInfo() != null && 
	        		mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)|| 
	        		mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
		}

}
