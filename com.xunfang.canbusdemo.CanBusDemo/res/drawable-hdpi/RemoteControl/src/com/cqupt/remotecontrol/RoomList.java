package com.cqupt.remotecontrol;

import java.util.List;

import com.cqupt.dbhelper.DBHelper;
import com.cqupt.model.Room;
import com.cqupt.model.RoomlistAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class RoomList extends Activity{

	private ListView list;
	private List<Room> dataArray;
	private RoomlistAdapter adapter;
	private Handler handler_show;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init_construction();
		init_setting();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		refresh_list();
	}

	@SuppressLint("HandlerLeak")
	public void init_construction(){
		handler_show = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Toast.makeText(RoomList.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
			}
		};
		list = (ListView) findViewById(R.id.list);
		Declare	declare = (Declare)getApplicationContext();
		if(declare.getDataArray() == null){
			DBHelper dbHelper = new DBHelper(this);
			dataArray = dbHelper.queryData();
			declare.setDataArray(dataArray);
			dbHelper.close();
		}else{
			dataArray = declare.getDataArray();
		}
		if(dataArray.size() < 1){
			show("没有设备信息，请添加");
			finish();
			return ;
		}
		adapter = new RoomlistAdapter(this, dataArray);
	}
	
	public void init_setting(){
		list.setAdapter(adapter);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Room room = dataArray.get(arg2);
				new AlertDialog.Builder(RoomList.this)
				.setTitle("提示")
				.setMessage("请选择需要的操作，设备："+ room.getRoomName())
				.setNeutralButton("修改", new LongClickListener(room, false))
				.setPositiveButton("删除", new LongClickListener(room,true))
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						return ;
					}
				})
				.show();  
				return false;
			}
        	
        });
	}
	
	public void show(String string){
		Message message = new Message();
		message.obj = string;
		handler_show.sendMessage(message);
	}
	
	public void refresh_list(){
		DBHelper dbHelper = new DBHelper(RoomList.this);
		dataArray = dbHelper.queryData();
		dbHelper.close();
		if(dataArray.size() < 1){
			show("没有设备信息，请添加");
			finish();
			return ;
		}
		adapter = new RoomlistAdapter(RoomList.this, dataArray);
		list.setAdapter(adapter);
	}
	
	public void refresh_info(){
		dataArray = ((Declare)getApplication()).getDataArray();
		adapter = new RoomlistAdapter(RoomList.this, dataArray);
		list.setAdapter(adapter);
	}
	
	class LongClickListener implements DialogInterface.OnClickListener {
		
		private Room room;
		private boolean which;
		
		public LongClickListener(Room room, boolean which){
			this.room = room;
			this.which = which;
		}
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			if(this.which){
				deleteByName(room.getRoomName());
			}else{
				Intent intent = new Intent();
				intent.setClass(RoomList.this, AddEquipment.class);
				Bundle bundle = new Bundle();
				bundle.putString("name", room.getRoomName());
				bundle.putString("ip", room.getRoomIp());
				bundle.putString("port", room.getRoomPort());
				intent.putExtras(bundle);
				RoomList.this.startActivity(intent);
			}
		}
		
		public void deleteByName(String name){
			DBHelper dbHelper = new DBHelper(RoomList.this);
			dbHelper.delete(name);
			dataArray = dbHelper.queryData();
			dbHelper.close();
			if(dataArray.size() < 1){
				show("没有设备信息，请添加");
				finish();
				return ;
			}
			adapter = new RoomlistAdapter(RoomList.this, dataArray);
			list.setAdapter(adapter);
			Declare	declare = (Declare)getApplicationContext();
			declare.setDataArray(dataArray);
		}
	};
	
}
