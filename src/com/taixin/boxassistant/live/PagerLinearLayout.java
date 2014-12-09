package com.taixin.boxassistant.live;

import com.ryg.slideview.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

public class PagerLinearLayout extends LinearLayout {

	private Context mContext;

	private Scroller mScroller;
	
	private int mFocusIndex;
	
	private ListViewCompat mChannelList;
	private ListView mTopMenuList;
	private ListView mEpgList;

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
		setOrientation(LinearLayout.HORIZONTAL);
		View.inflate(mContext, R.layout.live_menu_layout, this);
		mTopMenuList = (ListView)findViewById(R.id.top_menu_list);
		mChannelList = (ListViewCompat)findViewById(R.id.channel_list);
		mEpgList = (ListView)findViewById(R.id.epg_list);
		mContext = context;
		mScroller = new Scroller(mContext);
	}

	private void smoothScrollTo(int destX, int destY) {
		// 缓慢滚动到指定位置
		int scrollX = getScrollX();
		int delta = destX - scrollX;
		mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
		invalidate();
	}
	/**
	 * 移动到最左边 
	 */
	public void scrollToLeft()
	{
		
	}
	/**
	 * 移动到最右边
	 */
	public void scrollToRight()
	{
		
	}
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}
}
