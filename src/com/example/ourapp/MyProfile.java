package com.example.ourapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MyProfile extends Activity {
	private List<String> listAge = new ArrayList<String>();
	private List<String> listGender = new ArrayList<String>();
	private List<String> listProv = new ArrayList<String>();
	private List<String> listZone = new ArrayList<String>();
	private Spinner spinnerAge;
	private Spinner spinnerGender;
	private Spinner spinnerProv;
	private Spinner spinnerZone;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (!AppSession.isHasLogin()) {
			Intent intent = new Intent(MyProfile.this, login.class);
			startActivity(intent);
			finish();
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myprofile);

		EditText et1 = (EditText) findViewById(R.id.editText1);
		et1.setText(AppSession.getUSER_NAME());
		
		listAge.add("80����");
		listAge.add("60~80");
		listAge.add("40~60");
		listAge.add("20~40");
		listAge.add("20����");
		spinnerAge = (Spinner) findViewById(R.id.spinnerAge);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listAge);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAge.setAdapter(adapter);
		
		listGender.add("��");
		listGender.add("Ů");
		spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listGender);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerGender.setAdapter(adapter);
		
		listProv.add("�㽭ʡ");
		listProv.add("�Ϻ���");
		spinnerProv = (Spinner) findViewById(R.id.spinnerProv);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listProv);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerProv.setAdapter(adapter);
		
		listZone.add("������");
		listZone.add("������");
		listZone.add("������");
		listZone.add("������");
		listZone.add("������");
		listZone.add("����");
		spinnerZone = (Spinner) findViewById(R.id.spinnerZone);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listZone);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerZone.setAdapter(adapter);
		
		Button b1 = (Button) findViewById(R.id.modify);
		b1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(MyProfile.this, "�޸ĳɹ�",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MyProfile.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	}

}
