package com.cqupt.internet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPHelper {
	
	public static final int PORT = 22222;
	
	public DatagramSocket ds;
	
	public UDPHelper(){
		if(ds == null){
			try {
				ds = new DatagramSocket(PORT);
			} catch (SocketException e) {
				System.out.println(e);
			}
		}
	}
	
	public void sendData(String ip, int port, String info){
		byte[] data = info.getBytes();
		try {
			DatagramPacket dp = new DatagramPacket(data, data.length, new InetSocketAddress(ip, port));
			ds.send(dp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			System.out.println("∑¢ÀÕ ß∞‹");
		}
	}
	
	public String receive(){
		byte[] data = new byte[1024];
		DatagramPacket dp = new DatagramPacket(data, data.length);
		try {
			ds.receive(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			System.out.println("Ω” ’ ß∞‹");
		}
		String info = new String(dp.getData());
		return info.trim();
	}
	
}
