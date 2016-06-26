package com.hu.DataClass;

public class Content {
	// TODO 模式常量
	public static final int ADD = 010101;
	public static final int EDIT = 110000;
	public static final int LOOKUP = 16262;
	public static final int STUDENT = 111111;
	public static final int ELECTION = 976344;
	public static final int NETVOTE = 556546;

	// TODO 网络信息类型常量
	public static final int NET_CONNECT_HOPE = 793545;
	public static final int NET_CONNECT_OK = 7964646;
	public static final int NET_WANT_DATA = 634245;
	public static final int NET_RECEIVED_OK = 634598;
	public static final int NET_SEND_DATA = 434646;
	public static final int NET_SEND_VOTE = 7114343;
	public static final int NET_HAVE_VOTED = 8934342;

	public static final int ERROR = 4356433;

	public static final int SERVEICE_PORT = 4000;
	public static final int CLIENT_PORT = 3000;

	// TODO 网络信息分割常量
	public static final String SPLIT_TYPE_INFO = "#";
	public static final String SPLIT_INFO_BIG = "@";
	public static final String SPLIT_INFO_MID = ";";
	public static final String SPLIT_INFO_SML = ",";

	// TODO UI常量
	public static final int NUM_ADD = 35345;
	public static final int NUM_SUB = 63245;

	public static final int UPDATE_LISTVIEW = 756436;

	// TODO 短信查询常量
	/**
	 * 所有的短信
	 */
	public static final String SMS_URI_ALL = "content://sms/";
	/**
	 * 收件箱短信
	 */
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	/**
	 * 发件箱短信
	 */
	public static final String SMS_URI_SEND = "content://sms/sent";
	/**
	 * 草稿箱短信
	 */
	public static final String SMS_URI_DRAFT = "content://sms/draft";
}
