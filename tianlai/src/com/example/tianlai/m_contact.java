package com.example.tianlai;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class m_contact extends Activity{
	private Button button1=null,button2,button3,button4,button5,button6,play,next,pre;
	private AbsoluteLayout Lmusic;
	private MusicService myservice;
	private RelativeLayout zhiyin,guanyin4,guanyin5,heishan6,heishan7;
	private TextView guanyin,heishan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题  
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏 
        setContentView(R.layout.m_contact);
        MainActivity.activityList.add(this);
        getWidget();
        setWidget(); 
	}
    void getWidget()
    {
    	Lmusic=(AbsoluteLayout)findViewById(R.id.music);
        button1=(Button)findViewById(R.id.button1); 
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3); 
        button4=(Button)findViewById(R.id.button4); 
        button5=(Button)findViewById(R.id.button5); 
        button6=(Button)findViewById(R.id.button6);
        zhiyin=(RelativeLayout)findViewById(R.id.relativeLayout2);
        guanyin4=(RelativeLayout)findViewById(R.id.relativeLayout4);
        guanyin5=(RelativeLayout)findViewById(R.id.relativeLayout5);
        heishan6=(RelativeLayout)findViewById(R.id.relativeLayout6);
        heishan7=(RelativeLayout)findViewById(R.id.relativeLayout7);
        guanyin=(TextView)findViewById(R.id.textView5);
        heishan=(TextView)findViewById(R.id.textView6);
        play=(Button)findViewById(R.id.play);
        next=(Button)findViewById(R.id.next);
        pre=(Button)findViewById(R.id.pre);
    }
    void setWidget()
    {
        button1.setOnClickListener(new musicOk());
        button2.setOnClickListener(new menu_Button2());
        button3.setOnClickListener(new menu_Button3());
        button4.setOnClickListener(new menu_Button4());
        button5.setOnClickListener(new menu_Button5());
        button6.setOnClickListener(new menu_Button6());
        zhiyin.setOnClickListener(new call());
        guanyin4.setOnClickListener(new call());
        heishan6.setOnClickListener(new call());
        guanyin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(guanyin5.getVisibility()==View.VISIBLE)
				{
					guanyin4.setVisibility(View.GONE);
					guanyin5.setVisibility(View.GONE);
				}
				else
				{
					guanyin4.setVisibility(View.VISIBLE);
					guanyin5.setVisibility(View.VISIBLE);
				}
			}
		});
        heishan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(heishan6.getVisibility()==View.VISIBLE)
				{
					heishan6.setVisibility(View.GONE);
					heishan7.setVisibility(View.GONE);
				}
				else
				{
					heishan6.setVisibility(View.VISIBLE);
					heishan7.setVisibility(View.VISIBLE);
				}
			}
		});
        play.setOnClickListener(new play());
        next.setOnClickListener(new next());
        pre.setOnClickListener(new pre());
        connection();
    }
    
    class call implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18716321825"));
			m_contact.this.startActivity(intent);
		}
    	
    }
    class menu_Button2 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_contact.this,MainActivity.class);
			startActivity(open);
		}	
    }
    class menu_Button3 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_contact.this,m_introduce.class);
			startActivity(open);
		}	
    }
    class menu_Button4 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_contact.this,m_map.class);
			startActivity(open);
		}	
    }
    class menu_Button5 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_contact.this,m_team.class);
			startActivity(open);
		}	
    }
    class menu_Button6 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_contact.this,m_contact.class);
			startActivity(open);
		}	
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK){ 
        	MainActivity.exitClient();
            return true; 
        }else{        
            return super.onKeyDown(keyCode, event); 
        } 

	}
	//音乐*************************************************************
    class musicOk implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(Lmusic.getVisibility()==View.VISIBLE)
			{
				Lmusic.setVisibility(View.GONE);
			}
			else
			{
				Lmusic.setVisibility(View.VISIBLE);
			}
			playIconChange();
		}  	
    }
    class play implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(myservice!=null)
			{
				if(myservice.play())
				{
					play.setBackgroundResource(R.drawable.play);
				}
				else
				{
					play.setBackgroundResource(R.drawable.pause);
				}
			}
		}
    	
    }
    void playIconChange()
    {
    	if(myservice.check())
    	{
    		play.setBackgroundResource(R.drawable.pause);   		
    	}
    	else
    	{
    		play.setBackgroundResource(R.drawable.play);
    	}
    }
    class next implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			myservice.next();
			playIconChange();
		}    	
    }
    class pre implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			myservice.pre();
			playIconChange();
		}  	
    }
	private void connection(){ 
        Intent intent = new Intent();
        intent.setClass(this, MusicService.class);
        bindService(intent,sc,Service.BIND_AUTO_CREATE);  
        startService(intent);
    }
	
	private ServiceConnection sc = new ServiceConnection() { 
        @Override
        public void onServiceDisconnected(ComponentName name) { 
            myservice = null; 
        } 
          
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) { 
            myservice = ((MusicService.MyBinder)(service)).getService(); 
        } 
    }; 
        
}
