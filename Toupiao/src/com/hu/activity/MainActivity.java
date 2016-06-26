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
		// TODO 创建Activity
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		setContentView(R.layout.activity_main);// 加载布局文件
		// TODO 初始化全局变量
		all = (All) getApplication();// 得到全局变量
		all.dbManager = new DatabaseManager(this);// 初始化dbManager

		btn_student = (Button) findViewById(R.id.btn_student);
		btn_election = (Button) findViewById(R.id.btn_election);
		btn_newelection = (Button) findViewById(R.id.btn_newelection);// 通过id号得到控件

		OnClickListener btnListner = new btnListner();
		btn_student.setOnClickListener(btnListner);
		btn_election.setOnClickListener(btnListner);
		btn_newelection.setOnClickListener(btnListner);// 绑定监听器

	}

	class btnListner implements View.OnClickListener {
		// TODO 按钮监听器
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
