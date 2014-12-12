package com.taixin.txlive.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ShareImageView extends ImageView
{
	
	private int index;
	private Context mContext;
	private ResolveInfo mInfo;
	
	private OnClickListener mListener;
	
	private Intent mIntent;
	
	private ViewGroup mLayoutRoot;
	
	public ShareImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	public ShareImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public ShareImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	private void init(Context context)
	{
		mContext = context;
		this.setPadding(10, 10, 10, 10);
		mListener = new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mIntent == null)
					return;
				if(mInfo == null)
					return;
				ComponentName component =  new ComponentName(mInfo.activityInfo.packageName,mInfo.activityInfo.name);
				mIntent.setComponent(component);
				mContext.startActivity(mIntent);
				if(mLayoutRoot != null)
					mLayoutRoot.setVisibility(View.GONE);
			}
			
		};
		setOnClickListener(mListener);
	}
	
	public void initImageView(ResolveInfo info,Intent intent)
	{
		mInfo = info;
		mIntent = intent;
		setImageDrawable(mInfo.loadIcon(mContext.getPackageManager()));
	}
	public void setLayoutRoot(ViewGroup view)
	{
		mLayoutRoot = view;
	}
}

