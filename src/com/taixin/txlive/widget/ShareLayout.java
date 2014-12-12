package com.taixin.txlive.widget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.taixin.android.utils.DataHelper;
import com.taixin.android.utils.Log;
import com.taixin.test.R;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * 
 * 分享布局，用于手机遥控器分享到新浪、腾讯等等的添加
 * @author Joey
 *
 */
public class ShareLayout extends FrameLayout{

	
	private Context mContext;
	private View mRoot;
	private LinearLayout mListParent;
	private List<ResolveInfo> mAppsList;
	
	private boolean isShowing;
	
	public ShareLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	
	public ShareLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public ShareLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	
	private void initView(Context context)
	{
		mContext = context;;
		LayoutInflater inflater  = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		mRoot = inflater.inflate(R.layout.share_layout, this);
		mListParent =(LinearLayout)mRoot.findViewById(R.id.share_list_root);
		InputStream in;
		try {
			in = getResources().getAssets().open("two_code.png");
			Bitmap bmp = BitmapFactory.decodeStream(in);
			((ImageView)findViewById(R.id.share_image_view)).setImageBitmap(bmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void initShareList()
	{
		mAppsList = new ArrayList<ResolveInfo>();
    	Intent intent = new Intent(Intent.ACTION_SEND,null);
    	intent.addCategory(Intent.CATEGORY_DEFAULT);
    	intent.setType("image/*");
    	DataHelper.mkdir(DataHelper.getRootSavePath(mContext.getPackageName()));
		try {
			InputStream in = getResources().getAssets().open("two_code.png");
			File file = new File(DataHelper.getRootSavePath(mContext.getPackageName())+"tmp");
			FileOutputStream fileoutput = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			while(in.read(buffer)>0)
			{
				fileoutput.write(buffer);
			}
			in.close();
			fileoutput.close();
			Uri uri = Uri.fromFile(file);
			intent.putExtra(Intent.EXTRA_STREAM,uri);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	intent.putExtra(Intent.EXTRA_SUBJECT, "一个测试demo");
		intent.putExtra(Intent.EXTRA_TEXT, "我正在使用泰信手机遥控器，支持手机录制，再也不用担心错过电视节目啦！扫描下方的二维码即可下载");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	PackageManager pm = mContext.getPackageManager();
    	mAppsList = pm.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    	Log.i(""," list size = "+mAppsList.size());
    	for(int i= 0;i<mAppsList.size();i++)
    	{
    		if(mAppsList.get(i).activityInfo.packageName.contains("com.android"))
    			continue;
    		ShareImageView image = new ShareImageView(mContext);
    		image.initImageView(mAppsList.get(i), intent);
    		mListParent.addView(image);
    		image.setLayoutRoot((ViewGroup)mRoot);
    	}
    	isShowing = false;
    	mRoot.setVisibility(View.GONE);
	}
	
	public boolean isOnShowing()
	{
		return isShowing;
	}
	
	public void cancel()
	{
		if(!isShowing)
			return;
		isShowing = false;
    	mRoot.setVisibility(View.GONE);
	}
	
	public void show()
	{
		if(mAppsList == null||mAppsList.size()==0)
			return;
		if(isShowing)
			return;
		isShowing = true;
    	mRoot.setVisibility(View.VISIBLE);
	}
	
}
