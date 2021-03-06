package com.hu.DBoperator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "test";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}//构造函数

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table student(sid String PRIMARY KEY,"
				+ "name String,phone String,"
				+ "classPost String,schoolPost String,"
				+ "politicalStatus String,PartyClass String)");// 创建表 学生信息
		db.execSQL("create table election(eid INTEGER ," + "name String,"
				+ "date String)");// 创建表 选举信息
		// db.execSQL("create table candidate(eid INTEGER,sid String,"
		// + "FOREIGN KEY(eid) REFERENCES election(eid),"
		// +
		// "FOREIGN KEY(sid) REFERENCES student(sid),PRIMARY KEY (eid,sid))");//
		// 创建表候选人信息
		db.execSQL("create table winner(eid INTEGER,sid String,"
				+ "PRIMARY KEY (eid,sid))");
		// PRIMARY KEY AUTOINCREMENT
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onOpen(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#close()
	 */
	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		super.close();
	}

}
