package com.taixin.android.utils;

public class Log {
	private final static String TAG = "Log";
	public static boolean isDebug=Constants.DEBUG;
	public static void e(String tag,String msg,Exception e)
	{
		if(!isDebug){
			return;
		}
		StackTraceElement invoke=getInvoker();
		android.util.Log.e(invoke.getClassName(),"【"+invoke.getMethodName()+":"+invoke.getLineNumber()+"】"+ msg);
	}
	public static void i(String tag,String msg)
	{	if(!isDebug){
		return;
	}
		StackTraceElement invoke=getInvoker();
		android.util.Log.i(invoke.getClassName(),"【"+invoke.getMethodName()+":"+invoke.getLineNumber()+"】"+ msg);
	}
	public static void v(String tag,String msg)
	{
		if(!isDebug){
			return;
		}
		StackTraceElement invoke=getInvoker();
		android.util.Log.v(invoke.getClassName(),"【"+invoke.getMethodName()+":"+invoke.getLineNumber()+"】"+ msg);
	}
	public static void d(String tag,String msg)
	{
		if(!isDebug){
			return;
		}
		StackTraceElement invoke=getInvoker();
		android.util.Log.d(invoke.getClassName(),"【"+invoke.getMethodName()+":"+invoke.getLineNumber()+"】"+ msg);
	}
	public static void w(String tag,String msg)
	{
		if(!isDebug){
			return;
		}
		StackTraceElement invoke=getInvoker();
		android.util.Log.w(invoke.getClassName(),"【"+invoke.getMethodName()+":"+invoke.getLineNumber()+"】"+ msg);
	}
	public static void e(String tag,String msg)
	{
		if(!isDebug){
			return;
		}
		StackTraceElement invoke=getInvoker();
		android.util.Log.e(invoke.getClassName(),"【"+invoke.getMethodName()+":"+invoke.getLineNumber()+"】"+ msg);
	}
	private static StackTraceElement getInvoker(){
		return Thread.currentThread().getStackTrace()[4];
	}

}
