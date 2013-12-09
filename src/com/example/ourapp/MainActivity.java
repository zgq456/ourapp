package com.example.ourapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		PullDownListView.OnRefreshListioner {
	boolean isExit;
	private PullDownListView mPullDownView;
	private ListView mListView;
	private List<String> list = new ArrayList<String>();
	private SimpleAdapter adapter;
	private Handler mHandler = new Handler();
	private int maxAount = 20;// 设置了最大数据值

	Handler mHandlerExit = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPullDownView = (PullDownListView) findViewById(R.id.pull_list);
		mPullDownView.setRefreshListioner(this);
		mListView = mPullDownView.mListView;

		// ListView listview = (ListView) findViewById(R.id.lv);
		adapter = new SimpleAdapter(this, getData(), R.layout.listview_item,
				new String[] { "img", "name", "logo", "place", "donation",
						"donation", "jindu", "still_need", "riqi", "num",
						"file" }, new int[] { R.id.iv, R.id.name, R.id.logo,
						R.id.diqu, R.id.renshu, R.id.juankuan, R.id.jindu,
						R.id.still_need, R.id.riqi, R.id._id, R.id.file });

		mListView.setAdapter(adapter);
		// setListAdapter(adapter);
		mPullDownView.setMore(true);// 这里设置true表示还有更多加载，设置为false底部将不显示更多

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv1 = (TextView) arg1.findViewById(R.id._id);
				int _id = Integer.valueOf(tv1.getText().toString());

				TextView tv2 = (TextView) arg1.findViewById(R.id.jindu);
				String jindu = tv2.getText().toString();

				TextView tv3 = (TextView) arg1.findViewById(R.id.renshu);
				int donation = Integer.valueOf(tv3.getText().toString());

				TextView tv4 = (TextView) arg1.findViewById(R.id.file);
				String file = tv4.getText().toString();
				/*
				 * TextView tv5=(TextView)arg1.findViewById(R.id.diqu); String
				 * diqu=String.valueOf(tv5.getText().toString());
				 * 
				 * TextView tv6=(TextView)arg1.findViewById(R.id.renshu); String
				 * renshu=String.valueOf(tv6.getText().toString());
				 * 
				 * TextView tv7=(TextView)arg1.findViewById(R.id.jindu); String
				 * jindu=String.valueOf(tv7.getText().toString());
				 */

				Intent intent = new Intent(MainActivity.this, Detail.class);
				intent.putExtra("_id", _id);
				intent.putExtra("jindu", jindu);
				intent.putExtra("donation", donation);
				intent.putExtra("file", file);
				// intent.putExtra("logo", logo);
				// intent.putExtra("name", name);
				// intent.putExtra("diqu", diqu);
				// intent.putExtra("renshu", renshu);
				// intent.putExtra("jindu", jindu);

				startActivity(intent);
			}
		});

		Button but_myprofile = (Button) findViewById(R.id.but_myprofile);
		Button but_myhelp = (Button) findViewById(R.id.but_myhelp);
		// Button but_mynohelp = (Button) findViewById(R.id.but_mynohelp);
		Button but_allhelp = (Button) findViewById(R.id.but_allhelp);
		Button but_about = (Button) findViewById(R.id.but_about);

		but_myprofile.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MyProfile.class);
				// intent.setClass(logon.this, login.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 直接关闭Activity
				startActivity(intent);
			}
		});

		but_myhelp.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MyHelp.class);
				// intent.setClass(logon.this, login.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 直接关闭Activity
				startActivity(intent);
			}
		});

		/*
		 * but_mynohelp.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) { Intent intent=new
		 * Intent(MainActivity.this, MyNohelp.class);
		 * //intent.setClass(logon.this, login.class);
		 * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //直接关闭Activity
		 * startActivity(intent); } });
		 */

		but_allhelp.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AllHelp.class);
				// intent.setClass(logon.this, login.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 直接关闭Activity
				startActivity(intent);
			}
		});

		but_about.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, About.class);
				// intent.setClass(logon.this, login.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 直接关闭Activity
				startActivity(intent);
			}
		});
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// Map<String, Object> map = new HashMap<String, Object>();

		DBManager dbm = new DBManager(this);
		SQLiteDatabase db = dbm.openDatabase();

		String sql = "select * from Name";
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst()) {
			do {
				Map<String, Object> map = new HashMap<String, Object>();
				// map.put("img",R.drawable.img2);
				// map.put("text", "我");
				int num = c.getInt(0);
				Picture p = new Picture();
				String name = c.getString(1);
				String logo = c.getString(2);
				String place = c.getString(3);
				int donation = c.getInt(4);
				int need = c.getInt(5);

				int still_need = need - donation;

				DecimalFormat df = new DecimalFormat("0.00");
				float f = Float.valueOf(df.format((float) donation
						/ (float) need));
				String jindu = String.valueOf((int) (f * 100)) + "%";

				String riqi = c.getString(6);

				String file = c.getString(7);

				map.put("num", num);
				map.put("img", p.getPicture(num - 1));
				map.put("name", name);
				map.put("logo", logo);
				map.put("place", place);
				map.put("donation", donation);
				map.put("still_need", still_need);
				map.put("riqi", riqi);
				map.put("jindu", jindu);
				map.put("file", file);
				list.add(map);
			} while (c.moveToNext());
		}
		dbm.closeDatabase();
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 刷新，先清空list中数据然后重新加载更新内容
	 */
	public void onRefresh() {

		mHandler.postDelayed(new Runnable() {

			public void run() {
				list.clear();
				// addLists(10);
				mPullDownView.onRefreshComplete();// 这里表示刷新处理完成后把上面的加载刷新界面隐藏
				mPullDownView.setMore(true);// 这里设置true表示还有更多加载，设置为false底部将不显示更多
				adapter.notifyDataSetChanged();

			}
		}, 1500);

	}

	/**
	 * 加载更多，在原来基础上在添加新内容
	 */
	public void onLoadMore() {

		mHandler.postDelayed(new Runnable() {
			public void run() {
				// addLists(5);//每次加载五项新内容
				mPullDownView.onLoadMoreComplete();// 这里表示加载更多处理完成后把下面的加载更多界面（隐藏或者设置字样更多）
				if (list.size() < maxAount)// 判断当前list中已添加的数据是否小于最大值maxAount，是那么久显示更多否则不显示
					mPullDownView.setMore(true);// 这里设置true表示还有更多加载，设置为false底部将不显示更多
				else
					mPullDownView.setMore(false);
				adapter.notifyDataSetChanged();

			}
		}, 1500);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			mHandlerExit.sendEmptyMessageDelayed(0, 2000);
		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
		}
	}

}
