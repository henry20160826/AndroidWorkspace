package com.hu.activity;

import static com.hu.dataclass.Content.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.order_dishes.R;
import com.hu.encode.MD5;
import com.hu.udp.UDP;

@SuppressLint("HandlerLeak")
public class LoginActivity extends Activity {

	private Button loginButton;
	private EditText nameEditText;
	private EditText passwordEditText;
	private Handler myHandler;
	private UDP udp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_login);
		getViews();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		udp = new UDP(myHandler);
		udp.receiveRun();
	}

	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		udp.setreceiveRunFlag(false);
		udp.close();
	}

	private void getViews() {
		loginButton = (Button) findViewById(R.id.bt_login);
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 检查用户名密码
				// check();

				Bundle bundle = new Bundle();
				bundle.putString(WAITER_NAME, nameEditText.getText().toString());

				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(LoginActivity.this, TableActivity.class);
				startActivity(intent);
			}
		});
		nameEditText = (EditText) findViewById(R.id.et_name);
		passwordEditText = (EditText) findViewById(R.id.et_password);

		myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					Bundle bundle = new Bundle();
					bundle.putString(WAITER_NAME, nameEditText.getText()
							.toString());

					Intent intent = new Intent();
					intent.putExtras(bundle);
					intent.setClass(LoginActivity.this, TableActivity.class);
					startActivity(intent);
					finish();
					break;
				case 404:
					Toast.makeText(LoginActivity.this, "用户名或密码错误",
							Toast.LENGTH_SHORT).show();
					break;
				default:

					break;
				}
			}
		};

	}

	private void check() {
		// TODO 联网验证用户名密码
		final String name = nameEditText.getText().toString();
		final String password = passwordEditText.getText().toString();

		if (!name.isEmpty() && !password.isEmpty()) {
			String nameEncode = MD5.getMD5(name);
			String passwordEncode = MD5.getMD5(password);

			udp.sendNameAndPassword(nameEncode, passwordEncode);
		} else {
			Toast.makeText(LoginActivity.this, CAN_NOT_EMPTY, Toast.LENGTH_LONG)
					.show();
		}
	}
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_main, menu);
	// return true;
	// }
}
