package com.hu.DBoperator;

import static com.hu.DataClass.Content.*;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hu.DataClass.Election;
import com.hu.DataClass.Student;
import com.hu.DataClass.Winner;

public class DatabaseManager {
	private DatabaseHelper helper;
	public SQLiteDatabase db;
	public static final String selectFromStudent = "SELECT * FROM student where sid = ?";
	public static final String selectFromElection = "SELECT * FROM election where eid = ?";
	public static final String selectFromWinnerByEid = "SELECT * FROM winner where eid = ?";
	public static final String selectFromWinnerBySid = "SELECT * FROM winner where sid = ?";
	public static final String selectNameFromStudent = "SELECT name FROM student";

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

	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}

	// TODO student表操作

	public void addStudent(List<Student> students) throws Exception {
		db.beginTransaction(); // 开始事务
		try {
			for (Student student : students) {
				db.execSQL("INSERT INTO student VALUES(?,?,?,?,?,?,?)",
						new Object[] { student.sid, student.name,
								student.phone, student.classPost,
								student.schoolPost, student.politicalStatus,
								student.PartyClass });
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} catch (Exception e) {
			db.endTransaction(); // 结束事务
			System.out.println("数据库插入信息时错误");
			throw e;
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	public void addStudent(Student student) {
		db.execSQL("INSERT INTO student VALUES(?,?,?,?,?,?,?)",
				new Object[] { student.sid, student.name, student.phone,
						student.classPost, student.schoolPost,
						student.politicalStatus, student.PartyClass });
	}

	public void updateStudent(Student student) {
		ContentValues cv = new ContentValues();
		cv.put("name", student.name);
		cv.put("phone", student.phone);
		cv.put("classPost", student.classPost);
		cv.put("schoolPost", student.schoolPost);
		cv.put("politicalStatus", student.politicalStatus);
		cv.put("PartyClass", student.PartyClass);
		db.update("student", cv, "sid=?", new String[] { student.sid });
	}

	public void updateSid(String oString, String nString) {
		ContentValues cv = new ContentValues();
		cv.put("sid", nString);
		db.update("student", cv, "sid=?", new String[] { oString });
	}

	public Student queryStudent(String sid) {
		Cursor cursor = db.rawQuery(selectFromStudent, new String[] { sid });
		cursor.moveToFirst();
		Student student = new Student(cursor);
		cursor.close();
		return student;
	}

	public String queryAllStudentName() {
		Cursor cursor = db.rawQuery(selectNameFromStudent, null);
		String name = new String();
		while (cursor.moveToNext()) {
			if (name.equals("")) {
				name = cursor.getString(0);
			} else {
				name = name + SPLIT_INFO_SML + cursor.getString(0);
			}
		}
		return name;
	}

	public boolean existInStudent(String sid) {
		Cursor cursor = db.rawQuery(selectFromStudent, new String[] { sid });
		if (cursor.getCount() == 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;
	}

	public boolean existInStudent(Student s) {
		Cursor cursor = db.rawQuery(selectFromStudent, new String[] { s.sid });
		if (cursor.getCount() == 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;
	}

	public void deleteStudent(String sid) {
		db.delete("student", "sid=?", new String[] { sid });
	}

	// TODO election表操作

	public void addElection(Election election) {
		db.execSQL("INSERT INTO election VALUES(?,?,?)", new Object[] {
				election.eid, election.name, election.date });
	}

	public Election queryElection(String eid) {
		Cursor cursor = db.rawQuery(selectFromElection, new String[] { eid });
		cursor.moveToFirst();
		Election election = new Election(cursor);
		cursor.close();
		return election;
	}

	public void updateElection(Election election) {
		ContentValues cv = new ContentValues();
		cv.put("name", election.name);
		cv.put("date", election.date);
		db.update("election", cv, "eid=?", new String[] { election.eid + "" });
	}

	public void deleteElection(String eid) {
		db.delete("election", "eid=?", new String[] { eid });
	}

	public int getMaxEid() {
		Cursor cursor = db.rawQuery("select max(eid) from election", null);
		cursor.moveToFirst();
		int max = cursor.getInt(0);
		cursor.close();
		return max;
	}

	// TODO winner表操作
	public void addWinner(Winner winner) {
		db.execSQL("INSERT INTO winner VALUES(?,?)", new String[] {
				winner.eid + "", winner.sid });
	}

	public void addWinner(String eid, String sid) {
		db.execSQL("INSERT INTO winner VALUES(?,?)", new String[] { eid + "",
				sid });
	}

	public void addWinner(String eid, ArrayList<Student> winneridArrayList) {
		int length = winneridArrayList.size();
		for (int i = 0; i < length; i++) {
			addWinner(eid, winneridArrayList.get(i).sid);
		}
	}

	public Cursor queryWinnerBySid(String sid) {
		Cursor cursor = db
				.rawQuery(selectFromWinnerBySid, new String[] { sid });
		return cursor;
	}

	public Cursor queryWinnerByEid(String eid) {
		Cursor cursor = db
				.rawQuery(selectFromWinnerByEid, new String[] { eid });
		return cursor;
	}

	public ArrayList<Student> getStudentInWinner(String eid) {
		Cursor cursor = queryWinnerByEid(eid);
		ArrayList<Student> arrayList = new ArrayList<Student>();
		while (cursor.moveToNext()) {
			Student student = queryStudent(cursor.getString(cursor
					.getColumnIndex("sid")));
			arrayList.add(student);
		}
		return arrayList;
	}

	public void deleteEidinfoInWinner(String eid) {
		db.delete("winner", "eid=?", new String[] { eid });
	}

	public void deleteSidinfoInWinner(String sid) {
		db.delete("winner", "sid=?", new String[] { sid });
	}

}
