package com.hu.Activity;

import com.hu.NetHelper.WifiHelper;
import com.hu.toupiaoclient.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

	private TextView textView;
	private WifiHelper wifiHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wifiHelper = new WifiHelper();
		String ip = wifiHelper.getIp(MainActivity.this);
		String gw = wifiHelper.getGateWay(getApplication());
		textView.setText(ip + "\n" + gw);
	}

	public void getWidget() {
		textView = (TextView) findViewById(R.id.textView1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		}
		return super.onOptionsItemSelected(item);
	}

}
