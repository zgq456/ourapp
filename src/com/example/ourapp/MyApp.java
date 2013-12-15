package com.example.ourapp;

import java.net.CookieStore;

import android.app.Application;

public class MyApp extends Application {

	private CookieStore cookies;

	public CookieStore getCookie() {
		return cookies;
	}

	public void setCookie(CookieStore cks) {
		cookies = cks;
	}

}