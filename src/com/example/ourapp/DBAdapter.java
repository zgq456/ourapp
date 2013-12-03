package com.example.ourapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	public static final String KEY_ROWID = "id";
	public static final String KEY_NAME = "Name";
	public static final String KEY_PASS = "Password";
	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "Login.db";
	private static final String DATABASE_TABLE = "Info";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table Info (id integer primary key autoincrement, "
			+ "Name char(25) not null , Password char(25) not null);";

	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}

	// ---opens the database---
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	// 添加
	public long insertBook(String name, String password) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_PASS, password);

		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// 删除指定的书签
	public boolean deleteBook(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// 查询所有书签
	public Cursor getAllBook() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME,
				KEY_PASS }, null, null, null, null, null);
	}

	// 模糊查询指定的书签
	public Cursor getBook(String book) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_PASS }, KEY_NAME + " like ?",
				new String[] { "%" + book + "%" }, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// 精确查询指定的唯一书签
	public Cursor getAnBook(String book) throws SQLException {
		Cursor cur = db.query(true, DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_NAME, KEY_PASS }, KEY_NAME + " like ?",
				new String[] { book }, null, null, null, null);
		if (cur != null) {
			cur.moveToFirst();
		}
		return cur;
	}

	public Cursor getUser(String name) throws SQLException {
		//Cursor cur = db.rawQuery("select "+ KEY_PASS +" from Info where Name="+name, null);
		/*
		Cursor cur = db.query(true, DATABASE_TABLE, new String[] {KEY_PASS}, KEY_NAME+"="+name,
			
				null, null, null, null, null);
				*/
		Cursor cur=db.rawQuery("select * from Info where Name=?", new String[]{name});
		if (cur != null) {
			cur.moveToFirst();
		}
		return cur;
	}

	// 更新书签
	public boolean updateBook(long rowId, int password) {
		ContentValues args = new ContentValues();
		args.put(KEY_PASS, password);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
}
