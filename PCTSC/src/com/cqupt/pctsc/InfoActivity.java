package com.cqupt.pctsc;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class InfoActivity extends Activity {
	
	public static EquipmentInfo equipmentInfo;
	public static String md5;
	public static InternetHelper internetHelper;
	public static UIHelper uiHelper;
	public static Button picButton;
	public static Button gpsButton;
	public static Handler gpsHandler;
	public static Handler picHandler;
	public static Handler showHandler;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        Declare declare = (Declare) getApplicationContext();
        equipmentInfo = declare.getEquipmentInfo();
        md5 = declare.getUserInfo().getMd5();
        internetHelper = new InternetHelper();
        uiHelper = new UIHelper(this, R.id.pic, R.id.infoedit);
        gpsButton = (Button)findViewById(R.id.refreshinfo);
        picButton = (Button)findViewById(R.id.refreshpic);
        gpsButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Thread(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						setEquipmentInfo();
					}
	        		
	        	}.start();
			}
		});
        picButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						setPicInfo();
					}
	        		
	        	}.start();
			}
		});
        
        gpsHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				uiHelper.setGPSData((GPSInfo)msg.obj);
			}
        	
        };
        picHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				uiHelper.setImage((Bitmap)msg.obj);
			}
        	
        };
        showHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Toast.makeText(InfoActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
			}
        	
        };
    }
    
    public void setEquipmentInfo(){
    	GPSInfo gpsInfo;
    	try {
    		String backString = internetHelper.getGPSData(equipmentInfo.getEID(), md5);
			gpsInfo = MyTool.initGPSInfo(backString);
			if(gpsInfo == null){
				show("没有数据");
				return ;
			}
			Message message = new Message();
			message.obj = gpsInfo;
			gpsHandler.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			show("获取失败");
			e.printStackTrace();
		}
    }
    
    public void setPicInfo(){
    	PictureInfo pictureInfo;
        try {
			String backString = internetHelper.getPicData(equipmentInfo.getEID(), md5);
			pictureInfo = MyTool.initPictureInfo(backString);
			if(pictureInfo == null){
				show("没有数据");
				return ;
			}
			Bitmap bitmap = uiHelper.getImageView(pictureInfo.getPicPath());
			Message message = new Message();
			message.obj = bitmap;
			picHandler.sendMessage(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			show("获取失败");
			e.printStackTrace();
		}
    }
    
    public void show(String string){
    	Message message = new Message();
		message.obj = string;
		showHandler.sendMessage(message);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		return super.onMenuItemSelected(featureId, item);
	}
}
