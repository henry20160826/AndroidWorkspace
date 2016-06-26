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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class m_introduce extends Activity {

	private ListView listview;
	private List list;
	private Button button1 = null, button2, button3, button4, button5, button6,
			play, next, pre;
	private AbsoluteLayout Lmusic;
	private MusicService myservice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// “˛≤ÿ±ÍÃ‚
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//…Ë÷√»´∆¡
		setContentView(R.layout.m_introduce);
		MainActivity.activityList.add(this);
		getWidget();
		setWidget();
		showlistview();
	}

	void getWidget() {
		Lmusic = (AbsoluteLayout) findViewById(R.id.music);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		listview = (ListView) findViewById(R.id.listView1);
		play = (Button) findViewById(R.id.play);
		next = (Button) findViewById(R.id.next);
		pre = (Button) findViewById(R.id.pre);
	}

	void setWidget() {
		button1.setOnClickListener(new musicOk());
		button2.setOnClickListener(new menu_Button2());
		button3.setOnClickListener(new menu_Button3());
		button4.setOnClickListener(new menu_Button4());
		button5.setOnClickListener(new menu_Button5());
		button6.setOnClickListener(new menu_Button6());
		play.setOnClickListener(new play());
		next.setOnClickListener(new next());
		pre.setOnClickListener(new pre());
		list = getData();
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 >= 0 && arg2 <= 11) {
					Intent open = new Intent();
					open.putExtra("num", arg2);
					open.setClass(m_introduce.this, m_introduce_lisview.class);
					startActivity(open);
				} else {
					Toast.makeText(m_introduce.this, "¥ÌŒÛ£°", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
		connection();
	}

	private List<Map<String, Object>> getData() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String[] stitle;
		stitle = getResources().getStringArray(R.array.introduce);
		for (int i = 0; i < 12; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("stitle", stitle[i]);
			map.put("btn", R.drawable.go_call);
			list.add(map);
		}
		return list;
	}

	private void showlistview() {
		SpecialAdapter adapter = new SpecialAdapter(this, list,
				R.layout.list_introduce, new String[] { "stitle", "btn" },
				new int[] { R.id.stitle, R.id.btn });
		listview.setAdapter(adapter);
	}

	class menu_Button2 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce.this, MainActivity.class);
			startActivity(open);
		}
	}

	class menu_Button3 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce.this, m_introduce.class);
			startActivity(open);
		}
	}

	class menu_Button4 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce.this, m_map.class);
			startActivity(open);
		}
	}

	class menu_Button5 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce.this, m_team.class);
			startActivity(open);
		}
	}

	class menu_Button6 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce.this, m_contact.class);
			startActivity(open);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MainActivity.exitClient();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	// “Ù¿÷*************************************************************
	class musicOk implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (Lmusic.getVisibility() == View.VISIBLE) {
				Lmusic.setVisibility(View.GONE);
			} else {
				Lmusic.setVisibility(View.VISIBLE);
			}
			playIconChange();
		}
	}

	class play implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (myservice != null) {
				if (myservice.play()) {
					play.setBackgroundResource(R.drawable.play);
				} else {
					play.setBackgroundResource(R.drawable.pause);
				}
			}
		}

	}

	void playIconChange() {
		if (myservice.check()) {
			play.setBackgroundResource(R.drawable.pause);
		} else {
			play.setBackgroundResource(R.drawable.play);
		}
	}

	class next implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			myservice.next();
			playIconChange();
		}
	}

	class pre implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			myservice.pre();
			playIconChange();
		}
	}

	private void connection() {
		Intent intent = new Intent();
		intent.setClass(this, MusicService.class);
		bindService(intent, sc, Service.BIND_AUTO_CREATE);
		startService(intent);
	}

	private ServiceConnection sc = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			myservice = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myservice = ((MusicService.MyBinder) (service)).getService();
		}
	};
}
