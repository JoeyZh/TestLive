package com.taixin.txlive.tvremote.widget;

//import com.taixin.txlive.protocol.CommandSender;
import com.taixin.test.R;
import com.taixin.txlive.tvremote.widget.TvRemoteTouchView.MouseGesture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.FrameLayout;
import android.widget.TextView;
/**
 * create a layout
 * contains key events and direction gestures
 * @author Joey
 * @date 2014-08-18
 *
 */

public class TvRemoteLayout extends FrameLayout {

	private ImageButton mHomeBtn;
	private ImageButton mBackBtn;
	private ImageButton mMenuBtn;
	private ImageButton mShutDownBtn;
	private ImageButton mVoiceMuteBtn;
	private ImageButton mVoiceUpBtn;
	private ImageButton mVoiceDownBtn;
	private ImageButton mPvrBtn;

	private TvRemoteTouchView mTouchView;

	private View root;
	private Context mContext;

	private TextView showTextView;
//	private CommandSender mSender;
	
	AlertDialog mShutdownDialog; 
	private ShutDownCallback mShutDownCallback;

	public TvRemoteLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		initView();

	}

	public TvRemoteLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		initView();
	}
	public void setShutDownCallback(ShutDownCallback callback)
	{
		mShutDownCallback = callback;
	}
	/**
	 * 
	 */
	private void initView() {
		LayoutInflater inflater = ((LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		root = inflater.inflate(R.layout.remote_layout, this);

		mHomeBtn = (ImageButton) root.findViewById(R.id.remote_img_btn_home);
		mBackBtn = (ImageButton) root.findViewById(R.id.remote_img_btn_back);
		mMenuBtn = (ImageButton) root.findViewById(R.id.remote_img_btn_menu);
		mShutDownBtn = (ImageButton) root
				.findViewById(R.id.remote_img_btn_shutdown);
		mVoiceMuteBtn = (ImageButton) root
				.findViewById(R.id.remote_img_btn_mute);
		mVoiceUpBtn = (ImageButton) root
				.findViewById(R.id.remote_img_btn_voiceup);
		mVoiceDownBtn = (ImageButton) root
				.findViewById(R.id.remote_img_btn_voicedown);
		mPvrBtn = (ImageButton)root.findViewById(R.id.remote_img_btn_pvr);
		mTouchView = (TvRemoteTouchView) root.findViewById(R.id.remote_touch_view);
		showTextView = (TextView) root.findViewById(R.id.remote_show_text);

		mHomeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_HOME);
				showTextView.setText("click the Home button ");

			}
		});
		mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_BACK);
				showTextView.setText("click the Back button ");
			}
		});
		mMenuBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_MENU);
				showTextView.setText("click the Menu button ");
			}
		});
		mShutDownBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mShutdownDialog == null)
				{
					initDialog();
				}
				if(mShutdownDialog.isShowing())
					mShutdownDialog.cancel();
				mShutdownDialog.show();
				showTextView.setText("click the Shutdown button ");

			}
		});
		mVoiceMuteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (mSender != null)
//				{
//					mSender.Key(KeyEvent.KEYCODE_MUTE);
//				}
				showTextView.setText("click the KEYCODE_VOLUME_MUTE button ");

			}
		});
		mVoiceUpBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_VOLUME_UP);
				showTextView.setText("click the KEYCODE_VOLUME_UP button ");

			}
		});
		mVoiceDownBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_VOLUME_DOWN);
				showTextView.setText("click the KEYCODE_VOLUME_DOWN button ");

			}
		});
		mPvrBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(TvKeyEvent.KEYCODE_TX_RECORD);
				showTextView.setText("click the Pvr button ");

			}
		});
		mTouchView.setOnGestureListener(new MouseGesture() {

			@Override
			public void gestureLeft() {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_DPAD_LEFT);
				showTextView.setText("guseture Left ");
			}

			@Override
			public void gestureRight() {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_DPAD_RIGHT);
				showTextView.setText("guseture Right ");

			}

			@Override
			public void gestureDown() {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_DPAD_DOWN);
				showTextView.setText("guseture Down ");

			}

			@Override
			public void gestureUp() {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_DPAD_UP);
				showTextView.setText("guseture Up ");

			}

			@Override
			public void gestureClick() {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_DPAD_CENTER);
				showTextView.setText("guseture click ");

			}
		});
		initDialog();
	}
	
	private void initDialog()
	{
		mShutdownDialog = new AlertDialog.Builder(mContext,AlertDialog.THEME_HOLO_DARK)
		.setTitle("待机提示")
		.setMessage("您点击了待机键，请亲再次确认，是否关闭您的机顶盒,机顶盒待机之后，您将暂时不能使用手机遥控器，手机遥控器将会自动退出")
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
//				if (mSender != null)
//					mSender.Key(KeyEvent.KEYCODE_POWER);
				if(mShutDownCallback!=null)
					mShutDownCallback.OnShutDown();
			}
		})
		.setNegativeButton(android.R.string.cancel,null)
		.create();
	}
//	public void setCommandSender(CommandSender sender) {
//		mSender = sender;
//	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return mTouchView.onTouchEvent(event);
	}
	public interface ShutDownCallback
	{
		void OnShutDown();
	}

}
