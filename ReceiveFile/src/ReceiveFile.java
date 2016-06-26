//服务器端

import java.net.*;
import java.io.*;

/**
 * Socketserver从客户端读取文件并保存为本地文件！
 * 
 * 
 */
public class ReceiveFile {
	private static ServerSocket ss = null;
	private static Socket s = null;

	private static File f = null;
	private static RandomAccessFile fw = null;

	public static void main(String[] args) {
		ReceiveFile sk = new ReceiveFile();
		sk.initServer(3000);
		sk.getFile();
	}

	/**
	 * 初始化服务器端
	 * 
	 * @param port
	 *            服务器端要使用的端口
	 */
	public void initServer(int port) {
		try {
			ss = new ServerSocket(port);
			s = ss.accept();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// 从SocketClient读取文件
	public void getFile() {
		byte[] b = new byte[1024];

		try {
			// 定义输入流，s.getInputStream();
			InputStream in = s.getInputStream();
			DataInputStream din = new DataInputStream(new BufferedInputStream(
					in));

			// 创建要保存的文件
			f = new File("copy.txt");
			fw = new RandomAccessFile(f, "rw");

			int num = din.read(b);
			while (num != -1) {
				// 向文件中写入0~num个字节
				fw.write(b, 0, num);
				// 跳过num个字节再次写入文件
				fw.skipBytes(num);
				// 读取num个字节
				num = din.read(b);
			}
			// 关闭输入，输出流
			din.close();
			fw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}