package hu.network;

//客户端
import static hu.dataclass.Content.*;

import java.net.*;
import java.io.*;

import android.os.Environment;

/**
 * SocketServer从SocketClient接受文件名，将文件发送到SocketServer端保存！
 * 
 * 
 */
public class SendFile implements Runnable {
	private static File f = null;
	private static FileInputStream fr = null;
	private static DataOutputStream dout = null;
	private static Socket s = null;
	private String fileNameString;
	private String iP;
	private String SDPath;

	public SendFile(String iP, String fileNameString) {
		// TODO Auto-generated constructor stub
		this.iP = iP;
		SDPath = getSDPath();
		this.fileNameString =SDPath+ PATH + fileNameString;


	}

	public void run() {
		connectSocketServer(FILE_PORT);
	}

	/**
	 * 连接服务器端
	 * 
	 * @param port
	 *            要连接的服务器SocketServer端口
	 */
	public void connectSocketServer(int port) {
		try {
			s = new Socket(iP, port);
			sendFile(fileNameString);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
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
			s.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();

	}

}