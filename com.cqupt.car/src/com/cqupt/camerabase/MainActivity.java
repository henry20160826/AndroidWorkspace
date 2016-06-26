package com.cqupt.camerabase;

import java.io.IOException;

import com.bky.camerabase.R;
import com.cqupt.canbus.Linux;


import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final String EID = "10001";	//机器编号，固定5位数
	private RelativeLayout relativeLayout;
	private MySurfaceView mySurfaceView;
	private Camera camera;
	private int numOfCamera=0;
	private Handler message_handler;
	private Handler takePic_handler;
	private GPSHelper gpsHelper;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		message_handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
			}
			
		};
		takePic_handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				camera.takePicture(null, null, jpegCallback);
			}
        	
        };
		gpsHelper = new GPSHelper(this);
		camera = getCameraInstance(numOfCamera);
		if(camera == null){
			return ;
		}
		//camera.setDisplayOrientation(90);
		relativeLayout = (RelativeLayout)findViewById(R.id.all);
		//startCamera();
		com.cqupt.canbus.Linux.openUart(2);
	}
	
	public void startCamera(){
		if(mySurfaceView == null){
			mySurfaceView = new MySurfaceView(this);
			relativeLayout.addView(mySurfaceView);
		}
	}
	
	public void stopCamera(){
		if(mySurfaceView != null){
			camera.stopPreview();
			relativeLayout.removeView(mySurfaceView);
			mySurfaceView = null;
		}
	}
	
	public void closeCamera(){
      	if (camera != null) {
      		camera.stopPreview();
      		camera.release();
      		camera = null;
      	}
	}
	
	public void sendLocation(Location location){
		String data = location.getLongitude() + "#" + location.getLatitude();
		new SendDataThread(data);
	}

	public void takePic(){
		Message message = new Message();
		takePic_handler.sendMessage(message);
	}
	
	public void show(String msg){
		Message message = new Message();
		message.obj = msg;
		message_handler.sendMessage(message);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.menu_1){
			startCamera();
		}else if(item.getItemId() == R.id.menu_2){
			stopCamera();
		}else if(item.getItemId() == R.id.menu_3){
			Location location = gpsHelper.getLastLocation();
			if(location != null){
				sendLocation(location);
				System.out.println("经度：" + location.getLongitude() + "纬度：" + location.getLatitude());
			}else{
				show("请打开GPS!");
			}
		}else if(item.getItemId() == R.id.menu_4){
			takePic();
		}else if(item.getItemId() == R.id.menu_5){
			camera.startPreview();
		}else if(item.getItemId() == R.id.menu_6){
			camera=changeCamera();
			stopCamera();
			startCamera();
		}else if(item.getItemId() == R.id.menu_7){
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	 /** 安全获取Camera对象实例*/ 
    public Camera getCameraInstance(int num){ 
        Camera c = null; 
        try { 
            c = Camera.open(num); // 试图获取Camera实例
            System.out.println(Camera.getNumberOfCameras());
        } catch (Exception e){ 
        	System.out.println(e);
        	show("摄像头不可用");
        } 
        return c; // 不可用则返回null
    }
    
    public Camera changeCamera()
    {
    	if(1==Camera.getNumberOfCameras()){
    		return camera;
    	}
    	else {
    		closeCamera();
    		numOfCamera = 1 - numOfCamera;
			return getCameraInstance(numOfCamera);
		}
    }
    
    @Override
  	protected void onDestroy() {
  		// TODO Auto-generated method stub
    	closeCamera();
  		super.onDestroy();
  	}
    
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		gpsHelper.stopListen();
		System.out.println("关闭GPS监听");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		gpsHelper.startListen();
		System.out.println("开始GPS监听");
	}

	private PictureCallback jpegCallback = new PictureCallback(){

        public void onPictureTaken(byte[] data, Camera camera) {
                Parameters ps = camera.getParameters();
                if(ps.getPictureFormat() == PixelFormat.JPEG){
                	new SendDataThread(data);
                }
        }
	};
	
	class SendDataThread extends Thread{

		byte[] dataPic;
		String dataGPS;
		boolean isPic;
		
		public SendDataThread(byte[] data){
			this.dataPic = data;
			isPic = true;
			this.start();
		}
		
		public SendDataThread(String data){
			this.dataGPS = data;
			isPic = false;
			this.start();
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(isPic){
				String string = InternetHelper.sendPic(dataPic);
				if(string == null){
					show("上传照片失败");
				}
			}else{
				InternetHelper.sendData(dataGPS);
			}
		}
		
	}
	
	class MySurfaceView extends SurfaceView implements Callback{

		private SurfaceHolder holder;
		
		public MySurfaceView(Context context) {
			super(context);
			holder = getHolder();
			holder.addCallback(this);
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			try {
				camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				if(camera != null){
					camera.release();
					camera = null;
				}
				show("启动界面出现异常");
			}
			camera.startPreview();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (camera != null) {
		        camera.stopPreview();
		    }
		}
		
	}

}
