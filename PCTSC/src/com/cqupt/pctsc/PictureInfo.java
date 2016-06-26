package com.cqupt.pctsc;

public class PictureInfo {

	private String picPath;
	private String date;
	
	public PictureInfo(String picPath, String date) {
		this.picPath = Constant.PATH + "/" + picPath;
		this.date = date;
	}

	public String getPicPath() {
		return picPath;
	}

	public String getDate() {
		return date;
	}	
}
