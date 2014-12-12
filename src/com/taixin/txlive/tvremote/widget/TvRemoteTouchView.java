package com.taixin.txlive.tvremote.widget;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.taixin.android.utils.Log;
import com.taixin.test.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * @author Joey
 * A widget that imitates a TvRemoteTouchView that would float on top of the UI. TvRemoteTouchView
 * is being simulated as a touch area that recognizes slide gestures or taps
 * for OK. 
 * <p>
 * Make sure you set up a {@link MouseGesture} to handle the events.
 * <p>
 * To position the dpad on the screen, use {@code paddingTop} or
 * {@code PaddingBottom}. If you use {@code PaddingBottom}, the widget will be
 * aligned on the bottom of the screen minus the padding.
 * 
 */
@TargetApi(11)
public class TvRemoteTouchView extends ImageView {

	private Paint mPaint;

	/**
	 * 标记手势类型
	 * mark gesture types
	 */
	private GestureStatus mStatus;
	/***
	 * mouse arrow action 
	 * contains left, right, up, down, click
	 *  
	 */
	private MouseGesture mMouseGesture;

	private boolean isTouchViewonFocus;

	private Context mContext;
	private static final double TAN_DIRECTION = Math.tan(Math.PI / 4);
	/**
	 * touch down coordinator
	 */
	private float mOriginTouchX;
	private float mOriginTouchY;
	/**
	 * animation center coordinator
	 */
	float centerX = getMeasuredWidth()/2;
	float centerY = getMeasuredHeight()/2;
	/**
	 * 轨迹
	 */
	Path mPath;
	
	private int mImageResouceId;
	
	/**
	 * Vibrator
	 */
	private final Vibrator vibrator;
	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */

