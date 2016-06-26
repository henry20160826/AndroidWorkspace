package com.cqupt.pctsc;

public class GPSInfo {
	
	public String date;
	public String GPSdata;
	
	public GPSInfo(String GPSdata, String date) {
		this.GPSdata = GPSdata;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public String getGPSdata() {
		return GPSdata;
	}

	@Override
	public String toString() {
		return "位置信息:" + GPSdata + "\n" + "时间:" + date + "\n";
	}
}
