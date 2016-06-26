package com.cqupt.remotecontrol;

import com.cqupt.internet.UDPHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	public UDPHelper udpHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		udpHelper = new UDPHelper();
		new Thread(){

			@Override
			public void run() {
				while(true){
					String string = udpHelper.receive();
					System.out.println(string);
				}
			}
			
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.menu_1){
			Intent intent = new Intent();
			intent.setClass(this, RoomList.class);
			startActivity(intent);
		}else if(item.getItemId() == R.id.menu_2){
			Intent intent = new Intent();
			intent.setClass(this, AddEquipment.class);
			startActivity(intent);
		}else if(item.getItemId() == R.id.menu_3){
			udpHelper.sendData("172.18.105.230", 22223, "123");
		}else if(item.getItemId() == R.id.menu_4){
			Intent intent = new Intent();
			intent.setClass(this, RoomInfo.class);
			intent.putExtra("roomName", "309");
			startActivity(intent);
		}
		return super.onMenuItemSelected(featureId, item);
	}

	
}
