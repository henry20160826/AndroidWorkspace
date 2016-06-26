package com.hu.Activity;

import com.example.passwordmanager.R;
import com.hu.DataClass.All;
import com.hu.Listener.CancelButtonListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	private All all;
	private EditText newpsEditText, checkEditText,nowpsEditText;
	private ImageButton cancelButton, okButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		dealView();
	}

	private void dealView() {
		all=(All)getApplication();
		newpsEditText = (EditText) findViewById(R.id.et_newpas);
		checkEditText = (EditText) findViewById(R.id.et_check);
		nowpsEditText=(EditText)findViewById(R.id.et_nowps);
		dealButton();
	}

	private void dealButton() {
		cancelButton = (ImageButton) findViewById(R.id.bt_cancel);
		cancelButton.setOnClickListener(new CancelButtonListener(
				SettingsActivity.this));
		okButton = (ImageButton) findViewById(R.id.bt_ok);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				savePs();
			}
		});
	}

	private void savePs() {
		String nowpsString=nowpsEditText.getText().toString();
		String newpsString = newpsEditText.getText().toString();
		String checkString = checkEditText.getText().toString();
		
		if(all.dbManager.checkMypassword(nowpsString))
		{                                                                                                  
			if(newpsString.equals(checkString))
			{
				all.dbManager.setMyPassword(newpsString);
				Toast.makeText(SettingsActivity.this, "密码修改成功", Toast.LENGTH_LONG);
				finish();
			}
			else
			{
				Toast.makeText(SettingsActivity.this, "两次密码不一致", Toast.LENGTH_LONG)
				.show();
			}
		}
		else{
			Toast.makeText(SettingsActivity.this, "当前密码错误", Toast.LENGTH_LONG).show();
		}
		
	}
}
