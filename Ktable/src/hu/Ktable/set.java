package hu.Ktable;

import static hu.Ktable.MyConfig.*;
import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class set extends Activity{

	private RadioGroup styleGroup;
	private RadioButton my,pink,green,blue;
	private int nowStyle;
	private Button ok;
	private LinearLayout a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setWindowStyle();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);	
		getItem();
		setItem();
		int num=dbhelper.readstyle(dbW);
		setStyle(num);
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	private void setStyle(int num) {
	//  4	全局模式
//		0 	默认皮肤
//		1	黑色炫酷
//		2 	绿色雨林
//		3 	蓝色海洋
		switch(num)
		{
		case 1:
			pink.setChecked(true);
			break;
		case 2:
			green.setChecked(true);	
			break;
		case 3:
			blue.setChecked(true);			
			break;
		default:
			my.setChecked(true);
			ContentValues b=new ContentValues();
			b.put("data",0);
			dbW.update("user",b, "id=?",new String[]{"0"});
			break;
		}
	}
	private void setItem() {
		styleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				Resources resource=getResources();
				Drawable dr;
				switch(checkedId)
				{
					case R.id.my:
						nowStyle=0;
						dr=resource.getDrawable(R.drawable.shuimo1);
						a.setBackgroundDrawable(dr);
						my.setBackgroundColor(Color.parseColor("#000000"));
						pink.setBackgroundColor(Color.TRANSPARENT);
						green.setBackgroundColor(Color.TRANSPARENT);
						blue.setBackgroundColor(Color.TRANSPARENT);
						break;
					case R.id.pink:
						nowStyle=1;
						dr=resource.getDrawable(R.drawable.pink1);
						a.setBackgroundDrawable(dr);
						pink.setBackgroundColor(Color.parseColor("#e5336e"));
						my.setBackgroundColor(Color.TRANSPARENT);
						green.setBackgroundColor(Color.TRANSPARENT);
						blue.setBackgroundColor(Color.TRANSPARENT);
						break;
					case R.id.green:
						nowStyle=2;
						dr=resource.getDrawable(R.drawable.green1);
						a.setBackgroundDrawable(dr);
						green.setBackgroundColor(Color.parseColor("#005500"));
						my.setBackgroundColor(Color.TRANSPARENT);
						pink.setBackgroundColor(Color.TRANSPARENT);
						blue.setBackgroundColor(Color.TRANSPARENT);
						break;
					case R.id.blue:
						nowStyle=3;
						dr=resource.getDrawable(R.drawable.blue1);
						a.setBackgroundDrawable(dr);
						blue.setBackgroundColor(Color.parseColor("#296c88"));
						my.setBackgroundColor(Color.TRANSPARENT);
						pink.setBackgroundColor(Color.TRANSPARENT);
						green.setBackgroundColor(Color.TRANSPARENT);
						break;
				}
			}
		});
		
		ok.setOnClickListener(new buttonOk());
	}
	private void setWindowStyle() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	private void getItem() {
		my=(RadioButton)findViewById(R.id.my);
		a=(LinearLayout) findViewById(R.id.setWindow);
		pink=(RadioButton)findViewById(R.id.pink);
		green=(RadioButton)findViewById(R.id.green);
		blue=(RadioButton)findViewById(R.id.blue);
		styleGroup=(RadioGroup)findViewById(R.id.styleGroup);
		ok=(Button)findViewById(R.id.setOK);
	}
	class buttonOk implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ContentValues b=new ContentValues();
			b.put("data", nowStyle);
			dbW.update("user",b, "id=?",new String[]{"0"});
			finish();
		}	
	}
	
}
