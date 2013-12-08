package com.example.ourapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FirstWelcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_welcome);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finish();
		Intent intent = new Intent(FirstWelcome.this, MainActivity.class);
		startActivity(intent);
	}

}
