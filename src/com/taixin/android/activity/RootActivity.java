package com.taixin.android.activity;

import java.util.ArrayList;
import java.util.Stack;

import com.taixin.android.utils.Log;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
/**
 *<dl>
 * <dt>RootActivity.java</dt>
 * <dt>根Activity</dt>
 * <dt>Copyright: </dt>
 * <dd>Copyright (C) 2014</dd>
 * <dt>Company: </dt>
 * <dd>山东泰信电子股份有限公司</dd>
 * <dt>CreateDate: </dt>
 * <dd>2014-03-18</dd>
 * </dl>
 * @author zhouye
 *
 */
public class RootActivity extends Activity{
	private static Stack<Activity> mActivityStack = new Stack<Activity>();;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActivityStack.push(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(!mActivityStack.isEmpty())
			mActivityStack.pop();
	}
	/**
	 * 清除堆栈中的Activity
	 */
	public static void clean()
	{
		while(!mActivityStack.isEmpty())
		{
			mActivityStack.pop().finish();
		}
	}
}
