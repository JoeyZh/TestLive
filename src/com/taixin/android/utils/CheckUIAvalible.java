package com.taixin.android.utils;

import java.util.Timer;
import java.util.TimerTask;

public class CheckUIAvalible {
	
	private static boolean mAvalible = true;
	private static Timer mcheckTimer = new Timer();
	private static TimerTask mCheckTask;
	private final static int DELAY = 600;
	
	
	public static boolean isUIAvalible()
	{
		Log.d("", "mAvalible = "+mAvalible);
		return mAvalible;
	}
	public static void startAvalibleLock()
	{
		mAvalible = false;
		if(mCheckTask != null)
			mCheckTask.cancel();
		mCheckTask = new TimerTask()
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mAvalible = true;
			}
		};
		mcheckTimer.schedule(mCheckTask, DELAY);
	}

}
