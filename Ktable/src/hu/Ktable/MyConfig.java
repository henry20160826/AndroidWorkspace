package hu.Ktable;

import android.database.sqlite.SQLiteDatabase;

public class MyConfig {
	public static Class_info[][] ke;// �γ���Ϣ
	public static boolean[] classmap;
	public static String[][] StrKe;// ����ַ���
	public static boolean change = false;
	public static SQLiteDatabase dbW;
	public static DatabaseHelper dbhelper;
	public static final int BOX_NUMBER=7;//ÿ�ڿε���Ϣ��Ŀ
	public static final int DAY_NUMBER=7;//һ�ܼ���Ŀ�
	public static final int TIME_NUMBER=6;//һ���м���ʱ��εĿ�
	
	/**
	 * 
	 * �Զ�����class_info���γ���Ϣ
	 * 
	 */
	public static class Class_info {
		// private String name;
		// private String teacher;
		// private String room;
		// private String kind;
		// private String time;
		// private String last;
		// private String state;
		// private String note;
		public String[] data;

		void setdata(String[] m) {
			data = m;
		}

		/*
		 * String[] getdata() {
		 * 
		 * return data; }
		 */
		public static void updateStrKe(int weekday,int time) {
			String[] something = new String[8];
			something[0] = "\n";
			something[1] = "\t";
			something[2] = "\t";
			something[3] = "\t";
			something[4] = "\n";
			something[5] = "\n";
			something[6] = "\t";
			for (int i = 0; i < 7; i++) {
				if (0 == i) {
					StrKe[weekday][time] = ke[weekday][time].data[i];
				} else {
					StrKe[weekday][time] += something[i - 1]
							+ ke[weekday][time].data[i];

				}
			}
			change = true;
		}
	};
}
