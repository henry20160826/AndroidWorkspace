package com.cqupt.pctsc;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UserInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<EquipmentInfo> equipmentInfos;
	private String md5;
	
	public UserInfo(String md5) {
		this.md5 = md5;
		equipmentInfos = new LinkedList<EquipmentInfo>();
	}
	
	public String getMd5(){
		return md5;
	}
	
	public void addEquipmentInfo(EquipmentInfo equipmentInfo){
		equipmentInfos.add(equipmentInfo);
	}
	
	public int getCount(){
		return equipmentInfos.size();
	}
	
	public List<EquipmentInfo> getEquipmentInfos(){
		return equipmentInfos;
	}

	@Override
	public String toString() {
		String myString = "md5:" + md5 + "\n";
		for(int i = 0; i < equipmentInfos.size(); i++){
			myString += "No." + i + ": " + equipmentInfos.get(i).toString() + "\n";
		}
		return myString;
	}
	
	
}
