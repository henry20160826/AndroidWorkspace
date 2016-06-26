package com.hu.activity;

import static com.hu.dataclass.Content.CONNECTION;
import static com.hu.dataclass.Content.SERVER_FOR_YOU;
import static com.hu.dataclass.Content.TABLE_NUM;
import static com.hu.dataclass.Content.WAITER_NAME;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.order_dishes.R;

public class BillActivity extends Activity {
	private int tableNum;
	private TextView usernameTextView, stateTextView;
	private TextView tableNumTextView;
	private String waiterName;
	private Button backButton, okButton;
	private TextView dishNumTextView, allMoneyTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_bill);
		getViews();
	}

	private void getViews() {

		Intent intent = getIntent();

		usernameTextView = (TextView) findViewById(R.id.tv_username);
		stateTextView = (TextView) findViewById(R.id.tv_state);

		waiterName = intent.getExtras().getString(WAITER_NAME);
		usernameTextView.setText(waiterName + SERVER_FOR_YOU);
		stateTextView.setText(CONNECTION);

		tableNumTextView = (TextView) findViewById(R.id.tv_tablenum);
		tableNum = intent.getExtras().getInt(TABLE_NUM);
		tableNumTextView.setText(tableNum + "号桌");

		dishNumTextView=(TextView)findViewById(R.id.tv_dishNum);
		dishNumTextView.setText("共0道菜");
		
		allMoneyTextView=(TextView)findViewById(R.id.tv_allMoney);
		allMoneyTextView.setText("共0元");
		
		backButton = (Button) findViewById(R.id.bt_back);
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		okButton = (Button) findViewById(R.id.bt_ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
