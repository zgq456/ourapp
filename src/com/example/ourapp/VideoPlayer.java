package com.example.ourapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * @author Administrator
 * 
 */
public class VideoPlayer extends Activity implements OnCompletionListener,
		OnPreparedListener {// ,OnTouchListener
	private VideoView mVV;
	MediaController mController;

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.videoplayer);
		int fileRes = 0;
		Bundle e = getIntent().getExtras();
		if (e != null) {
			fileRes = e.getInt("fileRes");
		}
		mVV = (VideoView) findViewById(R.id.myvideoview);
		mVV.setOnCompletionListener(this);
		mVV.setOnPreparedListener(this);
		// mVV.setOnTouchListener(this);

		mController = new MediaController(this);
		mController.setMediaPlayer(mVV);

		mVV.setMediaController(mController);

		if (!playFileRes(fileRes)) {
			return;
		}
		mVV.start();
	}

	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		int fileRes = 0;
		Bundle e = getIntent().getExtras();
		if (e != null) {
			fileRes = e.getInt("fileRes");
		}
		playFileRes(fileRes);
	}

	private boolean playFileRes(int fileRes) {
		if (false && fileRes == 0) {// FIXME
			stopPlaying();
			return false;
		} else {
			 String uri = "android.resource://" +
			 getPackageName()
			 + "/" + R.raw.family;
			mVV.setVideoURI(Uri.parse(uri )); //fileRes
			return true;
		}
	}

	public void stopPlaying() {
		mVV.stopPlayback();
		this.finish();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {

		new AlertDialog.Builder(this)

		.setTitle("提示")

		.setMessage("伊利集团代表葛浩然谢谢您!")

		.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.show();
	}

	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	// // stopPlaying();
	// super.onTouchEvent(event);
	// return true;
	// }

	@Override
	public void onPrepared(MediaPlayer mp) {
		// mp.setLooping(true);
	}
}