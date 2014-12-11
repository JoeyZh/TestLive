package com.taixin.boxassistant.home.view;

import com.taixin.android.utils.Log;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class HomeContentSurface extends FrameLayout{

	public HomeContentSurface(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		measuredSelf();
	}

	public HomeContentSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		measuredSelf();
	}

	public HomeContentSurface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		measuredSelf();
	}
	
	private void measuredSelf()
	{
		Context context = getContext();
		if(context == null)
		{
			Log.i("", "context == null");
			return;
		}
		if(context instanceof Activity)
		{
			Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
			int width = display.getWidth();
			int height = width/16*9;
			Log.i("", "(width,height) = ("+width+","+height+")");
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
			setLayoutParams(params);
		}
		
	}

}