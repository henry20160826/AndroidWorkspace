package com.cqupt.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.cqupt.myclass.Declare;
import com.cqupt.pctsc.R;
import com.cqupt.pctsc.R.id;
import com.cqupt.pctsc.R.layout;
import com.cqupt.tool.UserInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ChooseActivity extends Activity{
	
	public static ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equipmentchoose);
		Declare declare = (Declare) getApplicationContext();
		UserInfo userInfo = declare.getUserInfo();
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		for(int i = 0; i < userInfo.getEquipmentInfos().size(); i++){
			String tempString = "设备号:" + userInfo.getEquipmentInfos().get(i).getEID();
			tempString += " 设备名:" + userInfo.getEquipmentInfos().get(i).getEquipmentName();
			tempString += " 设备类型:" + userInfo.getEquipmentInfos().get(i).getType();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("item", tempString);
			listItem.add(map);
		}
		list = (ListView) findViewById(R.id.chooselist);
		list.setDividerHeight(0);
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.list, new String[]{"item"}, new int[]{R.id.item});
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Declare declare = (Declare) getApplicationContext();
				declare.setEquipmentInfo(declare.getUserInfo().getEquipmentInfos().get(arg2));
				moveTo();
			}
		});
	}
	
	public void moveTo(){
		Intent intent = new Intent();
		intent.setClass(this, InfoActivity.class);
		startActivity(intent);
	}
}
