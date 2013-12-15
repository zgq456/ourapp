package com.example.ourapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class logon extends Activity {
	private List<String> listAge = new ArrayList<String>();
	private List<String> listGender = new ArrayList<String>();
	private List<String> listProv = new ArrayList<String>();
	private List<String> listZone = new ArrayList<String>();
	private Spinner spinnerAge;
	private Spinner spinnerGender;
	private Spinner spinnerProv;
	private Spinner spinnerZone;
	private ArrayAdapter<String> adapter;
	private EditText et1;
	private EditText et2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logon);

		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		
		listAge.add("80以上");
		listAge.add("60~80");
		listAge.add("40~60");
		listAge.add("20~40");
		listAge.add("20以下");
		spinnerAge = (Spinner) findViewById(R.id.spinnerAge);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listAge);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAge.setAdapter(adapter);
		
		listGender.add("男");
		listGender.add("女");
		spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listGender);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerGender.setAdapter(adapter);
		
		listProv.add("浙江省");
		listProv.add("上海市");
		spinnerProv = (Spinner) findViewById(R.id.spinnerProv);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listProv);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerProv.setAdapter(adapter);
		
		listZone.add("杭州市");
		listZone.add("绍兴市");
		listZone.add("宁波市");
		listZone.add("嘉兴市");
		listZone.add("温州市");
		listZone.add("金华市");
		spinnerZone = (Spinner) findViewById(R.id.spinnerZone);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listZone);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerZone.setAdapter(adapter);

		Button b1 = (Button) findViewById(R.id.logon);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				String name = et1.getText().toString();
				String password = et2.getText().toString();
				if (name.equals("") || password.equals("")) {
					Toast.makeText(logon.this, "请输入", Toast.LENGTH_SHORT).show();
					et1.requestFocus();
				} else {
					DBAdapter db = new DBAdapter(logon.this);
					//int pass = Integer.valueOf(password);
					db.open();
					Cursor c = db.getAnBook(name);
					if (c.moveToFirst())
						Toast.makeText(logon.this, "用户已存在，注册失败",
								Toast.LENGTH_SHORT).show();
					else {
						db.insertBook(name, password);
						db.close();
						Toast.makeText(logon.this, "注册成功", Toast.LENGTH_LONG)
								.show();
						et1.setText("");
						et2.setText("");
						et1.requestFocus();
						
						Intent intent=new Intent(logon.this, login.class);
						//intent.setClass(logon.this, login.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //直接关闭Activity
						startActivity(intent);
					}
					db.close();
				}
				
			}
		});
	}

}
