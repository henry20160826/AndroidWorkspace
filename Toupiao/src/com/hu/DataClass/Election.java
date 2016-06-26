package com.hu.DataClass;

import android.database.Cursor;

public class Election {
	public int eid;
	public String name;
	public String date;
	public Election(){
		
	}
	public Election(String id){
		this.eid=Integer.parseInt(id);
	}
	public Election(Cursor cursor){
		this.eid=cursor.getInt(cursor.getColumnIndex("eid"));
		this.name=cursor.getString(cursor.getColumnIndex("name"));
		this.date=cursor.getString(cursor.getColumnIndex("date"));
	}
}
