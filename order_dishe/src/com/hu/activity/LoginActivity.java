package com.hu.activity;

import com.example.order_dishe.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button loginButton;
	private EditText nameEditText;
	private EditText passwordEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getViews();
	}

	private void getViews() {
		loginButton = (Button) findViewById(R.id.bt_login);
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 检查用户名密码
				if (check()) {
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, TableActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(LoginActivity.this, "用户名或密码错误",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		nameEditText = (EditText) findViewById(R.id.et_name);
		passwordEditText = (EditText) findViewById(R.id.et_password);

	}

	private boolean check() {
		//TODO 联网验证用户名密码
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
