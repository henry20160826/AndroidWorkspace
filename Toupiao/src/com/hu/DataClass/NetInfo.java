package com.hu.DataClass;

import static com.hu.DataClass.Content.*;

public class NetInfo {
	String all;
	String type;
	String infoBig;

	public NetInfo() {
		// TODO Auto-generated constructor stub
	}

	public NetInfo(String all) {
		this.all = all;
		String s[] = all.split(SPLIT_TYPE_INFO);
		if (s.length > 1) {
			this.type = s[0];
			this.infoBig = s[1];
		} else {
			type = s[0];
		}
	}

	public NetInfo(String s[]) {
		if (s.length > 1) {
			this.type = s[0];
			this.infoBig = s[1];
		}
	}

	public NetInfo(int type, String infoString) {
		this.all = type + SPLIT_TYPE_INFO + infoString;
	}

	public NetInfo(String typeString, String infoString) {
		this.all = typeString + SPLIT_TYPE_INFO + infoString;
	}

	public String getTypeString() {
		return this.type;
	}

	public int getTypeInt() {
		if (this.type.equals("")) {
			return ERROR;
		}
		return Integer.parseInt(this.type);
	}

	public String getInfoBig() {
		return this.infoBig;
	}

	public String getInfoMidOne() {
		String s[] = this.infoBig.split(SPLIT_INFO_BIG);
		if (s.length > 1) {
			return s[0];
		}
		return null;
	}

	public String getInfoMidTwo() {
		String s[] = this.infoBig.split(SPLIT_INFO_BIG);
		if (s.length > 1) {
			return s[1];
		}
		return null;
	}
}
