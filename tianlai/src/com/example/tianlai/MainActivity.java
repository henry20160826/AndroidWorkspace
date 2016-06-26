package com.example.tianlai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity{
	public static List<Activity> activityList = new ArrayList<Activity>();
	private Button button1,button2,button3,button4,button5,button6,play,next,pre;
	private AbsoluteLayout Lmusic;
	private ListView listview;
	private List list;
	private ViewFlipper filpper_main;
	private GestureDetector detector;
	private Animation slideLeftIn,slideLeftOut,slideRightIn,slideRightOut;
	private MusicService myservice;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题  
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏 
        setContentView(R.layout.main);
        activityList.add(this);
        getWidget();
        setWidget();      
        showlistview();       
    }
    void getWidget()
    {   	
        filpper_main=(ViewFlipper)findViewById(R.id.viewFlipper1); 
        Lmusic=(AbsoluteLayout)findViewById(R.id.music);
        button1=(Button)findViewById(R.id.button1); 
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3); 
        button4=(Button)findViewById(R.id.button4); 
        button5=(Button)findViewById(R.id.button5); 
        button6=(Button)findViewById(R.id.button6);
        play=(Button)findViewById(R.id.play);
        next=(Button)findViewById(R.id.next);
        pre=(Button)findViewById(R.id.pre);
        slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
        slideRightIn = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
        slideRightOut = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
    }
    void setWidget()
    {
        button1.setOnClickListener(new musicOk());
        button2.setOnClickListener(new menu_Button2());
        button3.setOnClickListener(new menu_Button3());
        button4.setOnClickListener(new menu_Button4());
        button5.setOnClickListener(new menu_Button5());
        button6.setOnClickListener(new menu_Button6());
        
        listview=(ListView)findViewById(R.id.listView1);
        list=getData(); 
        
        detector=new GestureDetector(new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				// TODO Auto-generated method stub
				if (e1.getX() - e2.getX() > 120) {
		            filpper_main.setInAnimation(slideRightIn);  
		            filpper_main.setOutAnimation(slideLeftOut); 
		            filpper_main.showNext();  
		        } 
				else if (e1.getX() - e2.getX() < -120) { 
		            filpper_main.setInAnimation(slideLeftIn);  
		            filpper_main.setOutAnimation(slideRightOut); 
		            filpper_main.showPrevious(); 
				}
				return true;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
        filpper_main.setLongClickable(true);
        filpper_main.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return detector.onTouchEvent(event);
			}
		});
        play.setOnClickListener(new play());
        next.setOnClickListener(new next());
        pre.setOnClickListener(new pre());	
        connection();
        
    }
        

    private List<Map<String, Object>> getData() { 
    	
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        String[] stitle;
        Integer[] mImageIds = { R.drawable.tianlai_tb1, R.drawable.tianlai_tb2,
        	    R.drawable.tianlai_tb3, R.drawable.tianlai_tb4};

        stitle=getResources().getStringArray(R.array.home_stitle);
        for(int i=0;i<4;i++)
        {
        	Map<String, Object> map = new HashMap<String, Object>();
	        map.put("stitle",stitle[i]);                 
	        map.put("img", mImageIds[i]); 
	        map.put("btn", R.drawable.go_call);
	        list.add(map);
        } 
        return list; 
    } 
    private void showlistview()
    {
    	SpecialAdapter adapter=new SpecialAdapter(this,list,R.layout.list_home,new String[] {"stitle","img", "btn"},     
                new int[] {R.id.stitle,R.id.img,R.id.btn} );
    	listview.setAdapter(adapter);
    }

    class menu_Button2 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}	
    }
    class menu_Button3 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();		
			open.setClass(MainActivity.this,m_introduce.class); 
			startActivity(open);
		}	
    }
    class menu_Button4 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(MainActivity.this,m_map.class);
			startActivity(open);
		}	
    }
    class menu_Button5 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(MainActivity.this,m_team.class);  
			startActivity(open);
		}	
    }
    class menu_Button6 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(MainActivity.this,m_contact.class);
			startActivity(open);
		}	
    }
//关闭**************************************************************
    public static void exitClient()
    {
        // 关闭所有Activity
        for (int i = 0; i < activityList.size(); i++)
        {
            if (null != activityList.get(i))
            {
                activityList.get(i).finish();
            }
        }
        System.exit(0);//Android的程序只是让Activity finish()掉,而单纯的finish掉,退出并不完全
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK){ 
        	exitClient();
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
