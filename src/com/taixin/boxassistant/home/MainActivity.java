package com.taixin.boxassistant.home;

import com.taixin.android.activity.RootActivity;
import com.taixin.android.utils.Constants;
import com.taixin.android.utils.Log;
import com.taixin.android.utils.UpdateThread;
import com.taixin.boxassistant.home.adapter.MenuAdapter;
import com.taixin.boxassistant.home.view.HomeContent;
import com.taixin.boxassistant.home.view.HomeContent.OnStatusListener;
import com.taixin.test.R;
import com.taixin.txlive.slidemenu.SlideMenu;
import com.taixin.txlive.slidemenu.SlideMenu.OnSlideStateChangeListener;
import com.taixin.txlive.widget.ShareLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
/**
 *<dl>
 * <dt>MainActivity.java</dt>
 * <dt>应用主界面</dt>
 * <dt>1.左侧工具栏定义</dt>
 * <dt>2.二级界面调转</dt>
 * <dt>3.语音控制</dt>
 * <dt>Copyright: </dt>
 * <dd>Copyright (C) 2014</dd>
 * <dt>Company: </dt>
 * <dd>山东泰信电子股份有限公司</dd>
 * <dt>CreateDate: </dt>
 * <dd>2014-12-11</dd>
 * </dl>
 * @author Joey
 *
 */
public class MainActivity extends Activity implements OnSlideStateChangeListener,OnStatusListener{
	
	private SlideMenu mSlideMenu;
	
	private HomeContent mHomeContent;
	
	private ListView mSlideList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		initContentView();
		checkoutUpdate();
		upLoadInfo();
	}

	private void initContentView()
	{
		mSlideMenu = (SlideMenu)findViewById(R.id.slide_menu_root);
		mSlideMenu.setOnSlideStateChangeListener(this);
		mHomeContent =(HomeContent) findViewById(R.id.home_content);
		mHomeContent.initContentItem(null);
		mHomeContent.setOnStatusListener(this);
		
		mSlideList = (ListView)findViewById(R.id.slide_menu_list);
		String[] mMenuString = getResources().getStringArray(R.array.menu_list);
		Log.d("", "mMenuString length = " + mMenuString.length);
		int[] resources = { R.drawable.recorded_normal, R.drawable.recorded_normal,
				R.drawable.recorded_normal, R.drawable.recorded_normal };
		mSlideList.setAdapter(new MenuAdapter(this, mMenuString, resources));
		mSlideList.setOnItemClickListener(new MenuListItemClickListener());
	}
	/**
	 * 
	 */
	private void checkoutUpdate()
	{
		/**
		 * 检查更新
		 */
		UpdateThread mUpdateThread = new UpdateThread(MainActivity.this,
				Constants.UPDATE_APK_URL + getPackageName(), false);
		if (!isFinishing()) {
			Thread thread = new Thread(mUpdateThread);
			thread.start();
		}
	}
	/**
	 * 上传信息
	 */
	private void upLoadInfo()
	{
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		RootActivity.clean();
	}
	@Override
	public void onSlideStateChange(int slideState) {
		// TODO Auto-generated method stub
		switch (slideState) {
		case SlideMenu.STATE_OPEN_RIGHT:
			mHomeContent.onDetach();
			break;
		case SlideMenu.STATE_OPEN_LEFT:
			break;
		case SlideMenu.STATE_CLOSE:
			mHomeContent.onAttach();
			break;
		}
	}

	@Override
	public void onSlideOffsetChange(float offsetPercent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHide() {
		// TODO Auto-generated method stub
		mSlideMenu.open(false, true);
	}
	
	@Override
	public void onShow() {
		// TODO Auto-generated method stub
		
	}
	
	private final int SHARED = 0;
	private final int HELP = 1;
	private final int INFO_FEEDBACK = 2;
	private final int CHECKOUT = 3;
	/**
	 * 提醒对话框，用于menulist的布局中
	 */
	private AlertDialog mDialog;

	/**
	 * This list item click listener implements very simple view switching by
	 * changing the primary content text. The drawer is closed when a selection
	 * is made.
	 */
	private class MenuListItemClickListener implements
	ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (mDialog != null && mDialog.isShowing())
				mDialog.cancel();
			switch (arg2) {
			case SHARED:
				ShareLayout shareLayout = new ShareLayout(MainActivity.this);
				shareLayout.initShareList();
				shareLayout.show();
				mDialog = new AlertDialog.Builder(MainActivity.this).setView(
						shareLayout).create();
				mDialog.show();
				break;
			case HELP:
				break;
			case INFO_FEEDBACK:
				break;
			case CHECKOUT:
				UpdateThread mUpdateThread = new UpdateThread(MainActivity.this,
						Constants.UPDATE_APK_URL + getPackageName(), true);
				if (!isFinishing()) {
					Thread thread = new Thread(mUpdateThread);
					thread.start();
				}
				break;

			default:
				break;
			}
		}
	}
	
}
