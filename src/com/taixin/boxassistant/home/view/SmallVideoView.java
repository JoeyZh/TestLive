package com.taixin.boxassistant.home.view;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SmallVideoView extends SurfaceView {

	private SurfaceHolder mSurfaceHolder = null;
	private MediaPlayer mMediaPlayer = null;

	public SmallVideoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void play() {

	}

	public void stop() {

	}

	SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
			mSurfaceHolder = holder;
		}

		public void surfaceCreated(SurfaceHolder holder) {
			mSurfaceHolder = holder;
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			mSurfaceHolder = null;
		}
	};

}
