package com.hu.DataClass;

/**
 * class name��SmsInfo<BR>
 * class description����ȡ���Ÿ�����Ϣ����<BR>
 * PS�� <BR>
 * Date:2012-3-19<BR>
 * 
 * @version 1.00
 * @author CODYY)peijiangping
 */
public class SmsInfo {
	/**
	 * ��������
	 */
	private String smsbody;
	/**
	 * ���Ͷ��ŵĵ绰����
	 */
	private String phoneNumber;
	/**
	 * ���Ͷ��ŵ����ں�ʱ��
	 */
	private String date;
	/**
	 * ���Ͷ����˵�����
	 */
	private String name;
	/**
	 * ��������1�ǽ��յ��ģ�2���ѷ���
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