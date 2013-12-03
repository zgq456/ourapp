package com.example.ourapp;

import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Detail extends Activity {

	private ImageView iv_2;
	private Button juanzhu;
	private TextView logo_2, name_2, diqu_2, renshu_2, donation_2,
			still_need_2, jindu_2, riqi_2;

	private VideoView vv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		logo_2 = (TextView) findViewById(R.id.logo_2);
		name_2 = (TextView) findViewById(R.id.name_2);
		diqu_2 = (TextView) findViewById(R.id.diqu_2);
		renshu_2 = (TextView) findViewById(R.id.renshu_2);
		donation_2 = (TextView) findViewById(R.id.juankuan_2);
		still_need_2 = (TextView) findViewById(R.id.still_need_2);

		jindu_2 = (TextView) findViewById(R.id.jindu_2);
		riqi_2 = (TextView) findViewById(R.id.riqi_2);

		Intent intent = getIntent();
		final int _id = intent.getIntExtra("_id", 1);
		final int donation = intent.getIntExtra("donation", 0);
		String jindu = intent.getStringExtra("jindu");

		String file = intent.getStringExtra("file");

		vv = (VideoView) findViewById(R.id.videoView1);
		
//		vv.setVideoPath("/mnt/sdcard/" + file);
//		vv.setVideoPath("/mnt/sdcard/data/handou.3gp"); //FIXME
		vv.setVideoPath("/mnt/sdcard/data/Family.MP4"); //FIXME
		vv.setMediaController(new MediaController(this));

		DBManager dbm = new DBManager(this);
		SQLiteDatabase db = dbm.openDatabase();
		Cursor cur = db.rawQuery("SELECT * FROM Name WHERE _id=" + _id, null);
		if (cur.moveToFirst()) {

			logo_2.setText(cur.getString(2));
			name_2.setText(cur.getString(1));
			diqu_2.setText(cur.getString(3));
			renshu_2.setText(String.valueOf(cur.getInt(4)));
			donation_2.setText(String.valueOf(cur.getInt(4)));
			int still_need = cur.getInt(5) - cur.getInt(4);
			still_need_2.setText(String.valueOf(still_need));
			jindu_2.setText(jindu);
			riqi_2.setText(cur.getString(6));

		}
		iv_2 = (ImageView) findViewById(R.id.iv_2);
		iv_2.setImageResource(new Picture().getPicture(_id - 1));
		juanzhu = (Button) findViewById(R.id.juanzhu);
		juanzhu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// vv.setVideoPath("/sdcard/test.mp4");
				// vv.start();
				DBManager dbm = new DBManager(Detail.this);
				SQLiteDatabase db = dbm.openDatabase();
				ContentValues cv = new ContentValues();
				cv.put("donation", donation + 1);
				db.update("Name", cv, "_id" + "=" + _id, null);
				db.close();
				renshu_2.setText(String.valueOf(donation + 1));
				donation_2.setText(String.valueOf(donation + 1));

				vv.start();
				// Toast.makeText(Detail.this,"mnt/sdcard/"+file,
				// Toast.LENGTH_SHORT).show();

			}
		});

		// vv=(VideoView)findViewById(R.id.videoView1);
	}

}
