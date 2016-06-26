package com.hu.DataClass;

import java.util.List;

import com.hu.DBoperator.DatabaseManager;

import android.app.Application;

//TODO 全局变量
public class All extends Application {
//public List<Student> students;
	public DatabaseManager dbManager;
//	public int size() {
//		return students.size();
//	}
//
//	public boolean exist(String sid) {
//		int length = size();
//		int i;
//		for (i = 0; i < length; i++) {
//			if (students.get(i).sid.equals(sid)) {
//				break;
//			}
//		}
//		if (i == length) {
//			return false;
//		}
//		return true;
//	}
//	public boolean exist(Student student) {
//		int length = size();
//		int i;
//		for (i = 0; i < length; i++) {
//			if (students.get(i).sid.equals(student.sid)) {
//				break;
//			}
//		}
//		if (i == length) {
//			return false;
//		}
//		return true;
//	}

	/* (non-Javadoc)
	 * @see android.app.Application#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		this.dbManager.closeDB();
	}

	/* (non-Javadoc)
	 * @see android.app.Application#onTerminate()
	 */
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		this.dbManager.closeDB();
	}
	
}
