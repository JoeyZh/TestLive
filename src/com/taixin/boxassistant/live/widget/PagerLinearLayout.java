package com.taixin.boxassistant.live.widget;

import java.util.ArrayList;
import java.util.HashMap;

import com.taixin.android.utils.Log;
import com.taixin.boxassistant.live.adapter.ChannelAdapter;
import com.taixin.boxassistant.live.widget.SlideView.OnSlideListener;
import com.taixin.test.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PagerLinearLayout extends LinearLayout implements OnSlideListener,
		OnItemClickListener,View.OnClickListener {

	private Context mContext;

	private Scroller mScroller;

	private int mLeftIndex;

	private ListViewCompat mChannelList;
	private ListView mTopMenuList;
	private ListView mEpgList;
	private SlideView mLastSlideViewWithStatusOn;

	private ChannelAdapter mChannelAdapter;
	private ArrayList<HashMap<String, Object>> mChannelMap = new ArrayList<HashMap<String, Object>>();
	String testChannelName[] = { "山东卫视", "中央-5高清", "湖南卫视", "江苏卫视", "东方卫视",
			"浙江卫视", "黑龙江卫视", "云南卫视高清" };

	private SimpleAdapter mTopMenuAdapter;
	private ArrayList<HashMap<String, Object>> mTopMenuMap = new ArrayList<HashMap<String, Object>>();
	private SimpleAdapter mEpgAdapter;
	private ArrayList<HashMap<String, Object>> mEpgMap = new ArrayList<HashMap<String, Object>>();
	
	
	public PagerLinearLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public PagerLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public PagerLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	private void initView(Context context) {
		mContext = context;
		// LayoutInflater inflater = ((LayoutInflater)
		// mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		View.inflate(mContext, R.layout.live_menu_layout, this);
		setOrientation(LinearLayout.HORIZONTAL);
		mTopMenuList = (ListView) findViewById(R.id.top_menu_list);
		mChannelList = (ListViewCompat) findViewById(R.id.channel_list);
		mEpgList = (ListView) findViewById(R.id.epg_list);

		mScroller = new Scroller(mContext);

		String from[] = { "channel_logic_num", "channel_name", "channel_epg" };
		int to[] = { R.id.channel_logicnum, R.id.channel_name, R.id.channel_epg };
		for (int i = 0; i < testChannelName.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(from[0], "" + 0 + i);
			map.put(from[1], testChannelName[i]);
			map.put(from[2], "电视剧:战神 10：30-11：45");
			mChannelMap.add(map);
		}
		mChannelAdapter = new ChannelAdapter(mContext, mChannelMap,
				R.layout.slide_view_merge, from, to);
		mChannelList.setAdapter(mChannelAdapter);
		mChannelList.setOnItemClickListener(this);
		mChannelAdapter.setOnSlideListenter(this);
		mChannelAdapter.setOnClickListener(this);

		String from1[] = { "menu_tag", "menu_text" };
		int[] to1 = { R.id.top_menu_tag, R.id.top_menu_text };
		for (int i = 0; i < 7; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(from1[0], R.drawable.recorded_normal);
			map.put(from1[1], "我的频道");
			mTopMenuMap.add(map);
		}
		mTopMenuAdapter = new SimpleAdapter(mContext, mTopMenuMap,R.layout.top_menu_item, from1, to1);
		mTopMenuList.setAdapter(mTopMenuAdapter);
		for(int i=0;i<20;i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(from1[0], R.drawable.recorded_normal);
			map.put(from1[1], "19:00  电视剧:战神 10：30-11：45 -------------");
			mEpgMap.add(map);
		}
		mEpgAdapter = new SimpleAdapter(mContext, mEpgMap,R.layout.top_menu_item, from1, to1);
		mEpgList.setAdapter(mEpgAdapter);
		setBackgroundColor(Color.TRANSPARENT);
	}

	private void smoothScrollTo(int destX, int destY) {
		// 缓慢滚动到指定位置
		int scrollX = getScrollX();
		int delta = destX - scrollX;
		mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
		invalidate();
	}

	/**
	 * 移动到最右边
	 */
	public void scrollToRight() {
		if (mLeftIndex != 0) {
			int distance = mTopMenuList.getWidth();
			int scrollX = getScrollX();
			Log.i("","distance = "+distance);
			mScroller.startScroll(scrollX, 0, -distance, 0,
					Math.abs(distance));
			mEpgList.setVisibility(View.INVISIBLE);
			invalidate();
		} else
			return;
		mLeftIndex = 0;
	}

	/**
	 * 移动到最左边
	 */
	public void scrollToLeft() {
		if (mLeftIndex != 1) {
			int distance = mTopMenuList.getWidth();
			Log.i("","distance = "+distance);
			int scrollX = getScrollX();
			mScroller.startScroll(scrollX, 0, distance, 0,
					Math.abs(distance));
			mEpgList.setVisibility(View.VISIBLE);
			invalidate();
		} 
		else
		{
			return;
		}
		
		mLeftIndex = 1;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	@Override
	public void onSlide(View view, int status) {
		if (mLastSlideViewWithStatusOn != null
				&& mLastSlideViewWithStatusOn != view) {
			mLastSlideViewWithStatusOn.shrink();
		}

		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (SlideView) view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("", "onItemClick position=" + position);
		SlideView slideView =(SlideView) parent.getChildAt(position);
		if(slideView == null)
			return;
		if (slideView.ismIsMoveClick()) {// 如果是滑动中触发的点击事件，不做处理
			return;
		}
		if (slideView.close() && slideView.getScrollX() == 0) {
			if (mLastSlideViewWithStatusOn == null
					|| mLastSlideViewWithStatusOn.getScrollX() == 0) {
				// 此处添加item的点击事件
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.i("","onClick ");
		int id = v.getId();
		switch (id) {
		case R.id.epg_view:
			if(mLastSlideViewWithStatusOn != null)
				mLastSlideViewWithStatusOn.close();
			scrollToLeft();
			break;
		case R.id.fav_view:
			if(mLastSlideViewWithStatusOn != null)
				mLastSlideViewWithStatusOn.close();
			scrollToRight();
		default:
			break;
		}
	}
	
	
}
