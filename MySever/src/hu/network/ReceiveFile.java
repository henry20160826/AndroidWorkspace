package hu.network;

//服务器端
import static hu.dataclass.Content.*;

import java.net.*;
import java.io.*;

/**
 * Socketserver从客户端读取文件并保存为本地文件！
 * 
 * 
 */
public class ReceiveFile implements Runnable {
	private static ServerSocket ss = null;
	private static Socket s = null;

	private static File f = null;
	private static RandomAccessFile fw = null;

	private String fileNameString;

	public ReceiveFile(String fileNameString) {
		// TODO Auto-generated constructor stub
		this.fileNameString = fileNameString;
	}

	public void run() {
		initServer(FILE_PORT);
		getFile();
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

			// 如果删除重名文件
			deleteFile(PATH + fileNameString);

			// 创建要保存的文件
			f = new File(PATH + fileNameString);
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
			ss.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
}