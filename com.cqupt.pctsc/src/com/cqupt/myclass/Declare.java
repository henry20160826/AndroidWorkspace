package com.cqupt.myclass;

import com.cqupt.tool.UserInfo;

import android.app.Application;

public class Declare extends Application{
	
	private UserInfo userInfo;
	private EquipmentInfo equipmentInfo;
	
	public EquipmentInfo getEquipmentInfo() {
		return equipmentInfo;
	}

	public void setEquipmentInfo(EquipmentInfo equipmentInfo) {
		this.equipmentInfo = equipmentInfo;
	}

	public void setUserInfo(UserInfo userInfo){
		this.userInfo = userInfo;
	}
	
	public UserInfo getUserInfo(){
		return this.userInfo;
	}
	
}
