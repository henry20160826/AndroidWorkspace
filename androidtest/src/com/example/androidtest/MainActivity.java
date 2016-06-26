package com.example.androidtest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		// tabHost.addTab(tabHost.newTabSpec("111").setIndicator("view1")
		// .setContent(R.id.tab1));
		// tabHost.addTab(tabHost.newTabSpec("222").setIndicator("view2")
		// .setContent(R.id.tab2));
		textView = (TextView) findViewById(R.id.alert_title);

		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String username = null, password = null;
				startUrlCheck(username, password);
				super.run();
			}

		}.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void startUrlCheck(String username, String password) {
		HttpClient client = new DefaultHttpClient();
		StringBuilder builder = new StringBuilder();

		HttpGet myget = new HttpGet("http://kamuihqh.5.103dns.info/test.php");
		try {
			HttpResponse response = client.execute(myget);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
			JSONObject jsonObject = new JSONObject(builder.toString());
			String re_username = jsonObject.getString("username");
			String re_password = jsonObject.getString("password");
			int re_user_id = jsonObject.getInt("user_id");
//			setTitle("”√ªßid_" + re_user_id);
			Log.v("url response", "true=" + re_username);
			Log.v("url response", "true=" + re_password);
//			textView.setText(re_username + re_password);
		} catch (Exception e) {
			Log.v("url response", "false");
			e.printStackTrace();
		}
	}
}
