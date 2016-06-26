package com.hu.Listener;


import android.app.Activity;
import android.view.View;

public class CancelButtonListener implements View.OnClickListener{
	
	private Activity activity;
	
	public CancelButtonListener(Activity activity)
	{
		this.activity=activity;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		activity.finish();
	}

}
