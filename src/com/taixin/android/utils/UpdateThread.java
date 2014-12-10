package com.taixin.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Looper;

/**
 * 从后台服务器下载APK更新
 * @author Joey
 *
 */
public class UpdateThread implements Runnable{

	private String mUrl = "";
	private Context mContext;
	private String description;
	private String url_apk;
	private String update_name;
	private int update_code;
	public boolean autoCheckFlag = false;
	public UpdateThread(Context context,String url,boolean flag)
	{
		mContext = context;
		mUrl = url;
		autoCheckFlag = flag;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.i("", "URL:"+mUrl);
		Looper.prepare();
		String ChanelResult = "";
		
		BufferedReader in = null;
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(mUrl);
		HttpResponse response;
		
		try {
			response = client.execute(request);
		
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			//String NL = System.getProperty("line.separator");
		
			while ((line = in.readLine()) != null){
				sb.append(line);
			}
			in.close();
		
			ChanelResult = sb.toString();
			if(ChanelResult!=""&&ChanelResult!=null)
			{
				Log.d("","ChannelResult="+ChanelResult);
			}
			String jsonstr = "{\"results\":"+ChanelResult+"}";
			
			parseUrlString(ChanelResult);
			
			//{"url":"/app/downLoadAction.do?method=downFileAction&path=soft_20130827172943_59.apk&productID=59","versionCode":5,"versionName":"1.0.0.5"}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Looper.loop();
	}
	private String getVersionName()
	{
		try {
			return mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	private int getVersionCode()
	{
		try {
			int versionCode = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionCode;
			SaveInstance.getInstance("txlive", mContext).putInt("old_version_code", versionCode);
			return versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}
	/**
	 * 解析版本信息更新
	 * @param info
	 */
	private void parseUrlString(String info)
	{
		Log.d("","json = "+info);
		try {
			JSONObject jsonObj = new JSONObject(info);
			description = jsonObj.getString("updateDesc");
			url_apk = jsonObj.getString("url");
			update_code = jsonObj.getInt("versionCode");
			update_name = jsonObj.getString("versionName");
			Log.d("","url_apk = "+url_apk+"versionCode = "+update_code+"versionName = "+update_name);
			if(comparedVersioninfo(update_code,update_name))
			{
				showDialog();
			}
			else if(autoCheckFlag)
			{
				showDialog2();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 提示用户是否下载最新版本的对话框
	 */
	private void showDialog()
	{
		final AlertDialog dialog = new AlertDialog.Builder(mContext)
		.setTitle("版本更新")
		.setPositiveButton("下载最新",new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
						String downloadUrl = url_apk+"&versionCode="+update_code+"&versionName="+update_name;
						UpdateApk(downloadUrl);
			}
		})
		.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.setMessage(description+"\n是否更新到最新版本:"+update_name)
		.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	private void showDialog2()
	{
		final AlertDialog dialog = new AlertDialog.Builder(mContext)
		.setTitle("版本提示")
		.setPositiveButton("确定",new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
											
			}
		})
		
		.setMessage(getVersionName()+"已经是最新版本了")
		.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	private boolean comparedVersioninfo(int code,String name)
	{
		return (code>getVersionCode())&&(!getVersionName().equals(name));
	}
	
	private void UpdateApk(String url)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
		mContext.startActivity(intent);
	}

}
