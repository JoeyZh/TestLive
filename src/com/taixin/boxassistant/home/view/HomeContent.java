package com.taixin.boxassistant.home.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taixin.android.utils.Log;
import com.taixin.boxassistant.live.LivePlayerMain;
import com.taixin.test.R;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HomeContent extends RelativeLayout{
	
	private Context mContext;
	
	private GridView mGridView;
	private RelativeLayout mTitleBarLayout;
	private ImageButton mHomeMenuBtn;
	private RelativeLayout mMatchLayout;
	private FrameLayout mSurfaceParent;
	private SmallVideoView mVideoSurface;
	private ArrayList<HashMap<String,Object>> mItemMap = new ArrayList<HashMap<String,Object>>();
	
	/**
	 * 触屏或按键
	 */
	private final int MODE_TOUCH_AND_KEY_PRESS = 0;
	/**
	 * 居家安全
	 */
	private final int MODE_HOME_SAFETY = 1;
	/**
	 *大屏共享 
	 */
	private final int MODE_SCREEN_SHARE = 2;
	/**
	 * 应用 推荐
	 */
	private final int APP_RECOMMENDATION = 3;
	
	private OnStatusListener mOnStatusListener;
	

	public HomeContent(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public HomeContent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public HomeContent(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context)
	{
		Log.i("","init");
		mContext = context;
	    View.inflate(mContext, R.layout.home_content_layout, this);
	    mTitleBarLayout = (RelativeLayout)findViewById(R.id.home_content_title_layout);
	    mHomeMenuBtn = (ImageButton)findViewById(R.id.home_content_menu_btn);
	    mHomeMenuBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("","");
				if(mOnStatusListener !=null)
					mOnStatusListener.onHide();
			}
		});
	    mGridView = (GridView)findViewById(R.id.home_content_gridview);
	    mSurfaceParent = new HomeContentSurface(mContext);
	    ((ViewGroup)mGridView.getParent()).addView(mSurfaceParent, 0);
	    mVideoSurface = new SmallVideoView(mContext);
	    mSurfaceParent.addView(mVideoSurface);
	    mVideoSurface.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, LivePlayerMain.class);
				mContext.startActivity(intent);
				onDetach();
			}
		});
	    mMatchLayout = (RelativeLayout)findViewById(R.id.home_content_match_layout);
	    
	    mGridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				onDetach();
				switch(arg2)
				{
				/**
				 * 触屏、按键
				 */
				case MODE_TOUCH_AND_KEY_PRESS:
					break;
				/**
				 * 居家安全
				 */
				case MODE_HOME_SAFETY:
					break;
				/**
				 * 大屏共享
				 */
				case MODE_SCREEN_SHARE:
					break;
				/**
				 * 应用推荐
				 */
				case APP_RECOMMENDATION:
					break;
					default:
				}
			}});
	}
	
	public void setOnStatusListener(OnStatusListener l)
	{
		mOnStatusListener = l;
	}
	public void onDetach()
	{
		mVideoSurface.stop();
	}
	public void onAttach()
	{
		mVideoSurface.play();
	}
	/**
	 * 根据条件初始化布局
	 */
	public void initContentItem(String support[])
	{
		String from[] = {"img","title"};
		int to[] = {R.id.home_content_item_image,R.id.home_content_item_title};
		String test[] = {"触屏/按键","居家安全","大屏共享","应用推荐"};
		mItemMap.clear();
		for(int i=0;i<4;i++)
		{
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put(from[0],R.drawable.recorded_normal);
			map.put(from[1], test[i]);
			mItemMap.add(map);
		}
		GridViewAdapter adapter = new GridViewAdapter(mContext, mItemMap, R.layout.home_content_item, from, to);
		mGridView.setAdapter(adapter);
	}
	
	private class GridViewAdapter extends SimpleAdapter
	{
		
		private String from[];
		private int to[];
		private List<? extends Map<String, ?>> data;
		
		public GridViewAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
			this.data = data;
			this.from = from;
			this.to = to;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HomeContentItem view = (HomeContentItem)convertView;
			if(view == null)
			{
				view = new HomeContentItem(mContext);
			}
			Map map = data.get(position);
			for(int i = 0;i<to.length;i++)
			{
				View current = view.findViewById(to[i]);
				if(from.length<=i)
					break;
				if(current instanceof TextView)
					((TextView)current).setText((String)map.get(from[i]));
				else if(current instanceof ImageView)
					((ImageView)current).setImageResource((Integer)map.get(from[i]));
					
			}
			return view;
		}
		
	}
	public interface OnStatusListener
	{
		void onHide();
		void onShow();
	}
}
