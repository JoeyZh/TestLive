package com.taixin.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SaveInstance {
	private static  SaveInstance instance;
	private Context mContext;
	private SharedPreferences preferences;
	private Editor editor;
	private static String name = "mobilrobot";
	
	private SaveInstance(String name,Context context) {
		mContext = context;
		this.name = name;
		preferences = context.getSharedPreferences(name,context.MODE_WORLD_READABLE);
		editor = preferences.edit();
	}
	private SaveInstance(Context context)
	{
		mContext = context;
		preferences = context.getSharedPreferences(name,context.MODE_WORLD_READABLE);
		editor = preferences.edit();
	}
	public static synchronized SaveInstance getInstance(String name,Context context) {
		if (instance == null){
			instance  = new SaveInstance(name,context);
		}
		return instance;
	}
	public static synchronized SaveInstance getInstance(Context context)
	{
		if (instance == null){
			instance  = new SaveInstance(name,context);
		}
		return instance;
	}
	public static synchronized SaveInstance getInstance()
	{
		return instance;
	}
	public void putString(String key,String value)
	{
		editor.putString(key, value);
		editor.commit();
	}
	public void putInt(String key,int value)
	{
		editor.putInt(key, value);
		editor.commit();
	}
	public void putFloat(String key,float value)
	{
		editor.putFloat(key, value);
		editor.commit();
	}
	public void putLong(String key,long value)
	{
		editor.putLong(key, value);
		editor.commit();
	}
	public void putBoolean(String key,boolean value)
	{
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public String getString(String key,String defValue)
	{
		return preferences.getString(key, defValue);
	}
	public int getInt(String key,int defValue)
	{
		return preferences.getInt(key, defValue);
	}
	public Float getFloat(String key,Float defValue)
	{
		return preferences.getFloat(key, defValue);
	}
	public Long getLong(String key,Long defValue)
	{
		return preferences.getLong(key, defValue);
	}
	public boolean getBoolean(String key,boolean defValue)
	{
		return preferences.getBoolean(key, defValue);
	}

}
