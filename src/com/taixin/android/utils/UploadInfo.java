
package com.taixin.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
/**
 * 
 * @author zhouye
 * 
 *
 */
public class UploadInfo implements Runnable{

	private final String TAG = "unloadinfo";
	private String URL_str ="http://"+Constants.TESTDEMAIN+":"+Constants.DOWNLOADPORT+"/uploadFileAction.do?method=upPhoneDaInfo";
	private Context mContext;
	private int ctlmode;
	private String BoxNo;
	private String AreaCode;
	private String displayinfo;
	private int verCode;
	private String verName;
	private unloadCallback callback;
	/**
	 * 
	 * @param context
	 * @param mode ����ģʽ Int 0/1
	 * @param display �ֱ��� String e.g. 1280*720
	 * @param vercode �汾�� int
	 * @param vername �汾��� String
	 */
	public UploadInfo(Context context,int mode,String display,int vercode,String vername)
	{
		mContext = context;
		ctlmode= mode;
		displayinfo = display;
		verCode = vercode;
		verName = vername;
	}
	/**
	 * ���û�кź�������
	 * @param No
	 * @param areacode
	 */
	public void setInfo(String No,String areacode)
	{
		BoxNo = No;
		AreaCode = areacode;
	}
	/**
	 * ��Ҫ���ø÷���ʵ�ֻص��ӿ� 
	 * @param callback
	 */
	public void setCallback(unloadCallback callback)
	{
		this.callback = callback;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		URL url;
		try {
			url = new URL(URL_str);
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Cache-Control","no-cache"); 
			conn.setRequestProperty("Content-Type","application/zip");
			conn.setRequestProperty("Charsert","UTF-8");
			conn.connect();
			Log.i(TAG, "loadfile");
			OutputStream os=conn.getOutputStream();
			
			String json = getMobilInfo();
			byte[] bt=json.getBytes();
			int len = bt.length;
			os.write(bt, 0, len);
			Log.i(TAG, "Success");
			os.flush();
			os.close();
			
			InputStream in =conn.getInputStream();
			BufferedReader b = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String ss=b.readLine(); 
			Log.i(TAG,"callback ss="+ss);
			try {
				JSONObject obj = new JSONObject(ss);
				if(callback!=null)
					callback.result(obj.getInt("status"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(callback!=null)
					callback.result(5);
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i(TAG,"�ϴ�ʧ�� :" + e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i(TAG,"�ϴ�ʧ�� :" + e);
		}
		
	}
/**
 * "system":"ios",
 * "model":"iPhone5","imei":"0000000000","system_version":"IOS7",
 * "tel_number":"186xxxxxxxx","brand":"apple",
 * "ctl_mode":"0","device_type":"0","ctl_stbno":"xxxxxxxxxx",
 * "displaymetrics":"960*540","verion_code":"30","version_name":"1.0.30",
 * "areacode":"xxxxxxxxxx"
 * @return
 */
	private String getMobilInfo()
	{
		TelephonyManager tm = (TelephonyManager)  
                mContext.getSystemService(Context.TELEPHONY_SERVICE);  
	  StringBuilder sb = new StringBuilder(); 
	  
	  String system = "system";
	  String model = "model";
	  String imei = "imei";
	  String system_version = "system_version";
	  String tel_number = "tel_number";
	  String brand = "brand";
	  String ctl_mode = "ctl_mode";
	  String device_type = "device_type";
	  String ctl_stbno = "ctl_stbno";
	  String displaymetrics = "displaymetrics";
	  String verion_code = "verion_code";
	  String version_name = "version_name";
	  String areacode = "areacode";
	  
	  String info = "";
	  info +="{";
	  info +="\""+system+"\":"+"\""+"android"+"\""+",";
	  info +="\""+model+"\":"+"\""+Build.MODEL+"\""+",";
	  info +="\""+imei+"\":"+"\""+tm.getDeviceId()+"\""+",";
	  info +="\""+system_version+"\":"+"\""+"android"+Build.VERSION.RELEASE+"\""+",";
	  info +="\""+tel_number+"\":"+"\""+tm.getLine1Number()+"\""+",";
	  info +="\""+brand+"\":"+"\""+Build.BOARD+"\""+",";
	  info +="\""+ctl_mode+"\":"+"\""+ctlmode+"\""+",";
	  info +="\""+device_type+"\":"+"\""+"0"+"\""+",";
	  info +="\""+ctl_stbno+"\":"+"\""+BoxNo+"\""+",";
	  info +="\""+displaymetrics+"\":"+"\""+displayinfo+"\""+",";
	  info +="\""+verion_code+"\":"+"\""+verCode+"\""+",";
	  info +="\""+version_name+"\":"+"\""+verName+"\""+",";
	  info +="\""+areacode+"\":"+"\""+AreaCode+"\"";
	  info +="}";
	  
	  Log.d(TAG,"info = "+info);
	  return info;  
		
	}
	/**
	 * �ϱ���ɺ�Ļص��ӿڣ��жϻص�״̬
	 * @author ����
	 *
	 */
	public interface unloadCallback
	{
		public void result(int status);
	}
}

