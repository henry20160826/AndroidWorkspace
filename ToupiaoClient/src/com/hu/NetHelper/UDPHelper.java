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
		// TODO ���캯������ʼ��UDPHelper
		if (ds == null) {
			try {
				ds = new DatagramSocket(port);
				this.port = port;
			} catch (SocketException e) {
				System.out.println("ds����ʧ��");
			}
		}
	}

	public int getPort() {
		// TODO �õ�ʹ�õĶ˿ں�
		return port;
	}

	public String getLocalIpAddress() {
		// TODO �õ�����IP��ַ
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
			System.out.println("���IP��ַʧ��");
		}
		return null;
	}

	public void sendData(String ip, int port, String info) {
		// TODO ��������
		byte[] data = info.getBytes();
		try {
			DatagramPacket dp = new DatagramPacket(data, data.length,
					new InetSocketAddress(ip, port));
			ds.send(dp);
			System.out.println("���ͳɹ�");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("����ʧ��");
			System.out.println(e.toString());
		}
	}

	public String receive() {
		// TODO ��������
		byte[] data = new byte[1024];
		DatagramPacket dp = new DatagramPacket(data, data.length);
		try {
			ds.receive(dp);
			System.out.println("���յ�����");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("����ʧ��");
			System.out.println(e.toString());
		}
		String info = new String(dp.getData());
		return info.trim();
	}

	public void close() {
		// TODO �ر�UDPHelper
		if (ds != null) {
			ds.close();
			ds = null;
		}
	}
}
