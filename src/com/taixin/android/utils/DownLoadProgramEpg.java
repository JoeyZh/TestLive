package com.taixin.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;
import android.util.Log;

import com.taixin.android.bean.EPGService;

public class DownLoadProgramEpg implements Runnable{
	
	private final String TAG = "DownLoadProgramEpg";
	
	private static ArrayList<EPGService> EPGList = new ArrayList<EPGService>();
	private int serviceID ;
	private int tsID;
	private static String areaCode = "086080109";
	private final String ipUri = "http://"+Constants.DOWNLOADDEMAIN+":"+Constants.DOWNLOADPORT+"/";/*172.18.8.202:9999/";"http://60.216.97.202:9999/";*/
	
	
	private DownLoadListener listener;
	public DownLoadProgramEpg(int serviceID,int tsID){
		
		this.serviceID = serviceID;
		this.tsID = tsID;
	}
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		Log.i(TAG, "Get EPG content from network");
		Log.i(TAG, "tsID = "+tsID+" serviceID = "+serviceID);
		if (areaCode == ""){
			Log.d(TAG,"鍖哄煙鑾峰彇澶辫触");
			return;
		}
		Log.i(TAG, "areaCode = "+areaCode);
		
		String URLGetEPG = ipUri + "egpManageAction.do?method=queryChannel&tsID="+tsID+"&serviceID="+serviceID+"&version=0&areaCode="+areaCode;
		Log.i("EPG", "URLGetEPG:"+URLGetEPG);
		String ChanelResult = "";
		
		BufferedReader in = null;
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URLGetEPG);
		HttpResponse response;
		
		try {
			response = client.execute(request);
		
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
		
			while ((line = in.readLine()) != null){
				sb.append(line);
			}
			in.close();
		
			ChanelResult = sb.toString();
			if(ChanelResult == null)
			{
				if(listener != null)
					listener.Result(2);
				return;
			}
			Log.i(TAG, "Get EPG String========"+ChanelResult);
			ParseEpgInfoForWeek(ChanelResult);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//缃戠粶璇锋眰澶辫触锛屽垯鎻愰啋鐢ㄦ埛锛岀綉缁滆繛鎺ュ嚭鐜板紓甯�
			if(listener!=null)
				listener.Result(-1);
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ;
	}
	private boolean ParseEpgInfoForWeek(String Json) {
		// TODO Auto-generated method stub
		
		Log.i(TAG, "========ParseEpgInfoForWeek==========");
		String twData= "";
		
		JSONObject jsonObj;
		/**
		 * 鏄剧ず鍔犺浇瀵硅瘽妗�
		 */
		try {
			jsonObj = new JSONObject(Json);
			int version = jsonObj.getInt("version");
			Log.i(TAG, "version = "+version);
			JSONArray jsonArray = jsonObj.getJSONArray("rows");
				
			ArrayList<List<EPGService>> tEPGdatas = new ArrayList<List<EPGService>>();
				
			int i;
			for (i=0; i<7; i++){
				ArrayList<EPGService> wEPGdatas = new ArrayList<EPGService>();
				tEPGdatas.add(wEPGdatas);
			}
				
			int length = jsonArray.length();
			Log.i(TAG, "JsonArray length = "+length);
			i = 0;
			while (i<length){
			//	Log.d(TAG,"i = "+i);
				JSONObject jsonObj2 = jsonArray.getJSONObject(i);
				
				int tsID = jsonObj2.getInt("tsID");
				int serviceID = jsonObj2.getInt("serviceID");
				int eventID = jsonObj2.getInt("eventID");
				String eventName_zh = jsonObj2.getString("eventName_zh");
				jsonObj2.getString("eventName_en");
				String description = jsonObj2.getString("description");
				if (description == "") 
				{
					description = " ";
				}
				String beginTime = jsonObj2.getString("beginTime");
				String[] tmp = beginTime.split(" ");
				String date = tmp[0];
				if (!date.equals(twData)){
					twData = date;
				}
				String time = tmp[1];
				int duration = jsonObj2.getInt("duration");
				jsonObj2.getInt("age");
				jsonObj2.getInt("referenceServiceID");
				EPGService epgService = new EPGService(tsID,serviceID,eventID,eventName_zh,description,date,time,duration);
				Log.i(TAG, "(eventName_zh,description,date,time,duration)"+"("+eventName_zh+","+description+","+date+","+time+","+duration+")");
				EPGList.add(epgService);
				i++;
			}// end while
			if(listener!=null)
				listener.Result(1);
				return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(listener!=null)
				listener.Result(3);
		}
		return true;
	}
	/**
	 * 璁剧疆鍖哄煙鐮�
	 * @param code
	 */
	public static void setAreaCode(String code)
	{
		areaCode = code;
	}
	/**
	 * 鑾峰彇涓�懆EPG
	 * @return 涓�懆EPG鐨勫垪琛ㄤ俊鎭�
	 */
	public static ArrayList<EPGService> getEPGList()
	{
		return EPGList;
	}
	public void setListener(DownLoadListener listener)
	{
		this.listener = listener;
	}
	
	public interface DownLoadListener
	{
		/**
		 * 杩斿洖鐘舵�  
		 * 1 琛ㄧず涓嬭浇鎴愬姛锛孍PG瑙ｆ瀽鎴愬姛
		 * 2 琛ㄧず涓嬭浇鎴愬姛娌℃湁EPG鏁版嵁
		 * 3 琛ㄧず鐜板湪鎴愬姛锛屼絾鏁版嵁寮傚父锛堟湭瑙ｆ瀽鎴愬姛锛�
		 * -1琛ㄧず缃戠粶涓嶅彲鍒拌揪
		 * @param arg0
		 */
		public void Result(int arg0);
	}
}
