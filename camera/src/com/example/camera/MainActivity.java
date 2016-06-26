package com.example.camera;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements SurfaceHolder.Callback{
	private final static int MENU_MRCAMERA = Menu.FIRST;
	private final static int MENU_MRPHOTO = Menu.FIRST+1;
	private final static int MENU_ABOUT = Menu.FIRST+2;
	private final static int MENU_EXIT = Menu.FIRST+3;
	private boolean bTE = false;
	private Rect rc = new Rect(0, 380, 320, 480);
	private SurfaceHolder sfHolder;
	private Camera camera;
	private SurfaceView mr_sv;
	private ImageView mr_iv;
	private Bitmap jpg;
	private Bitmap markBmp;
	private OutputStream fos;
	private String mFileName;
	private Date mDate;
	private SimpleDateFormat mFmt;
	private static int IMG_PREVIEW_WIDTH = 480;
	private static int IMG_PREVIEW_HEIGHT = 320;
	private static int IMG_WIDTH = 1280;
	private static int IMG_HEIGHT = 960;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //getWindowManager().getDefaultDisplay().getMetrics(DisMet);
        
        mr_sv = (SurfaceView)findViewById(R.id.mr_sv);
        mr_iv = (ImageView)findViewById(R.id.mr_iv);
        
        Resources res = getResources();
        markBmp = BitmapFactory.decodeResource(res , R.drawable.ic_action_search);
        
        sfHolder = mr_sv.getHolder();
        sfHolder.addCallback(this);
        sfHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (!sdCardExist)
        {
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setMessage("请插入内存卡后重新再试!");
			builder.setTitle("提示");
			builder.setPositiveButton("确认", new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
					exit();
				}
			});
        }
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Camera.Parameters parameters = camera.getParameters();
		parameters.setPictureFormat(PixelFormat.JPEG);	// 设置图片的格式
		parameters.set("orientation", "portrait");
		parameters.setPreviewSize(IMG_PREVIEW_WIDTH, IMG_PREVIEW_HEIGHT);  	// 设置图片预览的高度和宽度
		parameters.setPictureSize(IMG_WIDTH, IMG_HEIGHT);	// 设置图片的高度和宽度
		parameters.setPreviewFrameRate(60);		// 设置图片预览的帧速
		camera.setParameters(parameters);
		camera.startPreview();
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		try {
			camera = Camera.open();
			camera.setPreviewDisplay(sfHolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		camera.stopPreview();
		camera = null;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//menu.add(0, MENU_MRCAMERA, 1, "大头贴");
		menu.add(0, MENU_MRPHOTO, 2, "相册");
		menu.add(0, MENU_ABOUT, 3, "关于");
		menu.add(0, MENU_EXIT, 3, "退出");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId())
		{
		case MENU_MRCAMERA:
			//mr_iv.setImageBitmap(markBmp);
			break;
		case MENU_MRPHOTO:
			break;
		case MENU_ABOUT:
			AlertDialog.Builder bd = new Builder(MainActivity.this);
			bd.setMessage("mrCamera.apk\n版本:1.0\n作者:mrandexe");
			bd.setTitle("关于");
			bd.setPositiveButton("确认", new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
				}
			});
			bd.create().show();
			break;
		case MENU_EXIT:
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setMessage("确认退出吗？");
			builder.setTitle("提示");
			builder.setPositiveButton("确认", new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
					exit();
				}
			});
			builder.setNegativeButton("取消", new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		if (event.getAction()==MotionEvent.ACTION_DOWN)
		{
			int x = (int)event.getX();
			int y = (int)event.getY();
			
			if (rc.contains(x, y))
			{
				bTE = !bTE;
				if (bTE)
				{
					camera.autoFocus(mAfc);
					camera.stopPreview();
					Toast.makeText(this, "拍摄照片储存中.", Toast.LENGTH_SHORT).show();
				}
				else
				{
					//mr_iv.setImageBitmap(null);
					camera.startPreview();
				}
			}
		}
		return super.onTouchEvent(event);
	}
	
	private AutoFocusCallback mAfc = new AutoFocusCallback()
	{

		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			// TODO Auto-generated method stub
			camera.takePicture(new shutter(), new raw(), new jpeg());
		}
		
	};
	class shutter implements Camera.ShutterCallback
	{
		@Override
		public void onShutter() {
			// TODO Auto-generated method stub
		}
		
	}
	
	class raw implements Camera.PictureCallback
	{

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
		}
	
	}
	
	class jpeg implements Camera.PictureCallback
	{
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			try {
				mDate = new Date(System.currentTimeMillis());
				mFmt = new SimpleDateFormat("MMddhhmmss");
				mFileName = mFmt.format(mDate);
				//Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
				jpg = BitmapFactory.decodeByteArray(data, 0, data.length);
				//jpg = mixtureBitmap(bm, markBmp);
				//mr_iv.setImageBitmap(jpg);
				File fPath = new File(Environment.getExternalStorageDirectory().toString()+"/mrCamera/");
				if (!fPath.exists())
				{
					fPath.mkdir();
				}
				File fName = new File(fPath, mFileName+".jpg");
				if (!fName.exists())
				{
					fName.createNewFile();
				}
				fos = new FileOutputStream(fName);
				jpg.compress(Bitmap.CompressFormat.JPEG, 100, fos );
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			}
			
		}
	
	public Bitmap mixtureBitmap(Bitmap bmp, Bitmap mark)
	{
		int sW = bmp.getWidth();
		int sH = bmp.getHeight();
		Bitmap newBmp = Bitmap.createBitmap(sW, sH, Config.ARGB_8888);
		Canvas cv = new Canvas(newBmp);
		cv.drawBitmap(bmp, 0, 0, null);
		cv.drawBitmap(mark, 0, 0, null);
		cv.save(Canvas.ALL_SAVE_FLAG);
		cv.restore();
		return newBmp;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
		{
			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    
	private void exit()
	{
		ActivityManager actMgr = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
		actMgr.restartPackage(getPackageName());
	}
}
