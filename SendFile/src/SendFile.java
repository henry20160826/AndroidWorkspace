//客户端

import java.net.*;
import java.io.*;

/**
 * SocketServer从SocketClient接受文件名，将文件发送到SocketServer端保存！
 * 
 * 
 */
public class SendFile {
	private static File f = null;
	private static FileInputStream fr = null;
	private static DataOutputStream dout = null;
	private static Socket s = null;

	public static void main(String[] args) {
		SendFile sc = new SendFile();
		sc.connectSocketServer(3000);
		sc.sendFile("lizhi.txt");
	}

	/**
	 * 连接服务器端
	 * 
	 * @param port
	 *            要连接的服务器SocketServer端口
	 */
	public void connectSocketServer(int port) {
		try {
			s = new Socket(InetAddress.getLocalHost(), port);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// 发送文件
	public void sendFile(String str) {

		byte[] b = new byte[1024];

		f = new File(str);
		try {
			// 数据输出流
			dout = new DataOutputStream(new BufferedOutputStream(
					s.getOutputStream()));

			// 文件读入流
			fr = new FileInputStream(f);
			int n = fr.read(b);
			while (n != -1) {
				// 向网络中写入数据
				dout.write(b, 0, n);
				dout.flush();
				// 再次读取n字节
				n = fr.read(b);
			}

			// 关闭流
			fr.close();
			dout.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}