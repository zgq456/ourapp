package com.example.ourapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {

	private Button b1, b2;
	private EditText et1;
	private EditText et2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		et1 = (EditText) findViewById(R.id.name);
		et2 = (EditText) findViewById(R.id.password);
//		et1.setText("qq");
//		et2.setText("123");
		
		b1 = (Button) findViewById(R.id.login);
		b2 = (Button) findViewById(R.id.logon);

		b1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String name = et1.getText().toString();
				String password = et2.getText().toString();
				if (name.equals("") || password.equals("")) {
					Toast.makeText(login.this, "������", Toast.LENGTH_SHORT)
							.show();
					et1.requestFocus();
				} else {
					DBAdapter db = new DBAdapter(login.this);
					// int pass = Integer.valueOf(password);
					db.open();
					Cursor c = db.getUser(name);
					if (c.moveToFirst()) {
						String p = c.getString(c.getColumnIndex("Password"));
						if (password.equals(p)) {
							Toast.makeText(login.this, "��¼�ɹ�",
									Toast.LENGTH_SHORT).show();
							db.close();
							
							AppSession.setHasLogin(true);
							AppSession.setUSER_NAME(name);
//							setLogin();
							
							Intent intent = new Intent(login.this,
									MainActivity.class);
							startActivity(intent);
							finish();
						} else {
							Toast.makeText(login.this, "�������",
									Toast.LENGTH_SHORT).show();
							et2.setText("");
							et2.requestFocus();
						}

					} else {
						Toast.makeText(login.this, "��δע��", Toast.LENGTH_SHORT)
								.show();
						et1.setText("");
						et2.setText("");
						et1.requestFocus();
						db.close();

					}
				}

			}
		});

		b2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(login.this, logon.class);
				startActivity(intent);
			}
		});
	}

}