	public TvRemoteTouchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		init();
	}

	public TvRemoteTouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		init();
	}

	public TvRemoteTouchView(Context context) {
		super(context);
		mContext = context;
		vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		mPaint = new Paint();
		/**设置颜色*/
		mPaint.setColor(Color.BLUE);
		/**设置画笔抗锯齿*/
		mPaint.setAntiAlias(true);
		/**设置画笔类型*/
		mPaint.setStyle(Paint.Style.STROKE);
		/**设置画笔为圆滑*/
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		/**设置画笔宽度*/
		mPaint.setStrokeWidth(5);
		
		mPath = new Path();
		mStatus = GestureStatus.IDLE;
		isTouchViewonFocus = false;
	}

	float currentX = 0;
	float currentY = 0;
	float distanceX;
	float distanceY;

	@SuppressLint({ "NewApi", "NewApi" })
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = getX();
		float y = getY();
		
		Log.i("", "x= "+x+", y = "+y);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mPath.moveTo(x,y);
			handleDownEvent(event.getX(), event.getY());
			break;
		case MotionEvent.ACTION_MOVE:
			if(isTouchViewonFocus)
				handleMoveEvent(event.getX(), event.getY());
			mPath.quadTo(currentX, currentY, x, y);
		
			return true;
		case MotionEvent.ACTION_UP:
			if(isTouchViewonFocus)
			{
				handleUpEvent(event.getX(),event.getY());
				
			}
			mPath.reset();
			break;
		}
		return super.onTouchEvent(event);

	}

	/**
	 * 处理Touch Down事件
	 * 
	 * @param x
	 * @param y
	 */
	private void handleDownEvent(float x, float y) {
		mOriginTouchX = x;
		mOriginTouchY = y;
		Log.i("", "ACTION_DOWN mOriginTouchX = " + mOriginTouchX
				+ ",mOriginTouchY = " + mOriginTouchY);
		mStatus = GestureStatus.IDLE;
		isTouchViewonFocus = true;
	}

	/**
	 * 处理Touch Move事件
	 * 
	 * @param x
	 * @param y
	 */
	private void handleMoveEvent(float x, float y) {
		currentX = x;
		currentY = y;
		float dx = x - mOriginTouchX;
		float dy = y - mOriginTouchY;
		distanceX = dx;
		distanceY = dy;
		GestureStatus status = getgesture(dx, dy);
		setBackgroundDrawable(null);
		Log.i("","status = "+status+System.currentTimeMillis());
		if (status.isMove && !mStatus.isMove) {
			sendEvent(status, true, true);
			mStatus = status;
		}
	}

	private void handleUpEvent(float x, float y) {
		boolean playSound = true;
		handleMoveEvent(x, y);
		if (mStatus.isMove) {
			sendEvent(mStatus, false, playSound);
		} else {
			onCenterAction();
		}
		center();
	}

	private GestureStatus getgesture(float dx, float dy) {
		if ((dx * dx + dy * dy) < 50)
			return GestureStatus.Center;
		if (dx == 0) {
			if (dy > 0) {
				return GestureStatus.Down;
			} else {
				return GestureStatus.Up;
			}
		}
		if (dy == 0) {
			if (dx > 0) {
				return GestureStatus.Right;
			} else {
				return GestureStatus.Left;
			}
		}
		float ratioX = (float) (dy) / (float) (dx);
		float ratioY = (float) (dx) / (float) (dy);
		if (Math.abs(ratioX) < TAN_DIRECTION) {
			if (dx > 0) {
				return GestureStatus.Right;
			} else {
				return GestureStatus.Left;
			}
		}
		if (Math.abs(ratioY) < TAN_DIRECTION) {
			if (dy > 0) {
				return GestureStatus.Down;
			} else {
				return GestureStatus.Up;
			}
		}
		return GestureStatus.Center;
	}

	/**
	 * 处理各种手势的监听.
	 * 
	 * @param status
	 *            {@linkplain #GestureStatus}.
	 * @param pressed
	 *            {@code true} if touch just begun.
	 * @param playSound
	 *            {@code true} if click sound should be played.
	 */
	private void sendEvent(GestureStatus status, boolean pressed,
			boolean playSound) {
		if (playSound) {
			if (pressed) {
				 vibrator.vibrate(40);
			}
			playSound();
		}
		if(pressed)
		{
				switch (status) {
				case Up:
					mImageResouceId = R.drawable.key_arrow_up;
					setImageResource(mImageResouceId);
					break;
				case Down:
					mImageResouceId = R.drawable.key_arrow_down;
					setImageResource(mImageResouceId);
					break;
				case Left:
					mImageResouceId = R.drawable.key_arrow_left;
					setImageResource(mImageResouceId);
					break;
				case Right:
					mImageResouceId = R.drawable.key_arrow_right;
					setImageResource(mImageResouceId);
					break;
				default:
					break;
				}
			
			Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.spark);
			startAnimation(animation);
			return;
		}
		if (mMouseGesture != null) {
			switch (status) {
			case Up:
				mMouseGesture.gestureUp();
				break;
			case Down:
				mMouseGesture.gestureDown();
				break;
			case Left:
				mMouseGesture.gestureLeft();
				break;
			case Right:
				mMouseGesture.gestureRight();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 用户点击时触发该事件
	 */
	private void onCenterAction() {
		Log.i("","mStatus = "+mStatus+System.currentTimeMillis());

		if (mMouseGesture != null) {
			
			mImageResouceId = R.drawable.key_click;
			setImageResource(mImageResouceId);
			Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.spark);
			startAnimation(animation);
			mMouseGesture.gestureClick();
			playSound();
		}
	}
	/**
	 * 重新处理数据.
	 *
	 */
	private void center() {
		isTouchViewonFocus = false;
		mStatus = GestureStatus.IDLE;
		if(mCleanTask!=null)
			mCleanTask.cancel();
		mCleanTask = new TimerTask()
		{
			/**
			 * Draw again
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mImageResouceId = -1;
				postInvalidate();
			}
			
		};
		mCleanTimer.cancel();
		mCleanTimer.purge();
		mCleanTimer = new Timer();
		
		mCleanTimer.schedule(mCleanTask, 1500);
	}
	private Timer mCleanTimer = new Timer();
	private TimerTask mCleanTask ;
	/**
	 * Plays a sound when sending a key.
	 */
	private void playSound() {
		playSoundEffect(SoundEffectConstants.CLICK);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Log.i("", "mImageResouceId = "+mImageResouceId);
		
		if(mImageResouceId < 0)
		{
			return;
		}
		super.onDraw(canvas);
		
	}


	public void setOnGestureListener(MouseGesture gesture) {
		mMouseGesture = gesture;
	}
	/**
	 * 
	 *  Different GestureStatus where the TvRemoteTouchView can be moved.
	 *
	 */
	public enum GestureStatus {
		IDLE(false), 
		Center(false), 
		Left(true),
		Right(true), 
		Up(true),
		Down(true);

		final boolean isMove;

		private GestureStatus(boolean isMove) {
			// TODO Auto-generated constructor stub
			this.isMove = isMove;
		}
	}
	/**
	 * 
	 * 
	 *
	 */
	public interface MouseGesture {
		/**
		 * called when the TvRemoteTouchView was moved in left direction
		 */
		void gestureLeft();
		/**
		 * called when the TvRemoteTouchView was moved in right direction
		 */
		void gestureRight();
		/**
		 * called when the TvRemoteTouchView was moved in down direction
		 */
		void gestureDown();
		/**
		 * called when the TvRemoteTouchView was moved in up direction
		 */
		void gestureUp();
		/**
		 * called when the TvRemoteTouchView was clicked
		 */
		void gestureClick();
	}

}
