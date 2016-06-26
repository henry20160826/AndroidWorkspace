package com.hu.udp;

import static com.hu.dataclass.Communication.*;
import static com.hu.dataclass.Content.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.hu.encode.Cipher;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

public class UDPHelper{

	public DatagramSocket ds = null;
	// private int port;
	private Handler netHandler;
	private String sendString;
	private boolean receiveRunFlag = true;

	public UDPHelper(Handler handler) {
		// TODO ���캯������ʼ��UDPHelper
		if (ds == null) {
			try {
				ds = new DatagramSocket(COMMUNICATION_PORT);// ���ý��ն˿�
			} catch (SocketException e) {
				System.out.println("ds����ʧ��");
			}
		}
		netHandler = handler;
	}

	public void sendData(final String info) {
		// TODO ��������
		// Ŀ��ip��Ŀ�Ķ˿ڣ���Ϣ
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();

				try {
					String s = Cipher.encode(info);// ����
					byte[] data = s.getBytes("GB2312");
					DatagramPacket dp = new DatagramPacket(
							data,
							data.length,
							new InetSocketAddress(SERVER_IP, COMMUNICATION_PORT));
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

	public void setHandler(Handler handler) {
		netHandler = handler;
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

	public String getIP() {
		return ds.getInetAddress() + "";
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
					String decodeString = receive();
					Message msg = new Message();
					// String decodeString=Cipher.decode(receiveString);
					if (decodeString.equals("ok")) {
						msg.what = 1;
					}
					else if(decodeString.equals("no")){
						msg.what=404;
					}
					else {
						String[] info = decodeString.split(GET_INFO);
						if (info[0].equals(TABLE_STATE)) {
							msg.what = 2;
							msg.obj = info[1];
						}
					}
					netHandler.sendMessage(msg);
				}
			}
		}.start();
	}

	public void setreceiveRunFlag(boolean flag) {
		receiveRunFlag = flag;
	}
}
