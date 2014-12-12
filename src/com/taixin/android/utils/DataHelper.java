package com.taixin.android.utils;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

/**
 * 
 * @author Joey <zhouye@taixin.cn>
 * 
 *
 */
public class DataHelper {
	public static final String hexString="0123456789abcdef";
	/**
	 * 将16进制的字符串转换成十进制的整数
	 * @param str
	 * @return
	 */
	public static int encode(String str)
	{
		
	/*	byte [] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length*2);
		for(int i=0;i<bytes.length;i++)
		{
			sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
			sb.append(hexString.charAt((bytes[i])&0x0f)>>0);
		}*/
		
		int element = 0;
		for(int i=0;i<str.length();i++)
		{
			StringBuilder sb =new StringBuilder();
			if(str.charAt(i)>='0'&&str.charAt(i)<='9')
				sb.append(str.charAt(i));
			else if(str.charAt(i)=='a'||str.charAt(i)=='A')
				sb.append("10");
			else if(str.charAt(i)=='b'||str.charAt(i)=='B')
				sb.append("11");
			else if(str.charAt(i)=='c'||str.charAt(i)=='C')
				sb.append("12");
			else if(str.charAt(i)=='d'||str.charAt(i)=='D')
				sb.append("13");
			else if(str.charAt(i)=='e'||str.charAt(i)=='E')
				sb.append("14");
			else if(str.charAt(i)=='f'||str.charAt(i)=='F')
				sb.append("15");
			element = element *16+Integer.parseInt(sb.toString());
			
		}
		//return sb.toString();
		return element;
	}
	int ip = 0xAC1208D4;//172.18.8.212
	public static String ChangeIPFormIntToString(int ip)
	{
		Log.i("DataHelper","ChangeIPFormIntToString");
	
		Log.i("DataHelper","ip = "+ip);

		short [] buff = new short[4];
		
		buff[0] =(short) ((short) ip&0xff);
		buff[1] =(short)(ip>>>8);
		buff[1] =(short)((short) buff[1]&0xff);
		buff[2] =(short)(ip>>>16);
		buff[2] =(short)(buff[2]&0xff);
		buff[3] =(short)(ip>>>24);
		buff[3] =(short)(buff[3]&0xff);
		String ipstr = "";
		
		ipstr = buff[3]+"."+buff[2]+"."+buff[1]+"."+buff[0];
		Log.i("DataHelper","ipstr = "+ipstr);
		return ipstr;
	}
	public static Date getCurrentDate()
	{
		try{
			URL url	=new URL("http://www.bjtime.cn");//取得资源对象
		    HttpURLConnection uc=( HttpURLConnection)url.openConnection();//生成连接对象
		    uc.connect(); //发出连接
		    long ld=uc.getDate(); //取得网站日期时间
		    Date date = new Date(ld); //转换为标准时间对象
		    return date;
		}catch(Exception e)
		{
			return null;
		}
		
	}
	public static String getRootSavePath(String pkgname)
	{
		if(pkgname != null)
			Log.d("DataHelper"," pkgname = "+pkgname);
		String path = "/data/data/";
		String SDCard = Environment.getExternalStorageDirectory().getAbsolutePath();
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			path = SDCard+File.separator+pkgname+File.separator;
		else
			path = path + pkgname+File.separator;
		File file = new File(path);
		if(!file.exists())
			file.mkdir();
		Log.d("DataHelper"," rootsavepath = "+path);
		
		return path;
	}
	public static void mkdir(String path)
	{
		File file = new File(path);
		File temp;
		if(!file.exists())
			file.mkdir();
		else
		{
			String [] templist = file.list();
			for(int i=0;i<templist.length;i++)
			{
				if(path.endsWith(File.separator))
					temp = new File(path+templist[i]);
				else
					temp = new File(path+File.separator+templist[i]);
				if(temp.exists())
					temp.delete();
			}
		}
	}
	/**
	* 格式化时长.
	* 
	* @param time 时间单位 S
	* @return 时间格式
	*/
	public static String formatTime(int time) {
	int minute = time / 60;
	int hour = minute / 60;
	int second = time % 60;
	minute %= 60;
	return String.format("%02d:%02d:%02d", hour, minute, second);
	}


}
