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

	public WifiHelper(Context context) {
		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
	}

	public int getAPState() {
		int state = wifiManager.getWifiState();
		return state;
	}

	public String getIp() {
		// TODO ��ȡip
		dhcpInfo = wifiManager.getDhcpInfo();
		wifiInfo = wifiManager.getConnectionInfo();
		// wifiInfo���ص�ǰ��Wi-Fi���ӵĶ�̬��Ϣ
		int ip = wifiInfo.getIpAddress();
		// return "wifi_ip:" + FormatIP(ip);
		return FormatIP(ip);
	}

	public String getGateWay() {
		// TODO ���ػ�ȡ
		dhcpInfo = wifiManager.getDhcpInfo();

		// dhcpInfo��ȡ�������һ�γɹ��������Ϣ���������ء�ip��
		// return "dh_ip:" + FormatIP(dhcpInfo.ipAddress) + "\n" + "dh_gateway"
		// + FormatIP(dhcpInfo.gateway);
		return FormatIP(dhcpInfo.gateway);
	}

	@SuppressWarnings("deprecation")
	public static String FormatIP(int IpAddress) {
		// TODO IP��ַת��Ϊ�ַ�����ʽ
		return Formatter.formatIpAddress(IpAddress);
	}

}
