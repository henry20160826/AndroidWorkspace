package com.demo.zpcy;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Spinner spinner1;
	private Button loginButton, registerButton, hylbButton, zxtzButton,
			bxjrButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		dealSpinner();
		dealButton();
		adjustView();
	}

	private void dealSpinner() {
		// 设置spinner
		ArrayAdapter<CharSequence> arrayAdapter;
		arrayAdapter = ArrayAdapter.createFromResource(this, R.array.hangye,
				android.R.layout.simple_spinner_item);
		// ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
		// R.layout.spinner);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setAdapter(arrayAdapter);
		spinner1.setPrompt("行业类别");
	}

	private void adjustTextview() {
		int width = spinner1.getWidth();
		if (0 != width) {
			TextView textView1 = (TextView) findViewById(R.id.textView1);
			textView1.setWidth(width);
			TextView textView2 = (TextView) findViewById(R.id.textView2);
			textView2.setWidth(width);
		}
	}

	private void dealButton() {
		loginButton = (Button) findViewById(R.id.bt_login);
		registerButton = (Button) findViewById(R.id.bt_register);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.dialog_login,
						(ViewGroup) findViewById(R.id.dialog_login));
				new AlertDialog.Builder(MainActivity.this).setTitle("登录")
						.setView(layout).show();
			}
		});
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.dialog_register,
						(ViewGroup) findViewById(R.id.dialog_register));
				new AlertDialog.Builder(MainActivity.this).setTitle("注册")
						.setView(layout).show();
			}
		});

		hylbButton = (Button) findViewById(R.id.bt_hylb);
		zxtzButton = (Button) findViewById(R.id.bt_zxtz);
		bxjrButton = (Button) findViewById(R.id.bt_bxjr);

		hylbButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
			}
		});
	}

	private void adjustView() {
		ViewTreeObserver vto = spinner1.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				adjustTextview();
				return true;
			}
		});
	}
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_main, menu);
	// return true;
	// }
}
