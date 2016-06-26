package com.cqupt.tool;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UIHelper {
	
	public static Activity uiActivity;
	public static InternetHelper internetHelper;
	public static ImageView carPhoto;
	public static TextView GPSInfoTextView;
	
	public UIHelper(Activity uiActivity, int imageViewId, int gpsViewId){
		UIHelper.uiActivity = uiActivity;
		carPhoto = (ImageView)uiActivity.findViewById(imageViewId);
		GPSInfoTextView = (TextView)uiActivity.findViewById(gpsViewId);
		internetHelper = new InternetHelper();
	}
	
	/*����GPS����*/
	public void setGPSData(GPSInfo gpsInfo){
		if(gpsInfo == null){
			Toast.makeText(uiActivity, "û�в鵽GPS����", Toast.LENGTH_SHORT).show();
			return ;
		}
		GPSInfoTextView.setText(gpsInfo.toString());
	}
	
	public void setImage(Bitmap bitmap){
		if(bitmap == null){
			return ;
		}
		carPhoto.setImageBitmap(bitmap);
	}
	
	/*������������һ��ͼƬ*/
	public Bitmap getImageView(String url){
		if(url == null){
			Toast.makeText(uiActivity, "��ȡͼƬʧ��", Toast.LENGTH_SHORT).show(); 
			return null;
		}
    	try { 
    		byte[] data = internetHelper.getImage(url); 
    		int length = data.length; 
    		Bitmap bitMap = BitmapFactory.decodeByteArray(data, 0, length); 
    		return bitMap;
    	} catch (Exception e) { 
    		Toast.makeText(uiActivity, "��ȡͼƬʧ��", Toast.LENGTH_SHORT).show(); 
    		return null;
    	} 
    }
}
