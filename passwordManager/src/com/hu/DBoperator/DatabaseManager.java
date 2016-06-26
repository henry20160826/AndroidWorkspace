package com.hu.DBoperator;

import static com.hu.DataClass.Content.*;
import java.util.ArrayList;
import java.util.List;

import com.hu.encode.Cipher;
import com.hu.encode.MD5;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
	private DatabaseHelper helper;
	public SQLiteDatabase db;
	public static final String selectMyPassword = "SELECT * FROM myp";
	public static final String selectAllPasswords = "SELECT * FROM ps";

	public DatabaseManager(Context context) {
		helper = new DatabaseHelper(context);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}// 构造函数

	public Cursor queryAll(String tablename) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + tablename, null);
		return cursor;
	}// 查询表tablename

	public Cursor query(String sql) {
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	public String getMyPassword() {
		Cursor cursor = query(selectMyPassword);
		cursor.moveToFirst();
		return cursor.getString(0);
	}

	public Cursor getPasswords() {
		Cursor cursor = query(selectAllPasswords);
		return cursor;
	}

	public String[] getOneItem(String id) {
		Cursor cursor = db.rawQuery(selectAllPasswords + " WHERE pkey = ?",
				new String[] { id });
		cursor.moveToFirst();
		String[] strings = new String[4];
		for (int i = 0; i < 4; i++) {
			strings[i] = cursor.getString(i + 1);
			strings[i] = Cipher.decode(strings[i]);
		}
		return strings;
	}

	public int getMaxId() {
		Cursor cursor = query("select max(pkey) from ps");
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public void insertOneItem(String[] s) {
		for (int i = 0; i < 4; i++) {
			s[i] = Cipher.encode(s[i]);
		}
		int max = getMaxId() + 1;
		db.execSQL("INSERT INTO ps(pkey,place,name,p,info) VALUES(?,?,?,?,?)",
				new Object[] { max, s[0], s[1], s[2], s[3] });
		// db.execSQL("insert into ps(pkey,place,name,p,info) values(" + "'"
		// + max + "'," + "'" + s[0] + "'," + "'" + s[1] + "',"
		// + "'" + s[2] + "'," + "'" + s[3] + "'" + ")");
	}

	public void updateOneItem(String id, String[] s) {
		for (int i = 0; i < 4; i++) {
			s[i] = Cipher.encode(s[i]);
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("place", s[0]);
		contentValues.put("name", s[1]);
		contentValues.put("p", s[2]);
		contentValues.put("info", s[3]);
		db.update("ps", contentValues, "pkey=?", new String[] { id });
	}

	public void deleteOneItem(String id) {
		db.delete("ps", "pkey=?", new String[] { id });
	}

	/**
	 * close database
	 */
	public void setMyPassword(String s) {
		String a = MD5.getMD5(s);
		ContentValues contentValues = new ContentValues();
		contentValues.put("p", a);
		db.update("myp", contentValues, "", new String[]{});
	}

	public boolean checkMypassword(String s) {
		String smd5String = MD5.getMD5(s);
		String password = getMyPassword();
		if (password.equals(smd5String)) {
			return true;
		}
		return false;
	}

	public void closeDB() {
		db.close();
	}

}