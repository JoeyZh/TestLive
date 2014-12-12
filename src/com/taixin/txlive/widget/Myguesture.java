package com.taixin.txlive.widget;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class Myguesture extends SimpleOnGestureListener{
	
	boolean directionY;
	boolean directionX;
	private Activity mController;
	private MyguestureListener listener;
	public void setGuestureListener(MyguestureListener l)
	{
		listener = l;
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Log.i("","onFling x= "+velocityX+", y="+velocityY);
		float mOldX = e1.getX();
    	float x=e2.getRawX();
//    	if(directionX){
//    		directionY=false;
//    		if(x-mOldX>10){
//    			//TODO 频道加
//    			if(listener!=null)
//    			{
//    				listener.onFling(x-mOldX);
//    			}
//    			Log.i("", "频道加");
//    		}else if(x-mOldX<-10){
//    			if(listener!=null)
//    			{
//    				listener.onFling(x-mOldX);
//    			}
//    			//TODO 频道减
//    			Log.i("", "频道减");
//    		}
//    	}
		return super.onFling(e1, e2, velocityX, velocityY);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2,
			float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		Log.i("","onScroll x= "+distanceX+", y="+distanceY);
		float mOldY = e1.getY();
    	float y=e2.getRawY();
    	float mOldX = e1.getX();
    	float x=e2.getRawX();
    	if(Math.abs(distanceX)<Math.abs(distanceY))
    	{
    		directionX = false;
    		directionY = true;
    	}
    	else
    	{
    		directionX = true;
    		directionY = false;
    		if(x-mOldX>10){
    			if(listener!=null)
    			{
    				listener.onFling(x-mOldX);
    			}
    			Log.i("", "频道加");
    		}else if(x-mOldX<-10){
    			if(listener!=null)
    			{
    				listener.onFling(x-mOldX);
    			}
    			Log.i("", "频道减");
    		}
    		return true;
    	}
    	if(y-mOldY>10||y-mOldY<-10){
    		if(listener!=null)
    			listener.onScroll(mOldY-y);
    	}
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
	/**
	 * 手势控制
	 * @author Joey
	 *
	 */
	public interface MyguestureListener
	{
		/**
		 * 上下滑动
		 * @param distance 相对位移,向上>0,向下<0
		 * 
		 */
		void onScroll(float distance);
		/**
		 * 左右滑动
		 * @param distance 相对位移，向右>0,向左<0
		 */
		void onFling(float distance);
	}
	
}
