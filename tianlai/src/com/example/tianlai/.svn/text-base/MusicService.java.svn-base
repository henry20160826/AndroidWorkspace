package com.example.tianlai;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
 
public class MusicService extends Service {
    private MediaPlayer mp;
    private Integer[] mp3={R.raw.aaa,R.raw.test,R.raw.test1};
    private int position=0,action=1;
    private final IBinder binder = new MyBinder(); 

    public class MyBinder extends Binder { 
    	MusicService getService() { 
            return MusicService.this; 
        } 
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return binder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mp=MediaPlayer.create(this,R.raw.aaa);
        mp.setLooping(true);
        Log.v("Myservice", "onCreate");
    }
    @Override  
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.v("Myservice", "onStart");
    } 
    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
        Log.v("Myservice", "onDestroy");
    }
    public void next()
    {
		position++;
		if(position>2)
		{
			position=0;
		}
		mp.reset();
		mp=MediaPlayer.create(this, mp3[position]);
		mp.start();
    }
    
    public void pre()
    {
		position--;
		if(position<0)
		{
			position=2;
		}
		mp.reset();
		mp=MediaPlayer.create(this, mp3[position]);
		mp.start();
    }
    public boolean play()
    {
		if(mp.isPlaying())
		{
			mp.pause();
			return true;
		}
		else
		{
			mp.start();
			return false;
		}
    }
    public boolean check()
    {
		return mp.isPlaying();
    }
    public int getnum()
    {
    	return position;
    }
}