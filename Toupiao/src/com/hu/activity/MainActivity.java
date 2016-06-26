package com.hu.activity;

import static com.hu.DataClass.Content.*;

import com.hu.DBoperator.DatabaseManager;
import com.hu.DataClass.All;
import com.hu.toupiao.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btn_student, btn_election, btn_newelection;
	private All all;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO ����Activity
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);// ���ر���
		setContentView(R.layout.activity_main);// ���ز����ļ�
		// TODO ��ʼ��ȫ�ֱ���
		all = (All) getApplication();// �õ�ȫ�ֱ���
		all.dbManager = new DatabaseManager(this);// ��ʼ��dbManager

		btn_student = (Button) findViewById(R.id.btn_student);
		btn_election = (Button) findViewById(R.id.btn_election);
		btn_newelection = (Button) findViewById(R.id.btn_newelection);// ͨ��id�ŵõ��ؼ�

		OnClickListener btnListner = new btnListner();
		btn_student.setOnClickListener(btnListner);
		btn_election.setOnClickListener(btnListner);
		btn_newelection.setOnClickListener(btnListner);// �󶨼�����

	}

	class btnListner implements View.OnClickListener {
		// TODO ��ť������
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.btn_student:
				intent.setClass(MainActivity.this, ListviewActivity.class);
				intent.putExtra("info", STUDENT);
				startActivity(intent);
				break;
			case R.id.btn_election:
				intent.setClass(MainActivity.this, ListviewActivity.class);
				intent.putExtra("info", ELECTION);
				startActivity(intent);
				break;
			case R.id.btn_newelection:
				intent.setClass(MainActivity.this, NewElectionActivity.class);
				// intent.putExtra("info", ELECTION);
				startActivity(intent);
				break;
			default:
				break;
			}
			// Toast.makeText(MainActivity.this, a+"",
			// Toast.LENGTH_LONG).show();
		}
	}

}
