package hu.Ktable;

import static hu.Ktable.MyConfig.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {

	private int weekday, time;
	private EditText name, teacher, room, kind, teachweek, last, state, note;
	private Button ok, cancel;
	private OnClickListener ls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		/*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		 */
		setContentView(R.layout.edit_info);
		getMyIntent();
		getItem();
		setEditText();
		setListener();
	}

	private void setListener() {
		ok.setOnClickListener(ls = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateke();
				Class_info.updateStrKe(weekday, time);
				finish();
			}

			private void updateke() {
				ke[weekday][time].data = new String[8];
				ke[weekday][time].data[0] = name.getText()
						.toString();
				ke[weekday][time].data[1] = teacher.getText()
						.toString();
				ke[weekday][time].data[2] = room.getText()
						.toString();
				ke[weekday][time].data[3] = kind.getText()
						.toString();
				ke[weekday][time].data[4] = teachweek.getText()
						.toString();
				ke[weekday][time].data[5] = last.getText()
						.toString();
				ke[weekday][time].data[6] = state.getText()
						.toString();
				ke[weekday][time].data[7] = note.getText()
						.toString();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog();
			}
		});
	}

	private void setEditText() {
		if (ke[weekday][time] != null) {
			if (ke[weekday][time].data != null) {
				name.setText(ke[weekday][time].data[0]);
				teacher.setText(ke[weekday][time].data[1]);
				room.setText(ke[weekday][time].data[2]);
				kind.setText(ke[weekday][time].data[3]);
				teachweek.setText(ke[weekday][time].data[4]);
				last.setText(ke[weekday][time].data[5]);
				state.setText(ke[weekday][time].data[6]);
				note.setText(ke[weekday][time].data[7]);
			}
		}
	}

	private void getMyIntent() {
		Intent intent = getIntent();
		weekday = intent.getIntExtra("week", 0);
		time = intent.getIntExtra("time", 0);
	}

	private void getItem() {
		name = (EditText) findViewById(R.id.editText1);
		teacher = (EditText) findViewById(R.id.editText2);
		room = (EditText) findViewById(R.id.editText3);
		kind = (EditText) findViewById(R.id.editText4);
		teachweek = (EditText) findViewById(R.id.editText5);
		last = (EditText) findViewById(R.id.editText6);
		state = (EditText) findViewById(R.id.editText7);
		note = (EditText) findViewById(R.id.editText8);
		ok = (Button) findViewById(R.id.ok);
		cancel = (Button) findViewById(R.id.cancel);
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确定放弃修改？").setTitle("提示")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).show();
	}

	/**
	 * 重写返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setTitle("提示")
					.setMessage("是否保存修改？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									ls.onClick(ok);
								}
							})
					.setNegativeButton("否",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									finish();
								}
							}).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}


}
