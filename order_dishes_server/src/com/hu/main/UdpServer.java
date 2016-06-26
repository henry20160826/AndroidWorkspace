package com.hu.main;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServer {

	public static final int PORT = 30000;
	private static final int DATA_LEN = 4096;
	byte[] inBuff = new byte[DATA_LEN];
	private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
	private DatagramPacket outPacket;
	String[] books = new String[] { "ºúÇå»ª", "¹þ¹þ" };

	public void init() throws IOException {
		try {
			DatagramSocket socket = new DatagramSocket(PORT);

			for (int i = 0; i < 1000; i++) {
				socket.receive(inPacket);
				System.out.println(inBuff == inPacket.getData());
				System.out.println(new String(inBuff, 0, inPacket.getLength()));
				byte[] sendData = books[i % 2].getBytes();
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
