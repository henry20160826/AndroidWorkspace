package com.cqupt.activity;

import java.io.IOException;

import com.cqupt.myclass.Declare;
import com.cqupt.pctsc.R;
import com.cqupt.pctsc.R.drawable;
import com.cqupt.pctsc.R.id;
import com.cqupt.pctsc.R.layout;
import com.cqupt.tool.InternetHelper;
import com.cqupt.tool.MyTool;
import com.cqupt.tool.UserInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity{

	public static EditText username;
	public static EditText password;
	public static ImageButton submit;
	public static InternetHelper internetHelper;
	public static Handler showHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		internetHelper = new InternetHelper();
		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		submit = (ImageButton)findViewById(R.id.submit);
		submit.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(arg1.getAction() == MotionEvent.ACTION_DOWN){  
					submit.setImageDrawable(getResources().getDrawable(R.drawable.submitdown));
		        }else if(arg1.getAction() == MotionEvent.ACTION_UP){  
		        	submit.setImageDrawable(getResources().getDrawable(R.drawable.submit));
		        	new Thread(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							submitAction();
						}
		        		
		        	}.start();
		        }  
				return false;
			}
		});
		showHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
			}
        	
        };
	}
	
	public void submitAction(){
		String usernameString = username.getText().toString();
		String passwordString = password.getText().toString();
		UserInfo userInfo = null;
		String backString = null;
		try {
			backString = internetHelper.login(usernameString, passwordString);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			show("ÍøÂç´íÎó");
			e1.printStackTrace();
			return ;
		}
		userInfo = MyTool.initUserInfo(backString);
		if(userInfo == null){
			show("ÓÃ»§Ãû»òÃÜÂë´íÎó");
			return ;
		}
		//Toast.makeText(this, "µÇÂ½³É¹¦", Toast.LENGTH_SHORT).show();
		Declare declare = (Declare) getApplicationContext();
		declare.setUserInfo(userInfo);
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, ChooseActivity.class);
		startActivity(intent);
	}
	
	public void show(String string){
    	Message message = new Message();
		message.obj = string;
		showHandler.sendMessage(message);
    }
	
}
