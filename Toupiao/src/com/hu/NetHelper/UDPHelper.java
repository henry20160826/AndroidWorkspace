package com.hu.NetHelper;

import static com.hu.DataClass.Content.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.os.Handler;
import android.os.Message;

public class UDPHelper {

	public DatagramSocket ds = null;
	private int port;
	private Handler netHandler;
	private boolean receiveRunFlag = false;
	private String sourceIP;

	public UDPHelper(int port, Handler handler) {
		// TODO ���캯������ʼ��UDPHelper
		if (ds == null) {
			try {
				ds = new DatagramSocket(port);// ���ý��ն˿�
				this.port = port;
			} catch (SocketException e) {
				System.out.println("ds����ʧ��");
			}
		}
		netHandler = handler;
	}

	public int getPort() {
		// TODO �õ�ʹ�õĶ˿ں�
		return this.port;
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

	public void sendData(final String ip, final int port, final String info) {
		// TODO ��������
		// Ŀ��ip��Ŀ�Ķ˿ڣ���Ϣ
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
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
			
		}.start();
	}

	public void sendNetConnectHope(String ip, int port) {
		sendData(ip, port, NET_CONNECT_HOPE + SPLIT_TYPE_INFO);
	}

	public void sendNetConnectOk(String ip, int port) {
		sendData(ip, port, NET_CONNECT_OK + SPLIT_TYPE_INFO);
	}

	public void sendNetWantData(String ip, int port) {
		sendData(ip, port, NET_WANT_DATA + SPLIT_TYPE_INFO);
	}

	public void sendNetReceivedOk(String ip, int port) {
		sendData(ip, port, NET_RECEIVED_OK + SPLIT_TYPE_INFO);
	}

	public void sendNetSendData(String ip, int port, String num,
			String studentname) {
		sendData(ip, port, NET_SEND_DATA + SPLIT_TYPE_INFO + num
				+ SPLIT_INFO_BIG + studentname);
	}

	public void sendNetSendVote(String ip, int port, String phonenum,
			String vote) {
		sendData(ip, port, NET_SEND_VOTE + SPLIT_TYPE_INFO + phonenum
				+ SPLIT_INFO_BIG + vote);
	}

	public void sendNetHaveVoted(String ip, int port) {
		sendData(ip, port, NET_HAVE_VOTED + SPLIT_TYPE_INFO);
	}

	private String receive() {
		// TODO ��������
		byte[] data = new byte[1024];
		DatagramPacket dp = new DatagramPacket(data, data.length);
		try {
			ds.receive(dp);
			String s[] = dp.getAddress().toString().split("/");
			sourceIP = s[1];
			System.out.println("���յ�����");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("����ʧ��");
			System.out.println(e.toString());
		}
		String info = new String(dp.getData());
		return info.trim();
	}

	public String getSourceIP() {
		return this.sourceIP;
	}

	public String getIP() {
		return ds.getInetAddress().toString();// �õ�����IP
	}

	public void close() {
		// TODO �ر�UDPHelper
		if (ds != null) {
			ds.close();
			ds = null;
		}
	}

	public void receiveRun() {
		receiveRunFlag = true;
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				while (receiveRunFlag) {
					String receiveString = receive();
					// Message msg = netHandler.obtainMessage();
					Message msg = new Message();
					msg.obj = receiveString;
					netHandler.sendMessage(msg);
				}
			}
		}.start();
	}

	public boolean getReceiveRunFlag() {
		return this.receiveRunFlag;
	}

	public void setReceiveRunFlag(boolean flag) {
		this.receiveRunFlag = flag;
	}
}
