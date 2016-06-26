package com.hu.Activity;

import static com.hu.DataClass.Content.*;
import java.nio.Buffer;
import java.security.Policy.Parameters;

import com.example.passwordmanager.R;
import com.hu.DataClass.All;
import com.hu.Listener.CancelButtonListener;
import com.hu.encode.Cipher;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditActivity extends Activity {

	private EditText[] editText;
	private String id;
	private All all;
	private ImageButton okButton, cancelButton;
	private int mode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		dealViews();
		getInfo();
		// String yuanwen = "¹þ¹þ";
		// String base64String = Cipher.encode(yuanwen);
		// System.out.println(base64String);
		//
		// String yString = Cipher.decode(base64String);
		// System.out.println(yString);
	}

	private void dealViews() {
		getEditTexts();
		dealButton();
	}

	private void dealButton() {
		okButton = (ImageButton) findViewById(R.id.bt_ok);
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] newStrings = new String[4];
				for (int i = 0; i < 4; i++) {
					newStrings[i] = editText[i].getText().toString();
				}
				if (EDIT == mode) {
					all.dbManager.updateOneItem(id + "", newStrings);
				}
				if (NEW == mode) {
					int flag = 0;
					for (int i = 0; i < 4; i++) {
						if (newStrings[i].equals("")) {
							flag++;
						}
					}
					if (flag != 4) {
						all.dbManager.insertOneItem(newStrings);
					}
				}
				finish();
			}
		});

		cancelButton = (ImageButton) findViewById(R.id.bt_cancel);
		cancelButton.setOnClickListener(new CancelButtonListener(EditActivity.this));
	}

	private void getInfo() {
		all = (All) getApplication();
		Intent intent = getIntent();
		mode = intent.getExtras().getInt("mode");
		if (EDIT == mode) {
			id = intent.getExtras().getString("pkey");
			String[] strings = all.dbManager.getOneItem(id);
			setinfo(strings);
		}
	}

	private void getEditTexts() {
		editText = new EditText[] { (EditText) findViewById(R.id.editText1),
				(EditText) findViewById(R.id.editText2),
				(EditText) findViewById(R.id.editText3),
				(EditText) findViewById(R.id.editText4), };
	}

	private void setinfo(String[] strings) {
		for (int i = 0; i < 4; i++) {
			editText[i].setText(strings[i]);
			// editText[i].setFocusable(false);
		}
	}

}
