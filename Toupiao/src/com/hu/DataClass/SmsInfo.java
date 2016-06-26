package com.hu.DataClass;

/**
 * class name：SmsInfo<BR>
 * class description：获取短信各种信息的类<BR>
 * PS： <BR>
 * Date:2012-3-19<BR>
 * 
 * @version 1.00
 * @author CODYY)peijiangping
 */
public class SmsInfo {
	/**
	 * 短信内容
	 */
	private String smsbody;
	/**
	 * 发送短信的电话号码
	 */
	private String phoneNumber;
	/**
	 * 发送短信的日期和时间
	 */
	private String date;
	/**
	 * 发送短信人的姓名
	 */
	private String name;
	/**
	 * 短信类型1是接收到的，2是已发出
	 */
	private String type;

	public String getSmsbody() {
		return this.smsbody;
	}

	public void setSmsbody(String smsbody) {
		this.smsbody = smsbody;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
}