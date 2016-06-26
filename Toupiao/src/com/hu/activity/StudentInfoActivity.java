package com.hu.activity;

import static com.hu.DataClass.Content.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hu.DataClass.All;
import com.hu.DataClass.Student;
import com.hu.toupiao.R;

public class StudentInfoActivity extends Activity {
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private EditText et_sid;
	private EditText et_name;
	private EditText et_phone;
	private EditText et_classPost;
	private EditText et_schoolPost;
	private EditText et_politicalStatus;
	private EditText et_PartyClass;
	private Button bt_ok, bt_cancel;
	private Student student;
	private int mode;
	private String oldSid;
	public All all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		setContentView(R.layout.activity_studentinfo);
		all = (All) getApplication();
		getWidget();
		getInfo();
//		bt_ok.setOnClickListener(new bt_okOnClickListener());
//		bt_cancel.setOnClickListener(new bt_cancelOnClickListener());
	}

	class bt_okOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			// 判断sid合法性
			// 1、将数据加入数据库2、更新ListView
			// Toast.makeText(SInfoActivity.this, mode, Toast.LENGTH_LONG)
			// .show();
			save();
		}
	}

	private void save() {
		Student student = getStudent();
		if (!student.sid.equals("")) {
			if (ADD == mode) {
				if (true == all.dbManager.existInStudent(student)) {
					Toast.makeText(StudentInfoActivity.this, "此学号已存在",
							Toast.LENGTH_LONG).show();
				} else {
					all.dbManager.addStudent(student);
					Intent intent = new Intent();

					intent.putExtra("name", student.name);
					intent.putExtra("classPost", student.classPost);
					intent.putExtra("sid", student.sid);
					setResult(ADD, intent);
					finish();
				}
			} else if (EDIT == mode) {
				boolean ok = false;
				if (oldSid.equals(student.sid)) {
					all.dbManager.updateStudent(student);
					ok = true;
				} else {
					if (true == all.dbManager.existInStudent(student)) {
						Toast.makeText(StudentInfoActivity.this, "此学号已存在",
								Toast.LENGTH_LONG).show();
					} else {
						String sid = student.sid;
						student.sid = oldSid;
						all.dbManager.updateStudent(student);
						all.dbManager.updateSid(oldSid, sid);
						student.sid = sid;
						ok = true;
					}
				}
				if (true == ok) {
					Intent intent = new Intent();
					intent.putExtra("sid", student.sid);
					intent.putExtra("name", student.name);
					intent.putExtra("classPost", student.classPost);
					setResult(EDIT, intent);
					finish();
				}
			}
		} else {
			Toast.makeText(StudentInfoActivity.this, "学号不能为空",
					Toast.LENGTH_LONG).show();
		}
	}

	class bt_cancelOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			back();
		}
	}

	private void back() {
		Intent intent = new Intent();
		// intent.putExtra("return", edit.getText().toString());
		setResult(RESULT_CANCELED, intent);
		finish();
	}

	private void getInfo() {
		Intent intent = getIntent();
		mode = intent.getExtras().getInt("mode");
		// Bundle bundle=intent.getExtras();
		if (ADD == mode) {
			setTitle("添加新学生");
		}
		if (EDIT == mode) {
			// student = (Student) intent.getSerializableExtra("student");
			setTitle("编辑学生信息");
			oldSid = intent.getExtras().getString("sid");
			student = all.dbManager.queryStudent(oldSid);
			showTextInEt();
		}
	}

	private Student getStudent() {
		Student student = new Student();
		student.sid = et_sid.getText().toString();
		student.name = et_name.getText().toString();
		student.phone = et_phone.getText().toString();
		student.classPost = et_classPost.getText().toString();
		student.schoolPost = et_schoolPost.getText().toString();
		student.politicalStatus = et_politicalStatus.getText().toString();
		student.PartyClass = et_PartyClass.getText().toString();
		return student;
	}

	private void getWidget() {
		et_sid = (EditText) findViewById(R.id.et_sid);
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_classPost = (EditText) findViewById(R.id.et_classPost);
		et_schoolPost = (EditText) findViewById(R.id.et_schoolPost);
		et_politicalStatus = (EditText) findViewById(R.id.et_politicalStatus);
		et_PartyClass = (EditText) findViewById(R.id.et_PartyClass);
//		bt_ok = (Button) findViewById(R.id.bt_ok);
//		bt_cancel = (Button) findViewById(R.id.bt_cancel);
	}

	private void showTextInEt() {
		et_sid.setText(student.sid);
		et_name.setText(student.name);
		et_phone.setText(student.phone);
		et_classPost.setText(student.classPost);
		et_schoolPost.setText(student.schoolPost);
		et_politicalStatus.setText(student.politicalStatus);
		et_PartyClass.setText(student.PartyClass);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater a = getMenuInflater();
		a.inflate(R.menu.menu_stuinfo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.back:
			back();
			break;
		case R.id.save:
			save();
			break;
		default:
			break;
		}
		return true;
	}

}
