package com.example.ourapp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class Detail extends Activity {

	private ImageView iv_2;
	private Button juanzhu;
	private TextView logo_2, name_2, diqu_2, renshu_2, donation_2,
			still_need_2, jindu_2, riqi_2, newsDetailTxt;

	private VideoView vv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		logo_2 = (TextView) findViewById(R.id.logo_2);
		name_2 = (TextView) findViewById(R.id.name_2);
		diqu_2 = (TextView) findViewById(R.id.diqu_2);
		renshu_2 = (TextView) findViewById(R.id.renshu_2);
		donation_2 = (TextView) findViewById(R.id.juankuan_2);
		still_need_2 = (TextView) findViewById(R.id.still_need_2);
		
		newsDetailTxt = (TextView) findViewById(R.id.news_details_text);
		 try {
			 InputStream in = getResources().getAssets().open("0.txt");
			// 读取assets文件夹中的txt文件,将它放入输入流中   
			 // 获得输入流的长度   
			 int length = in.available();  
			 // 创建字节输入   
			 byte[] buffer = new byte[length];  
			 // 放入字节输入中   
			 in.read(buffer);  
			 // 获得编码格式   
			 String type = codetype(buffer);  
			 // 设置编码格式读取TXT   
			 String res = EncodingUtils.getString(buffer, type);  
			 // 关闭输入流   
			 in.close();  
			 newsDetailTxt.setText(res);  
			
		} catch (IOException e) {
			e.printStackTrace();
		}  


		jindu_2 = (TextView) findViewById(R.id.jindu_2);
		riqi_2 = (TextView) findViewById(R.id.riqi_2);
		

		Intent intent = getIntent();
		final int _id = intent.getIntExtra("_id", 1);
		final int donation = intent.getIntExtra("donation", 0);
		String jindu = intent.getStringExtra("jindu");

		final String file = intent.getStringExtra("file");

//		vv = (VideoView) findViewById(R.id.videoView1);
//		vv.setVideoPath("mnt/sdcard/" + file);
		//vv.setMediaController(new MediaController(this));

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
		iv_2 = (ImageView) findViewById(R.id.iv_21);
		iv_2.setImageResource(new Picture().getPicture(_id - 1));
		juanzhu = (Button) findViewById(R.id.juanzhu);
		juanzhu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				if (!AppSession.isHasLogin()) {
					Intent intent = new Intent(Detail.this, login.class);
					startActivity(intent);
					finish();
					return;
				}
				
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

//				vv.start();
				// Toast.makeText(Detail.this,"mnt/sdcard/"+file,
				// Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(Detail.this, VideoPlayer.class);
				startActivity(intent);

			}
		});

		// vv=(VideoView)findViewById(R.id.videoView1);
		if(db != null) {
			db.close();
		}
	}
	
	private String codetype(byte[] head) {
        byte[] codehead = new byte[4];
        // 截取数组
        System.arraycopy(head, 0, codehead, 0, 4);
        String code = "";
        if(head[0] == -1 && head[1] == -2) {
            code = "UTF-16";
        }
        else if(head[0] == -2 && head[1] == -1) {
            code = "Unicode";
        }
        else if(head[0] == -17 && head[1] == -69 && head[2] == -65)
            code = "UTF-8";
        else {
            code = "gb2312";
        }
        return code;
	}

}
