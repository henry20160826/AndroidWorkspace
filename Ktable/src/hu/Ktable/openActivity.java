package hu.Ktable;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class openActivity extends Activity {
    /** Called when the activity is first created. */
	private int style;
	private DatabaseHelper dbhelper;
	private SQLiteDatabase dbW;
	private ImageButton short_show;
	private Handler handler;
	private Runnable r;
    @Override
    public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题  
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏 
        super.onCreate(savedInstanceState);
		dbhelper=new DatabaseHelper(openActivity.this,"user",null,1);
		dbW=dbhelper.getWritableDatabase();
		style=dbhelper.readstyle(dbW);
		set();
		short_show=(ImageButton)findViewById(R.id.short_show);
		short_show.setOnClickListener(new short_show());
		handler=new Handler();
		r=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent open=new Intent();
				open.setClass(openActivity.this,KtableActivity.class);
				startActivity(open);
				finish();
			}
		};
		handler.postDelayed(r, 200);
    }
	private void set()
	{
		switch(style)
		{
		case 1://粉色女生
			setContentView(R.layout.k_pink0);
			break;
		case 2://绿色雨后
			setContentView(R.layout.k_green0);
			break;
		case 3://蓝色雨滴
			setContentView(R.layout.k_blue0);
			break;
		case 4:
			setContentView(R.layout.quaju);
			break;
		default://默认皮肤(水墨清华)
			setContentView(R.layout.k_shuimo0);
			break;
		}
	}
	class short_show implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent open=new Intent();
			open.setClass(openActivity.this,KtableActivity.class);
			startActivity(open);
			handler.removeCallbacks(r);
			finish();
		}
	}
}