package com.hu.activity;

import com.example.order_dishe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DishesActivity extends Activity {

	private int tableNum;
	private TextView tableNumTextView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dishes);
		getViews();
		getTableNum();
	}
	private void getViews()
	{
		tableNumTextView=(TextView)findViewById(R.id.tv_tableNum);
	}
	private void getTableNum()
	{
		Intent intent=getIntent();
		tableNum=intent.getExtras().getInt("tableNum");
		tableNumTextView.setText(tableNum+"ºÅ×À");
	}
}
