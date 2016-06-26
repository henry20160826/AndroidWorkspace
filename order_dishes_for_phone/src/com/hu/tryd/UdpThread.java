package com.hu.tryd;

import static com.hu.dataclass.Communication.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import android.os.Handler;
import android.os.Message;

public class UdpThread implements Runnable {
	private static final int DATA_LEN = 4096;
	private Handler handler;
	private DatagramSocket socket;
	

	byte[] inBuff = new byte[DATA_LEN];

	private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
	private DatagramPacket outPacket = null;

	public UdpThread(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
	}

	public void init() {
		try {
			socket = new DatagramSocket();
			outPacket = new DatagramPacket(new byte[0], 0,
					InetAddress.getByName(SERVER_IP), COMMUNICATION_PORT);
			// Scanner scanner = new Scanner(System.in);
			// while (scanner.hasNextLine()) {
			// byte[] buff = scanner.nextLine().getBytes();
			// outPacket.setData(buff);
			// socket.send(outPacket);
			// socket.receive(inPacket);
			// System.out.println(new String(inBuff, 0, inPacket.getLength()));
			// }
//			while (true) {
//				socket.receive(inPacket);
//				String receiveString = new String(inBuff, 0,
//						inPacket.getLength());
//				Message msg = new Message();
//				msg.obj = receiveString;
//				handler.sendMessage(msg);
//			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void send(final String string) {

		try {
			byte[] buff = string.getBytes();
			outPacket.setData(buff);
			socket.send(outPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void close() {
		socket.close();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
	}



}
