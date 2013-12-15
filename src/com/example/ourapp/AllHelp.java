package com.example.ourapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AllHelp extends Activity {

	private SimpleAdapter adapter;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allhelp);
		ListView listview = (ListView) findViewById(R.id.allhelp_listview);
		dataList = getData();
		adapter = new SimpleAdapter(this, dataList, R.layout.allhelp_listitem,
				new String[] { "company", "zone_id", "help_amount" },
				new int[] { R.id.company, R.id.zone_id, R.id.help_amount });
		listview.setAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 20; i++) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("company", "公司" + (i + 1));
			map.put("zone_id", "省市" + (i + 1));
			map.put("help_amount", (i + 1)*i*10);
			list.add(map);
		}
		return list;
	}

}
