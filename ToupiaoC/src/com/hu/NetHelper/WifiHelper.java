package com.hu.NetHelper;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class WifiHelper {
	private static WifiManager wifiManager;
	private static DhcpInfo dhcpInfo;
	private static WifiInfo wifiInfo;

	public String getIp(Context context) {
		// TODO ip获取
		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		dhcpInfo = wifiManager.getDhcpInfo();
		wifiInfo = wifiManager.getConnectionInfo();
		// wifiInfo返回当前的Wi-Fi连接的动态信息
		int ip = wifiInfo.getIpAddress();
		// return "wifi_ip:" + FormatIP(ip);
		return FormatIP(ip);
	}

	public String getGateWay(Context context) {
		// TODO 网关获取
		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		dhcpInfo = wifiManager.getDhcpInfo();

		// dhcpInfo获取的是最后一次成功的相关信息，包括网关、ip等
		// return "dh_ip:" + FormatIP(dhcpInfo.ipAddress) + "\n" + "dh_gateway"
		// + FormatIP(dhcpInfo.gateway);
		return FormatIP(dhcpInfo.gateway);
	}

	@SuppressWarnings("deprecation")
	public static String FormatIP(int IpAddress) {
		// TODO IP地址转化为字符串格式
		return Formatter.formatIpAddress(IpAddress);
	}

}
