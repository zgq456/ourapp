package com.example.ourapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyHelp extends Activity {

	private SimpleAdapter adapter;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (!AppSession.isHasLogin()) {
			Intent intent = new Intent(MyHelp.this, login.class);
			startActivity(intent);
			finish();
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhelp);
		ListView listview = (ListView) findViewById(R.id.myhelp_listview);
		dataList = getData();
		adapter = new SimpleAdapter(
				this,
				dataList,
				R.layout.myhelp_listitem,
				new String[] { "seeker", "zone_id", "help_amount", "help_date" },
				new int[] { R.id.seeker, R.id.zone_id, R.id.help_amount,
						R.id.help_date });
		listview.setAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 20; i++) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("seeker", "求助者" + (i + 1));
			map.put("zone_id", "省市" + (i + 1));
			map.put("help_amount", (i + 1)*i);
			map.put("help_date", "" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			list.add(map);
		}
		return list;
	}

}
