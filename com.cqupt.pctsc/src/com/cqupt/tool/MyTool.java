package com.cqupt.tool;

import com.cqupt.myclass.EquipmentInfo;

public class MyTool {
	
	public static final String SEPARATE_USER = "~";
	public static final String SEPARATE_EQUIPMENT = "#";
	public static final String SEPARATE_PIC_AND_GPS = "#";
	
	/*�����ص����ݽ�����һ��userinfo��*/
	public static UserInfo initUserInfo(String backData){
		if(backData == null){
			return null;
		}
		UserInfo userInfo = null;
		String userTemp[] = backData.split(SEPARATE_USER);
		if(userTemp.length < 2){
			return null;
		}
		userInfo = new UserInfo(userTemp[userTemp.length - 1]);
		for(int i = 0; i < userTemp.length - 1; i++){
			String equipmentTemp[] = userTemp[i].split(SEPARATE_EQUIPMENT);
			EquipmentInfo equipmentInfo = new EquipmentInfo(equipmentTemp[0], equipmentTemp[1], equipmentTemp[2]);
			userInfo.addEquipmentInfo(equipmentInfo);
		}
		return userInfo;
	}
	
	/*�����ص����ݽ�����һ��pictureInfo��*/
	public static PictureInfo initPictureInfo(String backData){
		PictureInfo pictureInfo = null;
System.out.println(backData);
		String picTemp[] = backData.split(SEPARATE_PIC_AND_GPS);
		if(picTemp.length != 2){
			return null;
		}
		pictureInfo = new PictureInfo(picTemp[0],picTemp[1]);
		return pictureInfo;
	}
	
	/*�����ص����ݽ�����һ��GPSInfo��*/
	public static GPSInfo initGPSInfo(String backData){
		GPSInfo gpsInfo = null;
System.out.println(backData);
		String gpsTemp[] = backData.split(SEPARATE_PIC_AND_GPS);
		if(gpsTemp.length != 2){
			return null;
		}
		gpsInfo = new GPSInfo(gpsTemp[0],gpsTemp[1]);
		return gpsInfo;
	}
	
}
