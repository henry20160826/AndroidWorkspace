package com.example.tianlai;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.GestureDetector;
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
import android.widget.TextView;
import android.widget.ViewFlipper;

public class m_introduce_lisview extends Activity {
	private AbsoluteLayout Lmusic;
	private Button button1, button2, button3, button4, button5, button6, play,
			next, pre, back;
	private MusicService myservice;
	private int num=0,state = 0;
	private Animation slideLeftIn, slideLeftOut, slideRightIn, slideRightOut,emptyIn,emptyOut;
	private ViewFlipper flipper1, flipper2;
	private GestureDetector detector1, detector2;
	private TextView hintroduce, hmap, Tmusic;
	private String[] music;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.m_introduce_listview);
		MainActivity.activityList.add(this);
		getWidget();
		setWidget();
		getopen();
		show();
	}

	void show()
	{
		for(int i=0;i<num;i++)
		{
			flipper1.showNext();
			flipper2.showNext();
		}
	}
	void getopen() {
		Intent intent = getIntent();
		num = intent.getIntExtra("num", -1);
		num=0;
	}

	void getWidget() {
		flipper1 = (ViewFlipper) findViewById(R.id.viewFlipper1);
		flipper2 = (ViewFlipper) findViewById(R.id.viewFlipper2);
		hintroduce = (TextView) findViewById(R.id.hintroduce);
		hmap = (TextView) findViewById(R.id.hmap);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		back = (Button) findViewById(R.id.back);
		Lmusic = (AbsoluteLayout) findViewById(R.id.music);
		play = (Button) findViewById(R.id.play);
		next = (Button) findViewById(R.id.next);
		pre = (Button) findViewById(R.id.pre);
		Tmusic=(TextView)findViewById(R.id.text_music);
		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		slideRightIn = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.push_right_out);
		emptyIn=AnimationUtils.loadAnimation(this, R.anim.empty_in);
		emptyOut=AnimationUtils.loadAnimation(this, R.anim.empty_out);
		music=getResources().getStringArray(R.array.music_name);

	}

	void setWidget() {
		button1.setOnClickListener(new musicOk());
		button2.setOnClickListener(new menu_Button2());
		button3.setOnClickListener(new menu_Button3());
		button4.setOnClickListener(new menu_Button4());
		button5.setOnClickListener(new menu_Button5());
		button6.setOnClickListener(new menu_Button6());
		back.setOnClickListener(new menu_Button3());
		play.setOnClickListener(new play());
		next.setOnClickListener(new next());
		pre.setOnClickListener(new pre());
		detector1 = new GestureDetector(new OnGestureListener() {

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
					flipper1.setInAnimation(slideRightIn);
					flipper1.setOutAnimation(slideLeftOut);
					flipper1.showNext();
					flipper2.showNext();
				} else if (e1.getX() - e2.getX() < -120) {
					flipper1.setInAnimation(slideLeftIn);
					flipper1.setOutAnimation(slideRightOut);
					flipper1.showPrevious();
					flipper2.showPrevious();
				}
				return true;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		flipper1.setLongClickable(true);
		flipper1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return detector1.onTouchEvent(event);
			}
		});
		detector2 = new GestureDetector(new OnGestureListener() {

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
					flipper2.setInAnimation(slideRightIn);
					flipper2.setOutAnimation(slideLeftOut);
					flipper2.showNext();
					flipper1.showNext();
				} else if (e1.getX() - e2.getX() < -120) {
					flipper2.setInAnimation(slideLeftIn);
					flipper2.setOutAnimation(slideRightOut);
					flipper2.showPrevious();
					flipper1.showPrevious();
				}
				return true;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		flipper2.setLongClickable(true);
		flipper2.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return detector2.onTouchEvent(event);
			}
		});
		hintroduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flipper1.setInAnimation(emptyIn);
				flipper1.setOutAnimation(emptyOut);
				flipper1.setVisibility(View.VISIBLE);
				flipper2.setVisibility(View.GONE);
			}
		});
		hmap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flipper2.setInAnimation(emptyIn);
				flipper2.setOutAnimation(emptyOut);
				flipper1.setVisibility(View.GONE);
				flipper2.setVisibility(View.VISIBLE);
			}
		});
		connection();
	}

	class menu_Button2 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce_lisview.this, MainActivity.class);
			startActivity(open);
		}
	}

	class menu_Button3 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce_lisview.this, m_introduce.class);
			startActivity(open);
		}
	}

	class menu_Button4 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce_lisview.this, m_map.class);
			startActivity(open);
		}
	}

	class menu_Button5 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce_lisview.this, m_team.class);
			startActivity(open);
		}
	}

	class menu_Button6 implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open = new Intent();
			open.setClass(m_introduce_lisview.this, m_contact.class);
			startActivity(open);
		}
	}


	// ÒôÀÖ*************************************************************
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
