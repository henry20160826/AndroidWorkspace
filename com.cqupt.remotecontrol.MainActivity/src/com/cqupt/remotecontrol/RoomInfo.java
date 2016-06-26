package com.cqupt.remotecontrol;

import com.cqupt.model.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RoomInfo extends Activity{
	
	private static TextView textView;
	private static Room room;
	private String roomName;
	private TextView digital_1;
	private TextView digital_2;
	private TextView digital_3;
	private TextView digital_4;
	private TextView analog_1;
	private TextView analog_2;
	private TextView analog_3;
	private TextView analog_4;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.roominfo);
		digital_1 = (TextView)findViewById(R.id.digital_1);
		digital_2 = (TextView)findViewById(R.id.digital_2);
		digital_3 = (TextView)findViewById(R.id.digital_3);
		digital_4 = (TextView)findViewById(R.id.digital_4);
		analog_1 = (TextView)findViewById(R.id.analog_1);
		analog_2 = (TextView)findViewById(R.id.analog_2);
		analog_3 = (TextView)findViewById(R.id.analog_3);
		analog_4 = (TextView)findViewById(R.id.analog_4);
		textView = (TextView)findViewById(R.id.roomInfo_name);
		Intent intent = getIntent();
		roomName = intent.getStringExtra("roomName");
		textView.setText(roomName);
		refresh();
	}
	
	public void showRoomInfo(){
		digital_1.setText(room.getDigital_1());
		digital_2.setText(room.getDigital_2());
		digital_3.setText(room.getDigital_3());
		digital_4.setText(room.getDigital_4());
		analog_1.setText(room.getDigital_1());
		analog_2.setText(room.getDigital_2());
		analog_3.setText(room.getDigital_3());
		analog_4.setText(room.getDigital_4());
	}
	
	public void refresh(){
		Declare declare = (Declare)getApplication();
		room = declare.getRoomByName(roomName);
		showRoomInfo();
	}
}
