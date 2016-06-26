package com.hu.Activity;

import com.example.passwordmanager.R;
import com.hu.DBoperator.DatabaseManager;
import com.hu.DataClass.All;
import com.hu.encode.MD5;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	private All all;
	private ImageButton okButton;
	private EditText editText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		all = (All) getApplication();
		all.dbManager = new DatabaseManager(this);
		editText = (EditText) findViewById(R.id.et_password);
		getAsetButton();
	}

	private void getAsetButton() {

		okButton = (ImageButton) findViewById(R.id.bt_ok);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String editString = editText.getText().toString();
				if (all.dbManager.checkMypassword(editString)) {
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, ListviewActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(MainActivity.this, "√‹¬Î¥ÌŒÛ", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_main, menu);
	// return true;
	// }
}
