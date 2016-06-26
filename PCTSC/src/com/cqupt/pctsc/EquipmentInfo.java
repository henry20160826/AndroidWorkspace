package com.cqupt.pctsc;

public class EquipmentInfo {

	private String EID;
	private String equipmentName;
	private String type;
	
	public EquipmentInfo(String EID, String equipmentName, String type) {
		this.EID = EID;
		this.type = type;
		this.equipmentName = equipmentName;
	}
	
	public String getEID() {
		return EID;
	}
	
	public String getType() {
		return type;
	}
	
	public String getEquipmentName() {
		return equipmentName;
	}

	@Override
	public String toString() {
		return "EID:" + EID + " " + "设备名:" + equipmentName + " " + "类型:" + type; 
	}	
	
	
}
