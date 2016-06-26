package com.example.tianlai;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class m_team extends Activity{
	private Button button1=null,button2,button3,button4,button5,button6,play,next,pre;
	private AbsoluteLayout Lmusic;
	private Integer[] mp3={R.raw.aaa,R.raw.test,R.raw.test1};
	private int position=0;
	private ListView listview;
	private List list;
	private MusicService myservice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题  
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏 
        setContentView(R.layout.m_team);
        MainActivity.activityList.add(this);
        getWidget();
        setWidget();
        showlistview();
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
        listview=(ListView)findViewById(R.id.listView1);
        list=getData();
        play.setOnClickListener(new play());
        next.setOnClickListener(new next());
        pre.setOnClickListener(new pre());
        connection();
    }
 
    class menu_Button2 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_team.this,MainActivity.class); 
			startActivity(open);			
		}	
    }
    class menu_Button3 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_team.this,m_introduce.class);
			startActivity(open);
		}	
    }
    class menu_Button4 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_team.this,m_map.class);
			startActivity(open);
		}	
    }
    class menu_Button5 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_team.this,m_team.class);
			startActivity(open);
		}	
    }
    class menu_Button6 implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(m_team.this,m_contact.class);
			startActivity(open);
		}	
    }
    
    private List<Map<String, Object>> getData() { 
    	
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        String[] type,cname;
        Integer[] mImageIds = {R.drawable.fxlogo,R.drawable.zllogo,R.drawable.aksdlogo,
        	     R.drawable.rqlogo,R.drawable.tylogo};

        type=getResources().getStringArray(R.array.type);
        cname=getResources().getStringArray(R.array.cname);
        for(int i=0;i<5;i++)
        {
        	Map<String, Object> map = new HashMap<String, Object>();
	        map.put("type",type[i]);   
	        map.put("cname", cname[i]);
	        map.put("img", mImageIds[i]); 
	        list.add(map);
        } 
        return list; 
    } 
    private void showlistview()
    {
    	SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.list_team,new String[] {"type","cname", "img"},     
                new int[] {R.id.type,R.id.cname,R.id.imageView1} );
    	listview.setAdapter(adapter);
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
