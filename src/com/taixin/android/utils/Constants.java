package com.taixin.android.utils;

public class Constants {
	
	public static final boolean DEBUG = true;

	public static final String DOWNLOADDEMAIN = /*"172.18.8.202";*/"60.216.97.202";
	public static final int DOWNLOADPORT = 9999;
	public static final String TESTDEMAIN = /*"172.18.8.200";*/"60.216.97.200";
	public static final String UPDATE_APK_URL = "http://"
			+((DEBUG == true)?Constants.TESTDEMAIN:Constants.DOWNLOADDEMAIN) 
			+":"+DOWNLOADPORT
			+"/app/appAction.do?method=queryVersion&packageName=";
	
	public final static String SERVERDOMAIN = "of.taixin.cn";
	public final static String SERVERHOST = /*"172.18.8.205";*/"60.216.97.205";
	public final static int PORT = 5222;
	public final static String IP_URL = "http://"
			+ ((Constants.DEBUG == true) ? Constants.TESTDEMAIN
					: Constants.DOWNLOADDEMAIN) + ":" + Constants.DOWNLOADPORT
			+ "/";/* "http://172.18.8.202:9999/"; */;
	public static String DEFAULE_USERNAME="defaultusername";
	public static String DEFAULE_PASSWORD="defaultpassword";
}
