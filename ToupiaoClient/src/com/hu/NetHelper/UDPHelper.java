package com.hu.NetHelper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class UDPHelper {

	public DatagramSocket ds = null;
	private int port;

	public UDPHelper(int port) {
		// TODO 构造函数，初始化UDPHelper
		if (ds == null) {
			try {
				ds = new DatagramSocket(port);
				this.port = port;
			} catch (SocketException e) {
				System.out.println("ds创建失败");
			}
		}
	}

	public int getPort() {
		// TODO 得到使用的端口号
		return port;
	}

	public String getLocalIpAddress() {
		// TODO 得到本机IP地址
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			System.out.println("获得IP地址失败");
		}
		return null;
	}

	public void sendData(String ip, int port, String info) {
		// TODO 发送数据
		byte[] data = info.getBytes();
		try {
			DatagramPacket dp = new DatagramPacket(data, data.length,
					new InetSocketAddress(ip, port));
			ds.send(dp);
			System.out.println("发送成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("发送失败");
			System.out.println(e.toString());
		}
	}

	public String receive() {
		// TODO 接收数据
		byte[] data = new byte[1024];
		DatagramPacket dp = new DatagramPacket(data, data.length);
		try {
			ds.receive(dp);
			System.out.println("接收到数据");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("接收失败");
			System.out.println(e.toString());
		}
		String info = new String(dp.getData());
		return info.trim();
	}

	public void close() {
		// TODO 关闭UDPHelper
		if (ds != null) {
			ds.close();
			ds = null;
		}
	}
}
