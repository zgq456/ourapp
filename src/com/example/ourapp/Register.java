package com.example.ourapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

	private EditText et1;
	private EditText et2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logon);

		et1 = (EditText) findViewById(R.id.name);
		et2 = (EditText) findViewById(R.id.password);

		Button b1 = (Button) findViewById(R.id.logon);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				String name = et1.getText().toString();
				String password = et2.getText().toString();
				if (name.equals("") || password.equals("")) {
					Toast.makeText(Register.this, "������", Toast.LENGTH_SHORT).show();
					et1.requestFocus();
				} else {
					DBAdapter db = new DBAdapter(Register.this);
					//int pass = Integer.valueOf(password);
					db.open();
					Cursor c = db.getAnBook(name);
					if (c.moveToFirst())
						Toast.makeText(Register.this, "�û��Ѵ��ڣ�ע��ʧ��",
								Toast.LENGTH_SHORT).show();
					else {
						db.insertBook(name, password);
						db.close();
						Toast.makeText(Register.this, "ע��ɹ�", Toast.LENGTH_LONG)
								.show();
						et1.setText("");
						et2.setText("");
						et1.requestFocus();
						
						Intent intent=new Intent(Register.this, Login.class);
						//intent.setClass(logon.this, login.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //ֱ�ӹر�Activity
						startActivity(intent);
					}
					db.close();
				}
				
			}
		});
	}

}
