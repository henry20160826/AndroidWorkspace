package com.hu.DBoperator;

import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hu.encode.*;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "test";
	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_MYP = "create table myp(p String)";
	private static final String CREATE_PS = "create table ps(pkey int primary key,place String,name String,p String,info String)";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}// 构造函数

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_MYP);// 创建表 本软件密码
		db.execSQL(CREATE_PS);// 创建表 密码表
		String a = MD5.getMD5("123456");
		db.execSQL("insert into myp values(" + "'" + a + "'" + ")");
//		db.execSQL("insert into ps(pkey,place,name,p) values(0,'新浪微博','hh','7654321')");
		// db.execSQL("create table candidate(eid INTEGER,sid String,"
		// + "FOREIGN KEY(eid) REFERENCES election(eid),"
		// +
		// "FOREIGN KEY(sid) REFERENCES student(sid),PRIMARY KEY (eid,sid))");//
		// 创建表候选人信息
		// db.execSQL("create table winner(eid INTEGER,sid String,"
		// + "PRIMARY KEY (eid,sid))");
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
