package com.example.tianlai;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.FloatMath;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class m_map extends Activity {
	private Button button1 = null, button2, button3, button4, button5, button6,
			updown, play, next, pre;
	private TableRow menu_map;
	private TextView cq, wsh, zhb;
	private ViewFlipper map;
	private GestureDetector detector;
	private Animation slideLeftIn, slideLeftOut, slideRightIn, slideRightOut;
	private AbsoluteLayout Lmusic;
	private MusicService myservice;
	private int num = 0;
	private boolean up = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
		setContentView(R.layout.m_map);
		MainActivity.activityList.add(this);
		getWidget();
		setWidget();
	}

	void getWidget() {
		Lmusic = (AbsoluteLayout) findViewById(R.id.music);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		updown = (Button) findViewById(R.id.updown);
		cq = (TextView) findViewById(R.id.cq);
		wsh = (TextView) findViewById(R.id.wsh);
		zhb = (TextView) findViewById(R.id.zhb);
		map = (ViewFlipper) findViewById(R.id.viewFlipper1);
		menu_map = (TableRow) findViewById(R.id.tableRow1);
		play = (Button) findViewById(R.id.play);
		next = (Button) findViewById(R.id.next);
		pre = (Button) findViewById(R.id.pre);
		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		slideRightIn = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.push_right_out);
	}

	void setWidget() {
		button1.setOnClickListener(new musicOk());
		button2.setOnClickListener(new menu_Button2());
		button3.setOnClickListener(new menu_Button3());
		button4.setOnClickListener(new menu_Button4());
		button5.setOnClickListener(new menu_Button5());
		button6.setOnClickListener(new menu_Button6());
		cq.setOnClickListener(new cq());
		wsh.setOnClickListener(new wsh());
		zhb.setOnClickListener(new zhb());
		updown.setOnClickListener(new updown());

		detector = new GestureDetector(new OnGestureListener() {

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
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				if (e1.getX() - e2.getX() > 120) {
					map.setInAnimation(slideRightIn);
					map.setOutAnimation(slideLeftOut);
					map.showNext();
					num++;
					if (num > 2) {
						num = 0;
					}
				} else if (e1.getX() - e2.getX() < -120) {
					map.setInAnimation(slideLeftIn);
					map.setOutAnimation(slideRightOut);
					map.showPrevious();
					num--;
					if (num < 0) {
						num = 2;
					}
				}
				switch (num) {
				case 0:
					menu_map.setBackgroundResource(R.drawable.from_pcq);
					break;
				case 1:
					menu_map.setBackgroundResource(R.drawable.from_pws);
					break;
				case 2:
					menu_map.setBackgroundResource(R.drawable.caidan_paround);
					break;
				}
				return true;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

		});
		map.setLongClickable(true);
		map.setOnTouchListener(new OnTouchListener() {
			int mode = -1;
			int DRAG = 1;
			int NONE = 0;
			int ZOOM = 2;
			float oldDist;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					Toast.makeText(m_map.this, "0000", Toast.LENGTH_SHORT)
							.show();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					Toast.makeText(m_map.this,
							"ACTION_POINTER_DOWN：" + oldDist + "",
							Toast.LENGTH_SHORT).show();
//					if (oldDist > 10f) {
//						mode = ZOOM;
//					}
//					break;
				case MotionEvent.ACTION_MOVE:
					mode = ZOOM;
					break;
				case MotionEvent.ACTION_POINTER_UP:
					if (mode == ZOOM) {
						// 正在移动的点距初始点的距离
						float newDist = spacing(event);
						Toast.makeText(m_map.this,
								"ACTION_MOVE:" + newDist + "",
								Toast.LENGTH_SHORT).show();
					}
					break;
				}

				return detector.onTouchEvent(event);

			}
		});
		connection();
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	class cq implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			menu_map.setBackgroundResource(R.drawable.from_pcq);
			switch (num) {
			case 2:
				map.showPrevious();
				break;
			case 1:
				map.showNext();
				break;
			}
			num = 0;
		}

	}

	class wsh implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			menu_map.setBackgroundResource(R.drawable.from_pws);
			switch (num) {
			case 0:
				map.showPrevious();
				break;
			case 2:
				map.showNext();
				break;
			}
			num = 1;
		}

	}

	class zhb implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			menu_map.setBackgroundResource(R.drawable.caidan_paround);
			switch (num) {
			case 0:
				map.showNext();
			case 1:
				map.showPrevious();
				break;
			}
			num = 2;
		}

	}

	class updown implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (up == false)// 菜单在下
			{
				menu_map.setVisibility(View.GONE);
				updown.setBackgroundResource(R.drawable.caidan_xia);
				up = true;
			} else {
				menu_map.setVisibility(View.VISIBLE);
				updown.setBackgroundResource(R.drawable.from_shang);
				up = false;
			}
		}

	}

	class menu_Button2 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_map.this, MainActivity.class);
			startActivity(open);
		}
	}

	class menu_Button3 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_map.this, m_introduce.class);
			startActivity(open);
		}
	}

	class menu_Button4 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_map.this, m_map.class);
			startActivity(open);
		}
	}

	class menu_Button5 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_map.this, m_team.class);
			startActivity(open);
		}
	}

	class menu_Button6 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_map.this, m_contact.class);
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

	// 音乐*************************************************************
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
