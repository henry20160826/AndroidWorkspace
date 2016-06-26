package hu.Ktable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table user(id int,data varchar(30))");
		ContentValues a=new ContentValues();
		a.put("id", 0);
		a.put("data",0);
		db.insert("user",null,a);
		ContentValues b=new ContentValues();
		b.put("id",1);
		b.put("data", 0);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
//  4	全局模式
//	0 	默认皮肤
//	1	黑色炫酷
//	2 	绿色雨林
//	3 	蓝色海洋
	public int readstyle(SQLiteDatabase db)
	{
		Cursor cursor=db.query("user",new String[]{"id","data"},"id=?",new String[]{"0"}, null, null, null);
		cursor.moveToFirst();
		String d=cursor.getString(cursor.getColumnIndex("data"));
		int num=Integer.parseInt(d);
		return num;		
	}
}
