package com.hu.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.hu.encode.Cipher;

public class UdpServer {

	public static final int PORT = 5555;
	private static final int DATA_LEN = 4096;
	byte[] inBuff = new byte[DATA_LEN];
	private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
	private DatagramPacket outPacket;
	String[] books = new String[] { "ok", "no" };

	public void init() throws IOException {
		try {
			DatagramSocket socket = new DatagramSocket(PORT);

			for (int i = 0; i < 1000; i++) {
				socket.receive(inPacket);
				// System.out.println(inBuff == inPacket.getData());
				String getString = new String(inBuff, 0, inPacket.getLength());
				String string = Cipher.decode(getString);
				System.out.println(getString);
				System.out.println(string);
				// String send = Cipher.encode(books[i % 2]);
				String send = books[i % 2];
				byte[] sendData = send.getBytes();
				outPacket = new DatagramPacket(sendData, sendData.length,
						inPacket.getAddress(), inPacket.getPort());
				socket.send(outPacket);
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		new UdpServer().init();
	}
}
