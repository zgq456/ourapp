package com.example.ourapp;

public class AppSession {
	public static String USER_NAME = null;
	public static boolean hasLogin = false;

	public static String getUSER_NAME() {
		return USER_NAME;
	}

	public static void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public static boolean isHasLogin() {
		return hasLogin;
	}

	public static void setHasLogin(boolean hasLogin) {
		AppSession.hasLogin = hasLogin;
	}

}
