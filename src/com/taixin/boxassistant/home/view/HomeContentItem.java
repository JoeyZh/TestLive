package com.taixin.boxassistant.home.view;

import com.taixin.android.utils.Log;
import com.taixin.test.R;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeContentItem extends RelativeLayout{
	
	public HomeContentItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void measuredSelf(Context context)
	{
		if(context == null)
		{
			Log.i("", "context == null");
			return ;
		}
		if(context instanceof Activity)
		{
			Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
			int width = display.getWidth();
			
			int topHeight = context.getResources().getDimensionPixelSize(R.dimen.status_layout_height);
			int height = display.getHeight() - width *9/16 - 2*topHeight;
			Log.i("", "(width,height) = ("+width/2+","+height/2+")");
			int padding = context.getResources().getDimensionPixelSize(R.dimen.home_content_item_padding);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(width/2-padding*2, height/2-padding);
			setLayoutParams(params);
			setPadding(padding, padding, padding, padding);
		}
	}
	
	private void init()
	{
		Context context = getContext();
		View.inflate(context, R.layout.home_content_item, this);
		measuredSelf(context);
		setBackgroundColor(Color.BLUE);
	}

}
