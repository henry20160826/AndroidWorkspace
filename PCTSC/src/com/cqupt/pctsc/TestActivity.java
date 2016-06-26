package com.cqupt.pctsc;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TestActivity extends Activity {
	
	public static UIHelper uiHelper;
	public static InternetHelper internetHelper;
	public static UserInfo userInfo;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        internetHelper = new InternetHelper();
        uiHelper = new UIHelper(this , R.id.carPhoto , R.id.GPSInfo);
        //doLogin();
        //refreshPic();
        //refreshGPS();
    }
    
    public void doLogin(){
        try {
        	userInfo = MyTool.initUserInfo(internetHelper.login("admin", "admin"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "Õ¯¬Á¥ÌŒÛ£°", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
        if(userInfo != null){
        	System.out.println(userInfo);
        }else {
        	Toast.makeText(this, "”√ªß√˚√‹¬Î¥ÌŒÛ£°", Toast.LENGTH_SHORT).show();
		}
    }
    
    public void refreshPic(){
    	 PictureInfo pictureInfo;
         try {
 			String backString = internetHelper.getPicData(userInfo.getEquipmentInfos().get(0).getEID(), userInfo.getMd5());
 			pictureInfo = MyTool.initPictureInfo(backString);
 			//uiHelper.setCarPhoto(pictureInfo.getPicPath());
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }
    
    public void refreshGPS(){
    	GPSInfo gpsInfo;
    	try {
    		String backString = internetHelper.getGPSData(userInfo.getEquipmentInfos().get(0).getEID(), userInfo.getMd5());
			gpsInfo = MyTool.initGPSInfo(backString);
			uiHelper.setGPSData(gpsInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		finish();
		return super.onMenuItemSelected(featureId, item);
	}
    
}
