package com.cqupt.tool;

import com.cqupt.myclass.EquipmentInfo;

public class MyTool {
	
	public static final String SEPARATE_USER = "~";
	public static final String SEPARATE_EQUIPMENT = "#";
	public static final String SEPARATE_PIC_AND_GPS = "#";
	
	/*将返回的数据解析成一个userinfo类*/
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
	
	/*将返回的数据解析成一个pictureInfo类*/
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
	
	/*将返回的数据解析成一个GPSInfo类*/
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
