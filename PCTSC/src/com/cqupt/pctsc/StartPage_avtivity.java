package com.cqupt.pctsc;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class StartPage_avtivity extends Activity {
	private ImageView changePic = null;
	private ProgressBar  theFirstBar = null;
	Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_page_avtivity);
		changePic = (ImageView)findViewById(R.id.change_pic);
		theFirstBar = (ProgressBar)findViewById(R.id.progressBar1);
		handler.post(updateThread);

	}
	
	MyRunnable updateThread = new MyRunnable(this);
	
	class MyRunnable implements Runnable{
		Activity activity;
		int i = 0;
		int[ ] pic_arr = new int[]{R.drawable.start0, R.drawable.start1, R.drawable.start2,R.drawable.start3,R.drawable.start4,R.drawable.start5,R.drawable.start6,
				R.drawable.start7,R.drawable.start8,R.drawable.start9,R.drawable.start10,R.drawable.start11,R.drawable.start12,R.drawable.start13,
				R.drawable.start14,R.drawable.start15,R.drawable.start16,R.drawable.start17,R.drawable.start18,R.drawable.start19,R.drawable.start20};
		public MyRunnable(Activity activity){
			this.activity = activity;
		}
		public void run() {
			// TODO Auto-generated method stub
			changePic.setImageResource(pic_arr[i]);
			theFirstBar.setProgress(i);
			if(i<20){
				i++;
				handler.postDelayed(updateThread,70);
			}else{
				Intent intent = new Intent();
				intent.setClass(activity, LoginActivity.class);
				activity.startActivity(intent);
				finish();
			}
			//System.out.println(pic_arr[1]);
		}
	}
}
