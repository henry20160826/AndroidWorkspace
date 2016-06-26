package com.hu.DataClass;

import java.io.Serializable;

import android.database.Cursor;

public class Student implements Serializable {
	/**
	 * 
	 */
	// private static final long serialVersionUID = -2869883125027882502L;
	public String sid;
	public String name;
	public String phone;
	public String classPost;
	public String schoolPost;
	public String politicalStatus;
	public String PartyClass;

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(Cursor cursor) {
		this.sid = cursor.getString(cursor.getColumnIndex("sid"));
		this.name = cursor.getString(cursor.getColumnIndex("name"));
		this.phone = cursor.getString(cursor.getColumnIndex("phone"));
		this.classPost = cursor.getString(cursor.getColumnIndex("classPost"));
		this.schoolPost = cursor.getString(cursor.getColumnIndex("schoolPost"));
		this.politicalStatus = cursor.getString(cursor
				.getColumnIndex("politicalStatus"));
		this.PartyClass = cursor.getString(cursor.getColumnIndex("PartyClass"));
	}

}
