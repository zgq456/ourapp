package com.example.ourapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listview = (ListView) findViewById(R.id.lv);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.listview_item, new String[] { "img", "name", "logo",
						"place", "donation", "donation", "jindu", "still_need",
						"riqi", "num" ,"file"}, new int[] { R.id.iv, R.id.name,
						R.id.logo, R.id.diqu, R.id.renshu, R.id.juankuan,
						R.id.jindu, R.id.still_need, R.id.riqi, R.id._id ,R.id.file});

		listview.setAdapter(adapter);
		// setListAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv1=(TextView)arg1.findViewById(R.id._id);
				int _id=Integer.valueOf(tv1.getText().toString());
				
				TextView tv2=(TextView)arg1.findViewById(R.id.jindu);
				String jindu=tv2.getText().toString();
				
				TextView tv3=(TextView)arg1.findViewById(R.id.renshu);
				int donation=Integer.valueOf(tv3.getText().toString());
				
				TextView tv4=(TextView)arg1.findViewById(R.id.file);
				String file=tv4.getText().toString();
				/*
				TextView tv5=(TextView)arg1.findViewById(R.id.diqu);
				String diqu=String.valueOf(tv5.getText().toString());
				
				TextView tv6=(TextView)arg1.findViewById(R.id.renshu);
				String renshu=String.valueOf(tv6.getText().toString());
				
				TextView tv7=(TextView)arg1.findViewById(R.id.jindu);
				String jindu=String.valueOf(tv7.getText().toString());
				
				*/

				Intent intent = new Intent(MainActivity.this, Detail.class);
				intent.putExtra("_id", _id);
				intent.putExtra("jindu", jindu);
				intent.putExtra("donation",donation);
				intent.putExtra("file", file);
				//intent.putExtra("logo", logo);
				//intent.putExtra("name", name);
				//intent.putExtra("diqu", diqu);
				//intent.putExtra("renshu", renshu);
				//intent.putExtra("jindu", jindu);
				
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
				// map.put("text", "Œ“");
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
				
				String file=c.getString(7);
				

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

}
